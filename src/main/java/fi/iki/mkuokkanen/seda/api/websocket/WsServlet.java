package fi.iki.mkuokkanen.seda.api.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Good info.
 * http://stackoverflow.com/questions/15646213/how-do-i-access-instantiated
 * -websockets-in-jetty-9
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