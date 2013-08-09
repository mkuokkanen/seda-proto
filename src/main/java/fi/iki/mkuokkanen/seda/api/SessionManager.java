package fi.iki.mkuokkanen.seda.api;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mkuokkanen
 */
public class SessionManager {

    private static Logger logger = LoggerFactory.getLogger(SessionManager.class);

    /**
     * Singleton
     */
    public static final SessionManager instance = new SessionManager();
    
    /**
     * Store
     */
    private final Set<Session> sessions;

    /**
     * Private constructor
     */
    private SessionManager() {
        sessions = new HashSet<>();
    }

    public void join(Session s) {
        logger.info("Session joined: {}", s.getRemoteAddress());
        sessions.add(s);
    }
    
    public void part(Session s) {
        logger.info("Session parted: {}", s.getRemoteAddress());
        sessions.remove(s);
    }

    public void sendAll(String str) {
        for (Session each : sessions) {
            logger.info("Msg sended {}, {}", str, each.getRemoteAddress());
            try {
                each.getRemote().sendString(str);
            } catch (IOException e) {
                logger.error("error while sending", e);
            }
        }
    }

}
