package fi.iki.mkuokkanen.seda.api.resources;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.google.inject.Provider;

/**
 * Provides proper Jetty static resources context
 * 
 * @author mkuokkanen
 */
public class ResourceContextProvider implements Provider<ContextHandler> {

    @Override
    public ContextHandler get() {
        ResourceHandler rHandler = createResourceHandler();

        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        context.setHandler(rHandler);
        return context;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(true);
        rHandler.setResourceBase("./src/webapp");
        rHandler.setWelcomeFiles(new String[] { "index.html" });
        return rHandler;
    }

}
