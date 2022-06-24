package fr.carlens.murasaki.logic.sessions;

import fr.carlens.murasaki.logic.api.APIException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class SessionsManagerTest {

    private static final String AGK_ID = "8946189d-682f-4838-9c2a-3c2dd5132f2c";
    private static SessionKey jpKey;
    private static SessionsManager sm;
    private static Session jps;
    @BeforeAll
    static void createKeys() {
        jpKey = new SessionKey(52775, 12345);
        sm = SessionsManager.getInstance();
        sm.newSession(jpKey, AGK_ID, "en");
        jps = sm.getSession(jpKey);
    }

    @Test
    void testSessionManager() throws APIException, IOException, URISyntaxException {
        assertNotNull(sm);
        assertNotNull(jps.getCurrentVolume().getVolume());
        assertNotNull(jps.getCurrentChapter().getChapter());
        assertNotNull(jps.showCurrentPage());
    }

    @Test
    void testVolumesTitles() throws APIException, IOException, URISyntaxException {
        var titles = jps.getCurrentVolumeChapterTitles();
        assertTrue(titles.size() > 0);
    }

    @Test
    void testNextVolumes() throws APIException, IOException, URISyntaxException {
        var titles = jps.getCurrentVolumeChapterTitles();
        jps.nextVolume();
        titles = jps.getCurrentVolumeChapterTitles();
    }

    @Test
    void testPreviousVolumeTo0() {
        while(jps.getCurrentVolumeIndex() > 0)
            jps.previousVolume();

        int currentVolumeIndex = jps.getCurrentVolumeIndex();
        jps.previousVolume();
        assertEquals(currentVolumeIndex, jps.getCurrentVolumeIndex());
    }

    @Test
    void testSetVolumes() {
        int currentVolume = jps.getCurrentVolumeIndex();
        jps.setVolume(-50);
        assertEquals(currentVolume, jps.getCurrentVolumeIndex());
        jps.setVolume(Integer.MAX_VALUE);
        assertEquals(currentVolume, jps.getCurrentVolumeIndex());

        jps.setVolume(jps.volumesCount() - 1);
        assertEquals(jps.volumesCount() - 1, jps.getCurrentVolumeIndex());
        assertNotEquals(currentVolume, jps.getCurrentVolumeIndex());
    }

    @Test
    void testSetPages() throws APIException, IOException, URISyntaxException {
        int currentPage = jps.getCurrentPage();
        jps.setPage(-50);
        assertEquals(currentPage, jps.getCurrentPage());
        jps.setPage(Integer.MAX_VALUE);
        assertEquals(currentPage, jps.getCurrentPage());

        jps.setPage(jps.currentChapterPagesCount() - 1);
        assertEquals(jps.currentChapterPagesCount() - 1, jps.getCurrentPage());
        assertNotEquals(currentPage, jps.getCurrentPage());
    }

    @Test
    void testSetChapters() {
        int currentChapter = jps.getCurrentChapterIndex();
        jps.setChapter(-50);
        assertEquals(currentChapter, jps.getCurrentChapterIndex());
        jps.setChapter(Integer.MAX_VALUE);
        assertEquals(currentChapter, jps.getCurrentChapterIndex());

        jps.setChapter(jps.currentVolumeChaptersCount() - 1);
        assertEquals(jps.currentVolumeChaptersCount() - 1, jps.getCurrentChapterIndex());
        assertNotEquals(currentChapter, jps.getCurrentChapterIndex());
    }
}