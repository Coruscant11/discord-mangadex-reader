package fr.carlens.murasaki.discord.listeners;

import fr.carlens.murasaki.discord.Bot;
import fr.carlens.murasaki.discord.reader.MangaReader;
import fr.carlens.murasaki.logic.sessions.SessionKey;
import fr.carlens.murasaki.logic.sessions.SessionsManager;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.interaction.ButtonClickEvent;
import org.javacord.api.listener.interaction.ButtonClickListener;

import java.net.URL;

public class ButtonsNavigationListener implements ButtonClickListener {
    @Override
    public void onButtonClick(ButtonClickEvent buttonClickEvent) {
        var interaction = buttonClickEvent.getButtonInteraction();
        Bot.logInteraction(buttonClickEvent.getInteraction(), interaction.getCustomId());

        var user = interaction.getUser();
        var channel = interaction.getChannel().get();
        SessionKey key = new SessionKey(channel.getId(), user.getId());

        String[] idsplit = interaction.getCustomId().split("##");
        String buttonId = idsplit[0];
        String buttonUserId  = "";
        if(idsplit.length > 1)
            buttonUserId = idsplit[1];
        if (buttonId.equalsIgnoreCase("delete_session") && buttonUserId.equalsIgnoreCase(String.valueOf(user.getId()))) {
            SessionsManager.getInstance().removeSession(key);
            interaction.getMessage().delete();
            return;
        }

        if (SessionsManager.getInstance().getSession(key) != null) {

            switch (buttonId) {
                case "button_previous_volume" -> SessionsManager.getInstance().getSession(key).previousVolume();
                case "button_next_volume" -> SessionsManager.getInstance().getSession(key).nextVolume();
                case "button_previous_page" -> SessionsManager.getInstance().getSession(key).previousPage();
                case "button_next_page" -> SessionsManager.getInstance().getSession(key).nextPage();
                case "page_problem" -> {}
            }

            MangaReader reader = new MangaReader(key);
            reader.sendReader(buttonClickEvent.getInteraction());
            interaction.getMessage().delete();
        } else {
            interaction.createImmediateResponder()
                    .setContent("You are not allowed to use this reader.")
                    .respond();
        }
    }
}
