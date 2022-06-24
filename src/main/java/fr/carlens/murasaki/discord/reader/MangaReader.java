package fr.carlens.murasaki.discord.reader;

import fr.carlens.murasaki.logic.api.APIException;
import fr.carlens.murasaki.logic.sessions.Session;
import fr.carlens.murasaki.logic.sessions.SessionKey;
import fr.carlens.murasaki.logic.sessions.SessionsManager;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.Interaction;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MangaReader {

    private final SessionKey key;

    public MangaReader(SessionKey key) {
        this.key = key;
    }

    public MangaReader(SessionKey key, String mangaId, String language) {
        this(key);
        SessionsManager.getInstance().newSession(key, mangaId, language);
    }

    private Session getSession() {
        return SessionsManager.getInstance().getSession(key);
    }

    public void sendReader(Interaction interaction) {
        try {
            interaction.createImmediateResponder()
                    .addEmbed(buildPageEmbed())
                    .addComponents(buildChapterChoiceMenu(), buildNavigationsButtons())
                    .respond();

            new MessageBuilder().addAttachment(new URL(SessionsManager.getInstance().getSession(key).showCurrentPage())).send(interaction.getChannel().get());
        } catch (Exception e) {
            e.printStackTrace();
            interaction.createImmediateResponder()
                    .setContent("Encountered an error while reading the manga.\nCheck the developer console.")
                    .respond();
        }
    }
    public MessageBuilder buildReader() throws APIException, IOException, URISyntaxException {
        return new MessageBuilder()
                .addEmbed(buildPageEmbed())
                .addComponents(buildChapterChoiceMenu(), buildNavigationsButtons());
    }

    private EmbedBuilder buildPageEmbed() throws APIException, IOException, URISyntaxException {
        String imageUrl = getSession().showCurrentPage();

        return new EmbedBuilder()
                .setTitle(getSession().getCurrentChapter().getChapter() + " : " + getSession().getCurrentChapterTitle())
                .setDescription(String.format("Page %d/%d", getSession().getCurrentPage() + 1, getSession().currentChapterPagesCount()))
                .setUrl(imageUrl)
                .setTimestampToNow()
                .setColor(Color.MAGENTA)
                .setImage(imageUrl)
                .setFooter(String.format("Volume %d/%d", getSession().getCurrentVolumeIndex() + 1, getSession().volumesCount()));
    }
    private ActionRow buildChapterChoiceMenu() throws APIException, IOException, URISyntaxException {
        return ActionRow.of(SelectMenu.create("chapter_choice", "Choose a chapter", 1, 1, buildChapterOptions()));
    }

    private List<SelectMenuOption> buildChapterOptions() throws APIException, IOException, URISyntaxException {
        List<String> titles = getSession().getCurrentVolumeChapterTitles();
        List<String> ids = getSession().getCurrentVolumeChapterIds();
        List<SelectMenuOption> options = new ArrayList<>();

        for(int i = 0; i < titles.size(); i++)
            options.add(SelectMenuOption.create(titles.get(i), ids.get(i)));

        return options;
    }

    private ActionRow buildNavigationsButtons() throws APIException, IOException, URISyntaxException {
        boolean validPreviousPage = getSession().getCurrentPage() > 0;
        boolean validPreviousVolume = getSession().getCurrentVolumeIndex() > 0;
        boolean validNextVolume = getSession().volumesCount() > 1;
        boolean validNextPage = getSession().currentChapterPagesCount() > 1 && getSession().getCurrentPage() + 1 < getSession().currentChapterPagesCount();

        return ActionRow.of(
                Button.secondary("button_previous_volume", "Previous volume", !validPreviousVolume),
                Button.primary("button_previous_page", "Previous page", "⬅", !validPreviousPage),
                Button.primary("button_next_page", "Next page", "➡", !validNextPage),
                Button.secondary("button_next_volume", "Next volume", !validNextVolume),
                Button.danger("page_problem", "Send page again")
        );
    }
}
