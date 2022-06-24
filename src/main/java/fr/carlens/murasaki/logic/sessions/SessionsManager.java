package fr.carlens.murasaki.logic.sessions;

import java.util.concurrent.ConcurrentHashMap;

public class SessionsManager {

    private static SessionsManager instance;

    private ConcurrentHashMap<SessionKey, Session> sessions;
    private SessionsManager() {
        sessions = new ConcurrentHashMap<>();
    }

    public void newSession(SessionKey key, String mangaId, String language) {
        if (!sessions.containsKey(key)) {
            sessions.put(key, new Session(key, mangaId, language));
        }
    }

    public Session getSession(SessionKey key) {
        for (Session session : sessions.values()) {
            if (session.getSessionKey().equals(key)) {
                return session;
            }
        }
        return null;
    }

    public int count() {
        return sessions.size();
    }

    public static SessionsManager getInstance() {
        if(instance == null) {
            instance = new SessionsManager();
        }
        return instance;
    }
}
