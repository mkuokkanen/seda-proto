package fi.iki.mkuokkanen.seda.api;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

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
    private final int port;
    private final String host;

    /**
     * Default constructor
     * 
     * @param server
     * @param port
     * @param host
     */
    @Inject
    public ConnectorProvider(Server server,
            @Named("api.port") int port,
            @Named("api.host") String host) {
        this.server = checkNotNull(server);
        checkArgument(port > 0);
        this.port = port;
        this.host = host;
    }

    @Override
    public Connector get() {
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(host);
        connector.setPort(port);

        return connector;
    }

}
