package fi.iki.mkuokkanen.seda.api;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import com.google.inject.Provider;

/**
 * Provides proper Jetty Connector
 * 
 * @author mkuokkanen
 */
public class ConnectorProvider implements Provider<Connector> {

    private final Server server;

    /**
     * Default
     */
    @Inject
    public ConnectorProvider(Server server) {
        this.server = checkNotNull(server);
    }

    @Override
    public Connector get() {
        ServerConnector connector = new ServerConnector(server);
        connector.setHost("localhost");
        connector.setPort(8080);

        return connector;
    }

}
