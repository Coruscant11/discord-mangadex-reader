package fr.carlens.murasaki.discord.listeners;

import fr.carlens.murasaki.logic.api.MangadexClient;
import fr.carlens.murasaki.logic.api.models.Manga;
import fr.carlens.murasaki.logic.api.payloads.requests.SearchMangaRequest;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsListener implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent e) {
        var interaction = e.getSlashCommandInteraction();

        if (interaction.getChannel().isEmpty()) return;

        switch (interaction.getCommandName()) {

            case "search_manga" -> {

                String mangaTitle = interaction.getArguments().get(0).getStringValue().orElse("");
                String language = interaction.getArguments().get(1).getStringValue().orElse("");
                language = language.toLowerCase();

                MangadexClient client = new MangadexClient();
                try {
                    List<Manga> mangas = client.searchManga(new SearchMangaRequest(mangaTitle, 3));
                    if(mangas.size() > 0) {
                        List<SelectMenuOption> options = new ArrayList<>();
                        for (Manga m : mangas)
                            options.add(SelectMenuOption.create(m.getAttributes().getTitle().get(language), m.getId() + "##" + language));

                        interaction.createImmediateResponder()
                                .setContent("Make your choice.")
                                .addComponents(ActionRow.of(SelectMenu.create("search_manga", "Choose a manga", 1, 1, options)))
                                .respond();
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
        }
    }
}
