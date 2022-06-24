package fr.carlens.murasaki.logic.api.payloads.responses;

import fr.carlens.murasaki.logic.api.MangadexClient;
import fr.carlens.murasaki.logic.api.models.Manga;
import fr.carlens.murasaki.logic.api.payloads.responses.MangaResponse;
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