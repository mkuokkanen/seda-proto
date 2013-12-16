package fi.iki.mkuokkanen.seda.api.session;

import org.eclipse.jetty.websocket.api.Session;

/**
 * Interface for SessionManager.
 * http://stackoverflow.com/questions/15646213/how-do-i-access-instantiated-
 * websockets-in-jetty-9
 * 
 * @author mkuokkanen
 */
public interface SessionManager {

    /**
     * Add session.
     * 
     * @param s
     */
    void join(Session s);

    /**
     * Remove sessions.
     * 
     * @param s
     */
    void part(Session s);

    /**
     * Send message to all sessions. Maybe not best place for this?
     * 
     * @param str
     */
    void sendAll(String str);

}