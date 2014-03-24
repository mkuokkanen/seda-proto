package fi.iki.mkuokkanen.seda.api.resources;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.google.inject.Provider;

/**
 * Provides proper Jetty static resources context
 * 
 * @author mkuokkanen
 */
public class ResourceContextProvider implements Provider<ContextHandler> {

    private final String contextPath;

    /**
     * Default constructor.
     * 
     * @param contextPath
     */
    @Inject
    public ResourceContextProvider(@Named("api.resource.contextpath") String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public ContextHandler get() {
        ResourceHandler rHandler = createResourceHandler();

        ContextHandler context = new ContextHandler();
        context.setContextPath(contextPath);
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
