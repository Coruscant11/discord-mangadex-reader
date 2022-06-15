package fr.carlens.murasaki;

import fr.carlens.murasaki.front.commands.CommandsRegister;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {

    public static void main(String[] args) {
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

        CommandsRegister.registerCommands(api, guildId);
    }
}
