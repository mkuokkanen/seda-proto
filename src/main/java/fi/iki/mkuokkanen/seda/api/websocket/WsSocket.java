package fi.iki.mkuokkanen.seda.api.websocket;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.api.QueueWriter;
import fi.iki.mkuokkanen.seda.api.session.SessionManager;

/**
 * One instance per session, i presume.
 * 
 * @author mkuokkanen
 */
public class WsSocket implements WebSocketListener {

    private static Logger logger = LoggerFactory.getLogger(WsSocket.class);

    /**
     * Kind of kludge. Need this because we don't get to initialize this class.
     */
    private static SessionManager sessionManager = null;

    /**
     * Kind of kludge. Need this because we don't get to initialize this class.
     */
    private static QueueWriter apiToDisruptor = null;

    private Session session;

    /**
     * This shouldn't be needed, but currently we don't have access to
     * instantiation chain.
     * 
     * @param sessionManager
     */
    public static void setSessionManager(SessionManager sessionManager) {
        checkState(WsSocket.sessionManager == null, "Don't call this twice!");
        checkNotNull(sessionManager);
        WsSocket.sessionManager = sessionManager;
    }

    /**
     * This shouldn't be needed, but currently we don't have access to
     * instantiation chain.
     * 
     * @param apiToDisruptor
     */
    public static void setDisruptorWriter(QueueWriter apiToDisruptor) {
        checkState(WsSocket.apiToDisruptor == null, "Don't call this twice!");
        checkNotNull(apiToDisruptor);
        WsSocket.apiToDisruptor = apiToDisruptor;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        checkState(sessionManager != null);
        logger.info("Server got connection from address '{}'", session.getRemoteAddress());
        this.session = session;
        sessionManager.join(session);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        checkState(sessionManager != null);
        logger.info("Server got close with code: '{}', msg '{}'", statusCode, reason);
        sessionManager.part(session);
    }

    @Override
    public void onWebSocketText(String message) {
        checkState(apiToDisruptor != null);
        logger.info("Server got msg: {}", message);
        apiToDisruptor.passMessage(message);
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