package fi.iki.mkuokkanen.seda.api;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.Service;

/**
 * WebSocket server. Combines all the components together.
 * 
 * @author mkuokkanen
 */
public class SedaServer implements Service {

    private static Logger logger = LoggerFactory.getLogger(SedaServer.class);

    private final Server server;

    /**
     * Combines Jetty Server to Connector and handlers.
     * 
     * @param server
     * @param connector
     * @param handlers
     */
    @Inject
    public SedaServer(Server server, Connector connector, HandlerList handlers) {
        this.server = checkNotNull(server);

        server.addConnector(checkNotNull(connector));
        server.setHandler(checkNotNull(handlers));
    }

    @Override
    public void start() {
        logger.info("start() - websocket api");

        try {
            server.start();
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    @Override
    public void stop() {
        logger.info("stop() - websocket api");

        try {
            server.stop();
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
