package fi.iki.mkuokkanen.seda.api;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import fi.iki.mkuokkanen.seda.api.resources.ResourceContext;
import fi.iki.mkuokkanen.seda.api.resources.ResourceContextProvider;
import fi.iki.mkuokkanen.seda.api.session.SessionManager;
import fi.iki.mkuokkanen.seda.api.session.SessionManagerImpl;
import fi.iki.mkuokkanen.seda.api.websocket.WebSocketContext;
import fi.iki.mkuokkanen.seda.api.websocket.WebsocketContextProvider;

/**
 * Guice Module for WebSocket API Layer
 * 
 * @author mkuokkanen
 */
public class WebsocketModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SedaServer.class).in(Singleton.class);
        bind(Server.class).toProvider(ServerProvider.class).in(Singleton.class);
        bind(Connector.class).toProvider(ConnectorProvider.class);
        bind(HandlerList.class).toProvider(HandlerListProvider.class);
        bind(ContextHandler.class).annotatedWith(WebSocketContext.class).toProvider(WebsocketContextProvider.class)
                .in(Singleton.class);
        bind(ContextHandler.class).annotatedWith(ResourceContext.class).toProvider(ResourceContextProvider.class)
                .in(Singleton.class);
        bind(SessionManager.class).to(SessionManagerImpl.class).in(Singleton.class);
    }

}
