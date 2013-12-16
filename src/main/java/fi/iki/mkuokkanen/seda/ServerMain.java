package fi.iki.mkuokkanen.seda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fi.iki.mkuokkanen.seda.api.SedaServer;
import fi.iki.mkuokkanen.seda.api.WebsocketModule;
import fi.iki.mkuokkanen.seda.api.session.SessionManager;

/**
 * Main Application to start server.
 */
public class ServerMain {

    private static Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        logger.info("Starting application");

        Injector injector = Guice.createInjector(
                new WebsocketModule()
                );

        SedaServer api = injector.getInstance(SedaServer.class);
        
        api.start();

        ServerApp app = new ServerApp();
        app.startEngines(injector.getInstance(SessionManager.class));
        app.setupSingletonTransferrers(app);
        app.startSchedulers();

        logger.info("Finished starting");
    }

}
