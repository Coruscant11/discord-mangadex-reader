package fr.carlens.murasaki.discord.listeners;

import org.javacord.api.DiscordApi;

public class ListenersRegister {

    public static void registerListeners(DiscordApi api) {
        api.addListener(new ButtonsNavigationListener());
        api.addListener(new CommandsListenener());
        api.addListener(new SelectMenusChapterListener());
    }
}
