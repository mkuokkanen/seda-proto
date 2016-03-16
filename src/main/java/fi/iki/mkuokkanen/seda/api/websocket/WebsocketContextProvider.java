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
        // kludge - init WsSocket via static fields because no access to constructor.
        WsSocket.setSessionManager(sessionManager);
        WsSocket.setDisruptorWriter(queue);

        // Rest should be somewhat understandable
        ServletContextHandler wsContext = new ServletContextHandler();
        wsContext.setContextPath(contextPath);
        wsContext.addServlet(WsServlet.class, "/*");
        return wsContext;
    }

    /*
     * Some Jetty Magic. Attach Servlet to Servlet Handler.
     *
     * 15.3.2016:
     * This stopped working somewhere between Jetty 9.0 and 9.3
     * It seems ServletHandler should not be managed manually?
     * Instead Servlet should be injected straight to context?
     * http://stackoverflow.com/questions/26640098/embedded-jetty-servlet-not-initialized
     *
     * @return
     */
    /*
    private ServletHandler createServlet() {
        ServletHandler wsHandler = new ServletHandler();
        wsHandler.addServletWithMapping(WsServlet.class, "/*");

        // kludge
        WsSocket.setSessionManager(sessionManager);
        WsSocket.setDisruptorWriter(queue);

        return wsHandler;
    }
*/
}
