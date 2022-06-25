package fr.carlens.murasaki.discord.commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.*;

import java.util.Arrays;
import java.util.List;

public class CommandsRegister {

    public static void registerCommands(DiscordApi api, String guildId) {
        var optionalServer = api.getServerById(guildId);
        if (optionalServer.isEmpty()) {
            System.out.println("Couldn't register commands. The given server is not found.");
            return;
        }

        buildSearchMangaCommand().createGlobal(api).join();
        buildSetPageCommand().createGlobal(api).join();
        buildSetVolumeCommand().createGlobal(api).join();
        System.out.println("Commands registered.");
    }

    public static void deleteGlobalsCommands(DiscordApi api) {
        List<SlashCommand> commands = api.getGlobalSlashCommands().join();
        for (SlashCommand command : commands) {
            System.out.println(command.getName());
            command.deleteGlobal();
        }
    }

    public static void deleteGuildCommands(DiscordApi api, Server server) {
        List<SlashCommand> commands = api.getServerSlashCommands(server).join();
        for (SlashCommand command : commands) {
            System.out.println(command.getName());
            command.deleteForServer(server);
        }
    }
    public static SlashCommandBuilder buildSearchMangaCommand() {
        return SlashCommand.with("search_manga", "Search a manga on Mangadex",
                Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "manga", "The manga title", true),
                        SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "language", "Translation language", true,
                                Arrays.asList(
                                        SlashCommandOptionChoice.create("FR", "fr"),
                                        SlashCommandOptionChoice.create("EN", "EN")))
                ));
    }

    public static SlashCommandBuilder buildSetPageCommand() {
        return SlashCommand.with("set_page", "Set the current page",
                Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.DECIMAL, "page", "The page number", true)
                ));
    }

    public static SlashCommandBuilder buildSetVolumeCommand() {
        return SlashCommand.with("set_volume", "Set the current volume",
                Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.DECIMAL, "volume", "The volume number", true)
                ));
    }
}
