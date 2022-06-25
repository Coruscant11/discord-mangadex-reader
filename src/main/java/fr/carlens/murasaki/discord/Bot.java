package fr.carlens.murasaki.discord;

import fr.carlens.murasaki.ConsoleColors;
import fr.carlens.murasaki.discord.commands.CommandsRegister;
import fr.carlens.murasaki.discord.listeners.ButtonsNavigationListener;
import fr.carlens.murasaki.discord.listeners.CommandsListener;
import fr.carlens.murasaki.discord.listeners.ListenersRegister;
import fr.carlens.murasaki.discord.listeners.SelectMenusChapterListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.Interaction;

import java.time.ZoneId;

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

        System.out.println("Bot logged in. [" + api.getYourself().getDiscriminatedName() + "]");

//        CommandsRegister.deleteGlobalsCommands(api);
//        CommandsRegister.deleteGuildCommands(api, api.getServerById(guildId).get());
//        CommandsRegister.registerCommands(api, guildId);
        ListenersRegister.registerListeners(api);
    }

    public static void logInteraction(Interaction interaction, String interactionName) {
        System.out.println("\n["
                + ConsoleColors.PURPLE + interaction.getType().name()
                + ConsoleColors.RESET + " -> "
                + ConsoleColors.RED + interactionName
                + ConsoleColors.RESET
                + "]");

        System.out.println("\tAuthor : " + ConsoleColors.GREEN + interaction.getUser().getDiscriminatedName() + ConsoleColors.RESET);

        if (interaction.getChannel().isPresent() && interaction.getChannel().get().asServerTextChannel().isPresent())
            System.out.println("\tChannel : " + ConsoleColors.YELLOW + interaction.getChannel().get().asServerTextChannel().get().getName() + ConsoleColors.RESET);

        System.out.println("\tTime : " + interaction.getCreationTimestamp().atZone(ZoneId.systemDefault()));
    }
}
