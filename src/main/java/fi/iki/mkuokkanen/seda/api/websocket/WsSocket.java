package fi.iki.mkuokkanen.seda.api.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.api.ApiToDisruptor;
import fi.iki.mkuokkanen.seda.api.SessionManager;

/**
 * One instance per session, i presume.
 * 
 * @author mkuokkanen
 */
public class WsSocket implements WebSocketListener {

    private static Logger logger = LoggerFactory.getLogger(WsSocket.class);

    private Session session;

    @Override
    public void onWebSocketConnect(Session session) {
        logger.info("Server got connection from address '{}'", session.getRemoteAddress());
        this.session = session;
        SessionManager.instance.join(session);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        logger.info("Server got close with code: '{}', msg '{}'", statusCode, reason);
        SessionManager.instance.part(session);
    }

    @Override
    public void onWebSocketText(String message) {
        logger.info("Server got msg: {}", message);

        ApiToDisruptor.instance.passMessage(message);
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        logger.info("Server got binary: {}", payload);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        logger.error("Server got error", cause);
    }
}