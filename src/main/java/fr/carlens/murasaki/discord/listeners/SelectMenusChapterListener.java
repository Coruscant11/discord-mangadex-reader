package fr.carlens.murasaki.discord.listeners;

import fr.carlens.murasaki.ConsoleColors;
import fr.carlens.murasaki.discord.Bot;
import fr.carlens.murasaki.discord.reader.MangaReader;
import fr.carlens.murasaki.logic.sessions.Session;
import fr.carlens.murasaki.logic.sessions.SessionKey;
import fr.carlens.murasaki.logic.sessions.SessionsManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SelectMenuChooseEvent;
import org.javacord.api.listener.interaction.SelectMenuChooseListener;

import java.time.ZoneId;

public class SelectMenusChapterListener implements SelectMenuChooseListener {

    @Override
    public void onSelectMenuChoose(SelectMenuChooseEvent selectMenuChooseEvent) {
        var interaction = selectMenuChooseEvent.getSelectMenuInteraction();
        Bot.logInteraction(selectMenuChooseEvent.getInteraction(), interaction.getCustomId());

        TextChannel channel = interaction.getChannel().get();
        User user = interaction.getUser();
        SessionKey key = new SessionKey(channel.getId(), user.getId());

        switch (interaction.getCustomId()) {
            case "search_manga" -> {
                var values = interaction.getChosenOptions().get(0).getValue().split("##");
                String mangaId = values[0];
                String language = values[1];

                MangaReader reader = new MangaReader(key, mangaId, language);
                reader.sendReader(selectMenuChooseEvent.getInteraction());
                interaction.getMessage().delete();
            }
            case "chapter_choice" -> {
                if (SessionsManager.getInstance().getSession(key) != null) {
                    Session session = SessionsManager.getInstance().getSession(key);
                    session.setChapterById(interaction.getChosenOptions().get(0).getValue());

                    MangaReader reader = new MangaReader(key);
                    reader.sendReader(selectMenuChooseEvent.getInteraction());
                    interaction.getMessage().delete();
                } else {
                    interaction.createImmediateResponder()
                            .setContent("You are not allowed to use this reader.")
                            .respond();
                }
            }
        }
    }
}
