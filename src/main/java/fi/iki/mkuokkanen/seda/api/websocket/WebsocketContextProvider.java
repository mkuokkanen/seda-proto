package fi.iki.mkuokkanen.seda.api.websocket;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import fi.iki.mkuokkanen.seda.queue.QueueIn;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.inject.Provider;

import fi.iki.mkuokkanen.seda.api.session.SessionManager;

/**
 * Provides proper Jetty Websocket Context
 * 
 * @author mkuokkanen
 */
public class WebsocketContextProvider implements Provider<ContextHandler> {

    private final SessionManager sessionManager;
    private final String contextPath;
    private final QueueIn queue;

    /**
     * Default constructor.
     * 
     * @param sessionManager
     * @param queue
     * @param contextPath
     */
    @Inject
    public WebsocketContextProvider(
            SessionManager sessionManager,
            QueueIn queue,
            @Named("api.websocket.contextpath") String contextPath) {
        this.sessionManager = checkNotNull(sessionManager);
        this.queue = checkNotNull(queue);
        this.contextPath = checkNotNull(contextPath);
    }

    @Override
    public ContextHandler get() {
        ServletHandler wsHandler = createServlet();

        ContextHandler wsContext = new ServletContextHandler();
        wsContext.setContextPath(contextPath);
        wsContext.setHandler(wsHandler);
        return wsContext;
    }

    /**
     * Some Jetty Magic. Attach Servlet to Servlet Handler.
     * 
     * @return
     */
    private ServletHandler createServlet() {
        ServletHandler wsHandler = new ServletHandler();
        wsHandler.addServletWithMapping(WsServlet.class, "/*");

        // kludge
        WsSocket.setSessionManager(sessionManager);
        WsSocket.setDisruptorWriter(queue);

        return wsHandler;
    }

}
