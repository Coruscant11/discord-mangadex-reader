package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.MangadexClient;
import fr.carlens.murasaki.back.api.wrapper.models.Manga;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaRequest;
import fr.carlens.murasaki.back.api.wrapper.requests.SearchMangaRequest;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MangaListResponseTest {

    @Test
    void testFromSwordMangaList() throws URISyntaxException {
        SearchMangaRequest smr = new SearchMangaRequest("sword", new Random().nextInt(10) + 1);
        System.out.println(smr.buildUrl());

        MangadexClient client = new MangadexClient();
        try {
            List<Manga> mangas = client.searchManga(smr);

            assertTrue(mangas.size() > 0);

            for (Manga manga : mangas) {
                assertNotNull(manga.getId());
                assertNotNull(manga.getAttributes());
                assertEquals("manga", manga.getType());
                assertTrue(manga.getAttributes().getTitle().size() > 0);
            }
        } catch (Exception e) {
            fail(e);
        }
    }
}