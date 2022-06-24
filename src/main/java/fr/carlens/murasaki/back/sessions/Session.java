package fr.carlens.murasaki.back.sessions;

import fr.carlens.murasaki.back.api.APIException;
import fr.carlens.murasaki.back.api.MangadexClient;
import fr.carlens.murasaki.back.api.wrapper.models.ChapterAggregate;
import fr.carlens.murasaki.back.api.wrapper.models.ChapterAtHome;
import fr.carlens.murasaki.back.api.wrapper.models.VolumeAggregate;
import fr.carlens.murasaki.back.api.wrapper.requests.ChapterAtHomeRequest;
import fr.carlens.murasaki.back.api.wrapper.responses.ChapterAtHomeResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Session {
    private Long channelId;
    private String mangaId;
    private List<VolumeAggregate> volumes;

    private Integer currentPage;

    private Integer currentChapterIndex;
    private String currentChapterId;

    private int currentVolumeIndex;

    public Session(long channelId, String mangaId, List<VolumeAggregate> volumes) {
        this.channelId = channelId;
        this.mangaId = mangaId;
        this.volumes = volumes;

        this.currentPage = 0;
        this.currentVolumeIndex = 0;
        this.currentChapterIndex = 0;
    }

    public ChapterAggregate getCurrentChapter() throws IndexOutOfBoundsException {
        return ChapterAggregate.sortChaptersList(volumes.get(currentVolumeIndex).getChapters()).get(currentChapterIndex);
    }

    public VolumeAggregate getCurrentVolume() throws IndexOutOfBoundsException {
        return volumes.get(currentVolumeIndex);
    }

    public String showCurrentPage() throws APIException, IOException, URISyntaxException, IndexOutOfBoundsException {
        ChapterAtHomeResponse cahr = new MangadexClient().getChapterAtHome(new ChapterAtHomeRequest(getCurrentChapter().getId()));
        if (cahr.getResult().equalsIgnoreCase("ok")) {
            ChapterAtHome c = cahr.getChapter();
            return ChapterAtHome.buildPageUrl(ChapterAtHome.PageQuality.DATA, cahr.getBaseUrl(), c.getHash(), c.getData().get(currentChapterIndex));
        }
        return "";
    }

    public void nextPage() {
        currentPage = currentPage + 1 < getCurrentChapter().getCount() ? currentPage + 1 : currentPage;
    }

    public void previousPage() {
        currentPage = currentPage - 1 < 0 ? currentPage : currentPage - 1;
    }

    public void nextChapter() {
        currentChapterIndex = currentChapterIndex + 1 < getCurrentVolume().getCount() ? currentChapterIndex + 1 : currentChapterIndex;
    }

    public void previousChapter() {
        currentChapterIndex = currentChapterIndex - 1 < 0 ? currentChapterIndex : currentChapterIndex + 1;
    }

    public void nextVolume() {
        currentVolumeIndex = currentVolumeIndex + 1 < volumes.size() ? currentVolumeIndex + 1 : currentVolumeIndex;
    }

    public void previousVolume() {
        currentVolumeIndex = currentVolumeIndex - 1 < 0 ? currentVolumeIndex : currentVolumeIndex - 1;
    }

    public long getChannelId() {
        return channelId;
    }

    public String getMangaId() {
        return mangaId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentChapterIndex() {
        return currentChapterIndex;
    }

    public String getCurrentChapterId() {
        return currentChapterId;
    }

    public int getCurrentVolumeIndex() {
        return currentVolumeIndex;
    }
}
