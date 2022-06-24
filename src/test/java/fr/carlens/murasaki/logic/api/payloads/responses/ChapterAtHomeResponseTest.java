package fr.carlens.murasaki.logic.api.payloads.responses;

import fr.carlens.murasaki.logic.api.MangadexClient;
import fr.carlens.murasaki.logic.api.models.ChapterAggregate;
import fr.carlens.murasaki.logic.api.models.ChapterAtHome;
import fr.carlens.murasaki.logic.api.models.Manga;
import fr.carlens.murasaki.logic.api.models.VolumeAggregate;
import fr.carlens.murasaki.logic.api.payloads.requests.ChapterAtHomeRequest;
import fr.carlens.murasaki.logic.api.payloads.requests.MangaAggregateRequest;
import fr.carlens.murasaki.logic.api.payloads.requests.MangaRequest;
import fr.carlens.murasaki.logic.api.payloads.responses.ChapterAtHomeResponse;
import fr.carlens.murasaki.logic.api.payloads.responses.MangaAggregateResponse;
import fr.carlens.murasaki.logic.api.payloads.responses.MangaResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChapterAtHomeResponseTest {

    @Test
    void testAkameGaKillFirstChapter() {
        String agkId = "8946189d-682f-4838-9c2a-3c2dd5132f2c";

        MangadexClient client = new MangadexClient();
        try {
            MangaResponse mr = client.getManga(new MangaRequest(agkId));
            assertEquals("ok", mr.getResult());

            Manga agkManga = mr.getData();
            MangaAggregateResponse mar = client.getMangaAggregate(new MangaAggregateRequest(agkId, "en"));

            assertEquals("ok", mar.getResult());
            assertNotNull(mar.getVolumes());

            var volumes = mar.getVolumes();
            assertTrue(volumes.size() > 0);

            var sortedVolumes = VolumeAggregate.sortVolumesMap(volumes);
            assertTrue(sortedVolumes.size() > 0);

            VolumeAggregate firstVolume = sortedVolumes.iterator().next();
            assertEquals("1", firstVolume.getVolume());
            var chapters = sortedVolumes.get(0);
            var sortedChapters = ChapterAggregate.sortChaptersList(chapters.getChapters());
            assertTrue(sortedChapters.size() > 0);

            ChapterAtHomeResponse cahr = client.getChapterAtHome(new ChapterAtHomeRequest(sortedChapters.get(0).getId()));
            assertEquals("ok", cahr.getResult());
            assertNotNull(cahr.getBaseUrl());
            assertNotNull(cahr.getChapter());

            ChapterAtHome cah = cahr.getChapter();
            assertNotNull(cah.getHash());
            assertNotNull(cah.getData());
            assertNotNull(cah.getDataSaver());

            assertTrue(cah.getData().size() > 0);
            var page = ChapterAtHome.buildPageUrl(ChapterAtHome.PageQuality.DATA, cahr.getBaseUrl(), cah.getHash(), cah.getData().get(0));
            assertNotNull(page);
        } catch (Exception e) {
            fail(e);
        }
    }
}