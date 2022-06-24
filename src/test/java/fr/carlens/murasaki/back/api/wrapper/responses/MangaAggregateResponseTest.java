package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.MangadexClient;
import fr.carlens.murasaki.back.api.wrapper.models.Manga;
import fr.carlens.murasaki.back.api.wrapper.models.VolumeAggregate;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaAggregateRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MangaAggregateResponseTest {

    @Test
    void testMangaAggregateFromRandom() {
        MangadexClient client = new MangadexClient();
        try {
            MangaResponse mangar = client.getRandomManga();

            assertEquals("ok", mangar.getResult());
            if(mangar.getResult().equalsIgnoreCase("ok")) {
                Manga m = mangar.getData();
                System.out.println("Random manga : " + m.getId());

                MangaAggregateRequest mar = new MangaAggregateRequest(m.getId(), "en");
                MangaAggregateResponse mangaar = client.getMangaAggregate(mar);
                assertEquals("ok", mangaar.getResult());

                if(mangaar.getResult().equalsIgnoreCase("ok") && mangaar.getVolumes() != null) {
                    Map<String, VolumeAggregate> volumes = mangaar.getVolumes();


                    assertTrue(volumes.size() > 0);

                    VolumeAggregate firstVolume = volumes.values().iterator().next();
                    assertNotNull(firstVolume.getVolume());
                    assertTrue(firstVolume.getCount() > 0);
                    assertTrue(firstVolume.getChapters().size() > 0);
                    assertNotNull(firstVolume.getChapters().values().iterator().next().getId());
                }
                else {
                    System.out.println("Volumes null.");
                }
            }
        } catch (Exception e) {
            fail(e);
        }
    }

}