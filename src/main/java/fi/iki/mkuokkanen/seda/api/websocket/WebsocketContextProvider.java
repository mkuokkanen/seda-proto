package fi.iki.mkuokkanen.seda.api.websocket;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.inject.Provider;

import fi.iki.mkuokkanen.seda.api.QueueWriter;
import fi.iki.mkuokkanen.seda.api.session.SessionManager;

/**
 * Provides proper Jetty Websocket Context
 * 
 * @author mkuokkanen
 */
public class WebsocketContextProvider implements Provider<ContextHandler> {

    private final SessionManager sessionManager;
    private final QueueWriter queueWriter;
    private final String contextPath;

    /**
     * Default constructor.
     * 
     * @param sessionManager
     * @param queueWriter
     * @param contextPath
     */
    @Inject
    public WebsocketContextProvider(
            SessionManager sessionManager,
            QueueWriter queueWriter,
            @Named("api.websocket.contextpath") String contextPath) {
        this.sessionManager = checkNotNull(sessionManager);
        this.queueWriter = checkNotNull(queueWriter);
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
        WsSocket.setDisruptorWriter(queueWriter);

        return wsHandler;
    }

}
