package fr.carlens.murasaki.logic.sessions;

import fr.carlens.murasaki.logic.api.APIException;
import fr.carlens.murasaki.logic.api.MangadexClient;
import fr.carlens.murasaki.logic.api.models.ChapterAggregate;
import fr.carlens.murasaki.logic.api.models.ChapterAtHome;
import fr.carlens.murasaki.logic.api.models.VolumeAggregate;
import fr.carlens.murasaki.logic.api.payloads.requests.ChapterAtHomeRequest;
import fr.carlens.murasaki.logic.api.payloads.requests.ChapterRequest;
import fr.carlens.murasaki.logic.api.payloads.requests.MangaAggregateRequest;
import fr.carlens.murasaki.logic.api.payloads.responses.ChapterAtHomeResponse;
import fr.carlens.murasaki.logic.api.payloads.responses.ChapterResponse;
import fr.carlens.murasaki.logic.api.payloads.responses.MangaAggregateResponse;
import org.javacord.api.entity.message.component.SelectMenuOption;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Session {
    private final SessionKey sessionKey;
    private final String mangaId;
    private final String language;
    private List<VolumeAggregate> volumes;

    private Integer currentPage;
    private Integer currentChapterIndex;
    private Integer currentVolumeIndex;

    private MangadexClient client;

    public Session(SessionKey sessionKey, String mangaId, String language) {
        this.sessionKey = sessionKey;

        this.mangaId = mangaId;
        this.language = language;
        loadVolumes();
        var volume1 = ChapterAggregate.sortChaptersList(volumes.get(0).getChapters());
        var volume2 = ChapterAggregate.sortChaptersList(volumes.get(1).getChapters());
        this.currentPage = 0;
        this.currentVolumeIndex = 0;
        this.currentChapterIndex = 0;

        client = new MangadexClient();
    }

    private boolean hasTooBigVolumes(List<VolumeAggregate> volumes) {
        for (VolumeAggregate volume : volumes) {
            if (volume.getChapters().size() > 25) {
                return true;
            }
        }
        return false;
    }

    private void loadVolumes() {
        try {
            MangaAggregateResponse mar = new MangadexClient().getMangaAggregate(new MangaAggregateRequest(mangaId, language));
            this.volumes = VolumeAggregate.sortVolumesMap(mar.getVolumes());

            int i = 0;
            while(hasTooBigVolumes(volumes)) {
                var volume = volumes.get(i);
                var chapters = ChapterAggregate.sortChaptersList(volume.getChapters());
                volume.setChapters(new HashMap<>());

                if (chapters.size() > 25) {
                    VolumeAggregate newVol = new VolumeAggregate();
                    newVol.setChapters(new HashMap<>());

                    List<ChapterAggregate> chaptersToRemove = new ArrayList<>();
                    for (int x = 25; x < chapters.size(); x++)
                        chaptersToRemove.add(chapters.get(x));

                    for (ChapterAggregate ca : chaptersToRemove) {
                        chapters.remove(ca);
                        newVol.getChapters().put(ca.getChapter(), ca);
                    }

                    newVol.setVolume(volume.getVolume() + "(2)");
                    newVol.setCount(newVol.getChapters().size());
                    volumes.add(i+1, newVol);
                }
                for (ChapterAggregate c : chapters)
                    volume.getChapters().put(c.getChapter(), c);
                volume.setCount(volume.getChapters().size());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.volumes = null;
        }
    }

    public String showCurrentPage() throws APIException, IOException, URISyntaxException, IndexOutOfBoundsException {
        ChapterAtHomeResponse cahr = new MangadexClient().getChapterAtHome(new ChapterAtHomeRequest(getCurrentChapter().getId()));
        if (cahr.getResult().equalsIgnoreCase("ok")) {
            ChapterAtHome c = cahr.getChapter();
            return ChapterAtHome.buildPageUrl(ChapterAtHome.PageQuality.DATA, cahr.getBaseUrl(), c.getHash(), c.getData().get(currentPage));
        }
        return "";
    }

    public String getCurrentChapterTitle() throws APIException, IOException, URISyntaxException {
        return getChapterTitle(getCurrentChapter().getId());
    }

    public List<String> getCurrentVolumeChapterTitles() throws APIException, IOException, URISyntaxException, IndexOutOfBoundsException {
        List<String> titles = new ArrayList<>();
        for (ChapterAggregate ca : ChapterAggregate.sortChaptersList(getCurrentVolume().getChapters())) {
            String title = getChapterTitle(ca.getId());
            titles.add(ca.getChapter() + " : " + title);
        }
        return titles;
    }

    private String getChapterTitle(String mangaId) throws APIException, IOException, URISyntaxException {
        ChapterResponse c = client.getChapter(new ChapterRequest(mangaId));
        StringBuilder sb = new StringBuilder();
        if (c.getData().getAttributes().getTitle() != null && !c.getData().getAttributes().getTitle().equals(""))
            sb.append(c.getData().getAttributes().getTitle().length() > 25 ?
                    c.getData().getAttributes().getTitle().substring(0, 25)
                    :
                    c.getData().getAttributes().getTitle());
        else
            sb.append("No Title");
        return sb.toString();
    }

    public List<String> getCurrentVolumeChapterIds() {
        List<String> ids = new ArrayList<>();
        for (ChapterAggregate ca : ChapterAggregate.sortChaptersList(getCurrentVolume().getChapters())) {
            ids.add(ca.getId());
        }
        return ids;
    }

    public int currentChapterPagesCount() throws APIException, IOException, URISyntaxException, IndexOutOfBoundsException {
        ChapterResponse c = client.getChapter(new ChapterRequest(getCurrentChapter().getId()));
        return c.getData().getAttributes().getPages();
    }

    public ChapterAggregate getCurrentChapter() throws IndexOutOfBoundsException {
        return ChapterAggregate.sortChaptersList(volumes.get(currentVolumeIndex).getChapters()).get(currentChapterIndex);
    }

    public VolumeAggregate getCurrentVolume() throws IndexOutOfBoundsException {
        return volumes.get(currentVolumeIndex);
    }

    public boolean hasVolumes() {
        return volumes != null && volumes.size() > 0;
    }

    public int volumesCount() {
        return volumes.size();
    }

    public int currentVolumeChaptersCount() {
        return getCurrentVolume().getChapters().size();
    }

    public void nextPage() {
        try {
            currentPage = currentPage + 1 < currentChapterPagesCount() ? currentPage + 1 : currentPage;
        } catch(Exception e) {e.printStackTrace();}
    }

    public void setPage(int newPage) throws APIException, IOException, URISyntaxException {
        try {
            currentPage = newPage >= 0 && newPage < currentChapterPagesCount() ? newPage : currentPage;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void previousPage() {
        currentPage = currentPage - 1 < 0 ? currentPage : currentPage - 1;
    }

    public void nextChapter() {
        currentChapterIndex = currentChapterIndex + 1 < currentVolumeChaptersCount() ? currentChapterIndex + 1 : currentChapterIndex;
        currentPage = 0;
    }

    public void setChapter(int newChapter) {
        currentChapterIndex = newChapter >= 0 && newChapter < currentVolumeChaptersCount() ? newChapter : currentChapterIndex;
        currentPage = 0;
    }

    public void setChapterById(String chapterId) {
        for (ChapterAggregate ca : ChapterAggregate.sortChaptersList(getCurrentVolume().getChapters())) {
            if (ca.getId().equals(chapterId)) {
                currentChapterIndex = ChapterAggregate.sortChaptersList(getCurrentVolume().getChapters()).indexOf(ca);
                currentPage = 0;
                return;
            }
        }
    }

    public void previousChapter() {
        currentChapterIndex = currentChapterIndex - 1 < 0 ? currentChapterIndex : currentChapterIndex + 1;
        currentPage = 0;
    }

    public void nextVolume() {
        currentVolumeIndex = currentVolumeIndex + 1 < volumesCount() ? currentVolumeIndex + 1 : currentVolumeIndex;
        currentChapterIndex = 0;
        currentPage = 0;
    }

    public void setVolume(int volume) {
        currentVolumeIndex = volume >= 0 && volume < volumesCount() ? volume : currentVolumeIndex;
        currentPage = 0;
    }

    public void previousVolume() {
        currentVolumeIndex = currentVolumeIndex - 1 < 0 ? currentVolumeIndex : currentVolumeIndex - 1;
        currentPage = 0;
    }

    public SessionKey getSessionKey() {
        return sessionKey;
    }

    public String getMangaId() {
        return mangaId;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getCurrentChapterIndex() {
        return currentChapterIndex;
    }

    public Integer getCurrentVolumeIndex() {
        return currentVolumeIndex;
    }

    public List<VolumeAggregate> getVolumes() {
        return volumes;
    }
}
