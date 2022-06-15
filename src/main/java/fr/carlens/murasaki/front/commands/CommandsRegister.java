package fr.carlens.murasaki.front.commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.*;

import java.util.Arrays;

public class CommandsRegister {

    public static void registerCommands(DiscordApi api, String guildId) {
        var optionalServer = api.getServerById(guildId);
        if (optionalServer.isEmpty()) {
            System.out.println("Couldn't register commands. The given server is not found.");
            return;
        }

        Server server = optionalServer.get();
        System.out.println(buildSearchMangaCommand().createForServer(server).join());
        System.out.println("Commands registered.");
    }

    public static SlashCommandBuilder buildSearchMangaCommand() {
        return SlashCommand.with("search_manga", "Search a manga on Mangadex",
                Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "manga", "The manga title", true),
                        SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "language", "Translation language", true,
                                Arrays.asList(
                                        SlashCommandOptionChoice.create("EN", "EN"),
                                        SlashCommandOptionChoice.create("FR", "fr")))
                ));
    }
}
