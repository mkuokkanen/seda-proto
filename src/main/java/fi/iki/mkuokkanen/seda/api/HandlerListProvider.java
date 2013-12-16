package fi.iki.mkuokkanen.seda.api;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;

import com.google.inject.Provider;

import fi.iki.mkuokkanen.seda.api.resources.ResourceContext;
import fi.iki.mkuokkanen.seda.api.websocket.WebSocketContext;

/**
 * Provides proper Jetty Handlers
 * 
 * @author mkuokkanen
 */
public class HandlerListProvider implements Provider<HandlerList> {

    private final ContextHandler websocketContext;
    private final ContextHandler resourcesContext;

    @Inject
    public HandlerListProvider(
            @ResourceContext ContextHandler resourceContextHandler,
            @WebSocketContext ContextHandler websocketContextHandler) {
        this.resourcesContext = checkNotNull(resourceContextHandler);
        this.websocketContext = checkNotNull(websocketContextHandler);
    }

    @Override
    public HandlerList get() {
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { websocketContext, resourcesContext });
        return handlers;
    }
}
