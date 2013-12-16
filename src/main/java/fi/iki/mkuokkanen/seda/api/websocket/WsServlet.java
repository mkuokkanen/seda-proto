package fi.iki.mkuokkanen.seda.api.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Only purpose of this Servlet is to provide WebSocket.
 * 
 * @author mkuokkanen
 */
public class WsServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void configure(WebSocketServletFactory factory) {
        // factory.getPolicy().setIdleTimeout(1000);
        factory.register(WsSocket.class);
    }

}