package fi.iki.mkuokkanen.seda.api.websocket;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebsocketServer {

    private static Logger logger = LoggerFactory.getLogger(JettyWebsocketServer.class);

    public void setup() throws Exception {
        Server server = new Server();

        // cleartext port setup
        ServerConnector connector = setupConnector(server);

        // setup handlers and contexts
        ContextHandler wsContext = createWSContext();
        ContextHandler rsContext = createRsContext();

        // put context into handler list
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { wsContext, rsContext });

        // put handler list into server
        server.setHandler(handlers);

        // start server
        server.start();

        logger.info("Jetty started, host {}, port {}",
                connector.getHost(),
                connector.getLocalPort());
    }

    private ContextHandler createWSContext() {
        // put servlet into handler
        ServletHandler wsHandler = new ServletHandler();
        wsHandler.addServletWithMapping(WsServlet.class, "/*");

        // put handler into context
        ContextHandler wsContext = new ServletContextHandler();
        wsContext.setContextPath("/websocket");
        wsContext.setHandler(wsHandler);
        return wsContext;
    }

    private ContextHandler createRsContext() {
        // put servlet into handler
        ResourceHandler rHandler = addResourcesToWebSocketHandler();

        // put handler into context
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        context.setHandler(rHandler);
        return context;
    }

    private ServerConnector setupConnector(Server server) {
        ServerConnector connector = new ServerConnector(server);
        connector.setHost("localhost");
        connector.setPort(8080);
        server.addConnector(connector);

        return connector;
    }

    private ResourceHandler addResourcesToWebSocketHandler() {
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(true);
        rHandler.setResourceBase("./src/webapp");
        rHandler.setWelcomeFiles(new String[] { "index.html" });
        return rHandler;
    }
}
