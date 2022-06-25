package fr.carlens.murasaki.discord;

import fr.carlens.murasaki.discord.commands.CommandsRegister;
import fr.carlens.murasaki.discord.listeners.ButtonsNavigationListener;
import fr.carlens.murasaki.discord.listeners.CommandsListener;
import fr.carlens.murasaki.discord.listeners.ListenersRegister;
import fr.carlens.murasaki.discord.listeners.SelectMenusChapterListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Bot {

    public Bot() {
        String token = System.getenv("DISCORD_MURASAKI_TOKEN");
        String guildId = System.getenv("DISCORD_MURASAKI_GUILDID");

        if (token == null || guildId == null) {
            System.err.println("Missing environment variables. Closing program.");
            return;
        }

        DiscordApi api = new DiscordApiBuilder()
                .setToken(token)
                .login()
                .join();

        System.out.println("Bot logged in. [" + api.getYourself().getMentionTag() + "]");

        CommandsRegister.deleteGlobalsCommands(api);
        CommandsRegister.deleteGuildCommands(api, api.getServerById(guildId).get());
        CommandsRegister.registerCommands(api, guildId);
        ListenersRegister.registerListeners(api);
    }
}
