package fi.iki.mkuokkanen.seda.api;

import org.eclipse.jetty.server.Server;

import com.google.inject.Provider;

/**
 * Provides proper Jetty Server
 * 
 * @author mkuokkanen
 */
public class ServerProvider implements Provider<Server> {


    @Override
    public Server get() {
        return new Server();
    }

}
