package fi.iki.mkuokkanen.seda.api.session;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements Session Manager
 * 
 * @author mkuokkanen
 */
public class SessionManagerImpl implements SessionManager {

    private static Logger logger = LoggerFactory.getLogger(SessionManagerImpl.class);

    /**
     * Store
     */
    private final Set<Session> sessions;

    /**
     * Default constructor
     */
    public SessionManagerImpl() {
        sessions = new HashSet<>();
    }

    @Override
    public void join(Session s) {
        logger.info("Session joined: {}", s.getRemoteAddress());
        sessions.add(s);
    }

    @Override
    public void part(Session s) {
        logger.info("Session parted: {}", s.getRemoteAddress());
        sessions.remove(s);
    }

    @Override
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
