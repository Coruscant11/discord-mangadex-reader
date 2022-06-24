package fr.carlens.murasaki.logic.api.payloads.responses;

import fr.carlens.murasaki.logic.api.APIException;
import fr.carlens.murasaki.logic.api.MangadexClient;
import fr.carlens.murasaki.logic.api.models.Chapter;
import fr.carlens.murasaki.logic.api.models.ChapterAggregate;
import fr.carlens.murasaki.logic.api.models.Manga;
import fr.carlens.murasaki.logic.api.models.VolumeAggregate;
import fr.carlens.murasaki.logic.api.payloads.requests.ChapterRequest;
import fr.carlens.murasaki.logic.api.payloads.requests.MangaAggregateRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class ChapterResponseTest {

    @Test
    void testFirstChapterAgk() throws APIException, IOException, URISyntaxException {
        MangadexClient client = new MangadexClient();
        String agk = "8946189d-682f-4838-9c2a-3c2dd5132f2c";

        var volumesResponse = client.getMangaAggregate(new MangaAggregateRequest(agk, "en"));
        assertEquals("ok", volumesResponse.getResult());
        var sortedVolumes = VolumeAggregate.sortVolumesMap(volumesResponse.getVolumes());
        var firstVolumesChapters = ChapterAggregate.sortChaptersList(sortedVolumes.get(0).getChapters());
        var firstChapterAggregate = firstVolumesChapters.get(0);

        assertTrue(firstChapterAggregate.getCount() > 0);
        assertEquals("1", firstChapterAggregate.getChapter());

        ChapterResponse cr = client.getChapter(new ChapterRequest(firstChapterAggregate.getId()));
        assertEquals("ok", cr.getResult());

        Chapter c = cr.getData();
        assertEquals(firstChapterAggregate.getId(), c.getId());
        assertNotNull(c.getAttributes().getTitle());
        assertNotNull(c.getAttributes());
        assertTrue(c.getAttributes().getPages() > 0);
        assertEquals(sortedVolumes.get(0).getVolume(), c.getAttributes().getVolume());
    }

    @Test
    void testFirstChapterRandomManga() throws APIException, IOException, URISyntaxException {
        MangadexClient client = new MangadexClient();
        boolean success = false;

        while (!success) {
            MangaResponse randomManga = client.getRandomManga();
            assertEquals("ok", randomManga.getResult());

            var volumesResponse = client.getMangaAggregate(new MangaAggregateRequest(randomManga.getData().getId(), "en"));
            assertEquals("ok", volumesResponse.getResult());

            if (volumesResponse.getVolumes() != null) {
                success = true;
                var sortedVolumes = VolumeAggregate.sortVolumesMap(volumesResponse.getVolumes());
                var firstVolumesChapters = ChapterAggregate.sortChaptersList(sortedVolumes.get(0).getChapters());
                var firstChapterAggregate = firstVolumesChapters.get(0);

                assertTrue(firstChapterAggregate.getCount() > 0);

                ChapterResponse cr = client.getChapter(new ChapterRequest(firstChapterAggregate.getId()));
                assertEquals("ok", cr.getResult());

                Chapter c = cr.getData();
                System.out.println("Manga id : " + randomManga.getData().getId());
                System.out.println("Manga name : " + randomManga.getData().getAttributes().getTitle().get("en"));
                System.out.println("Volume id : " + sortedVolumes.get(0).getVolume());
                System.out.println("chapter aggregate number : " + firstChapterAggregate.getChapter());
                System.out.println("chapter aggregate id : " + firstChapterAggregate.getId());
                System.out.println("chapterId : " + c.getId());
                assertEquals(firstChapterAggregate.getId(), c.getId());
                assertNotNull(c.getAttributes());
                assertTrue(c.getAttributes().getPages() > 0);
            }
        }
    }
}