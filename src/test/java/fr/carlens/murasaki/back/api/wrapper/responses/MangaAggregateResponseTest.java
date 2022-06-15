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
            MangaAggregateRequest mar = new MangaAggregateRequest("8946189d-682f-4838-9c2a-3c2dd5132f2c", "en");
            Map<String, VolumeAggregate> volumes = client.getMangaAggregate(mar);


            assertTrue(volumes.size() > 0);

            VolumeAggregate firstVolume = volumes.values().iterator().next();
            assertNotNull(firstVolume.getVolume());
            assertTrue(firstVolume.getCount() > 0);
            assertTrue(firstVolume.getChapters().size() > 0);
            assertNotNull(firstVolume.getChapters().values().iterator().next().getId());
        } catch (Exception e) {
            fail(e);
        }
    }

}