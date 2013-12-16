package fi.iki.mkuokkanen.seda.api.websocket;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

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

    private SessionManager sessionManager;

    /**
     * @param sessionManager
     */
    @Inject
    public WebsocketContextProvider(SessionManager sessionManager) {
        this.sessionManager = checkNotNull(sessionManager);
    }

    @Override
    public ContextHandler get() {
        ServletHandler wsHandler = createServlet();

        ContextHandler wsContext = new ServletContextHandler();
        wsContext.setContextPath("/websocket");
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

        return wsHandler;
    }

}
