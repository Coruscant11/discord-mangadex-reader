package fr.carlens.murasaki.logic.sessions;

import fr.carlens.murasaki.logic.api.APIException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class SessionsManagerTest {

    private static final String AGK_ID = "8946189d-682f-4838-9c2a-3c2dd5132f2c";

    @Test
    void testSessionManager() throws APIException, IOException, URISyntaxException {
        SessionsManager sm = SessionsManager.getInstance();

        SessionKey jpKey = new SessionKey(52775, 12345);
        SessionKey akaKey = new SessionKey(52775, 14523);

        sm.newSession(jpKey, AGK_ID, "en");

        Session jps = sm.getSession(jpKey);

        System.out.println("Current volume : " + jps.getCurrentVolume().getVolume());
        System.out.println("Current chapter : " + jps.getCurrentChapter().getChapter());
        System.out.println("Current page : " + jps.showCurrentPage());

        System.out.println(sm.count());
        assertNotNull(sm);
    }

}