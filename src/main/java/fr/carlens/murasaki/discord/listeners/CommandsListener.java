package fr.carlens.murasaki.discord.listeners;

import fr.carlens.murasaki.ConsoleColors;
import fr.carlens.murasaki.discord.Bot;
import fr.carlens.murasaki.discord.reader.MangaReader;
import fr.carlens.murasaki.logic.api.APIException;
import fr.carlens.murasaki.logic.api.MangadexClient;
import fr.carlens.murasaki.logic.api.models.Manga;
import fr.carlens.murasaki.logic.api.payloads.requests.SearchMangaRequest;
import fr.carlens.murasaki.logic.sessions.Session;
import fr.carlens.murasaki.logic.sessions.SessionKey;
import fr.carlens.murasaki.logic.sessions.SessionsManager;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsListener implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent e) {
        var interaction = e.getSlashCommandInteraction();

        if (interaction.getChannel().isEmpty()) return;
        Bot.logInteraction(e.getInteraction(), interaction.getCommandName());

        SessionKey key = new SessionKey(interaction.getChannel().get().getId(), interaction.getUser().getId());

        switch (interaction.getCommandName()) {

            case "search" -> {

                String mangaTitle = interaction.getArguments().get(0).getStringValue().orElse("");
                String language = interaction.getArguments().get(1).getStringValue().orElse("");
                language = language.toLowerCase();

                MangadexClient client = new MangadexClient();
                try {
                    List<Manga> mangas = client.searchManga(new SearchMangaRequest(mangaTitle, 3));
                    if(mangas.size() > 0) {
                        List<SelectMenuOption> options = new ArrayList<>();
                        for (Manga m : mangas)
                            options.add(SelectMenuOption.create(
                                    m.getAttributes().getTitle(language).length() > 25 ?
                                            m.getAttributes().getTitle(language).substring(0, 25)
                                            :
                                            m.getAttributes().getTitle(language),
                                    m.getId() + "##" + language));


                        interaction.createImmediateResponder()
                                .setContent("Make your choice.")
                                .addComponents(ActionRow.of(SelectMenu.create("search_manga", "Choose a manga", 1, 1, options)))
                                .respond().exceptionally(e1 -> {
                                    e1.printStackTrace();
                                    return null;
                                });
                    } else {
                        interaction.createImmediateResponder()
                                .setContent("No manga found.")
                                .respond();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    interaction.createImmediateResponder()
                            .setContent("Encountered an error while searching the manga.\nCheck the developer console.")
                            .respond();
                }
            }

            case "page" -> {
                Session session = SessionsManager.getInstance().getSession(key);
                if (session != null) {
                    int page = interaction.getArguments().get(0).getDecimalValue().orElse(0.0).intValue() - 1;

                    try {
                        if (page < 0) page = 0;
                        if (page >= session.currentChapterPagesCount())
                            page = session.currentChapterPagesCount() - 1;

                        session.setPage(page);
                        MangaReader reader = new MangaReader(key);
                        reader.sendReader(e.getInteraction());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        interaction.createImmediateResponder()
                                .setContent("Encountered an error while setting the page.\nCheck the developer console.")
                                .respond();
                    }
                } else {
                    interaction.createImmediateResponder()
                            .setContent("You must first select a manga.")
                            .respond();
                }
            }

            case "volume" -> {
                Session session = SessionsManager.getInstance().getSession(key);
                if (session != null) {
                    int volume = interaction.getArguments().get(0).getDecimalValue().orElse(0.0).intValue() - 1;

                    try {
                        if (volume < 0) volume = 0;
                        if (volume >= session.volumesCount())
                            volume = session.volumesCount() - 1;

                        session.setVolume(volume);
                        MangaReader reader = new MangaReader(key);
                        reader.sendReader(e.getInteraction());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        interaction.createImmediateResponder()
                                .setContent("Encountered an error while setting the volume.\nCheck the developer console.")
                                .respond();
                    }
                } else {
                    interaction.createImmediateResponder()
                            .setContent("You must first select a manga.")
                            .respond();
                }
            }
        }
    }
}
