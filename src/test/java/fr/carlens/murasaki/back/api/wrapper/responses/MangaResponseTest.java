package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.MangadexClient;
import fr.carlens.murasaki.back.api.wrapper.models.Manga;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaRandomRequest;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaRequest;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class MangaResponseTest {

    @Test
    void testRandomMangaResponse() throws URISyntaxException  {
        MangadexClient client = new MangadexClient();
        try {
            MangaResponse mangar = client.getRandomManga();

            assertEquals("ok", mangar.getResult());

            if(mangar.getResult().equalsIgnoreCase("ok")) {
                Manga manga = mangar.getData();

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