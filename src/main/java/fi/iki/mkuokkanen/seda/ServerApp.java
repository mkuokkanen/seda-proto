package fi.iki.mkuokkanen.seda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.api.ApiToDisruptor;
import fi.iki.mkuokkanen.seda.api.SessionManager;
import fi.iki.mkuokkanen.seda.api.websocket.JettyWebsocketServer;
import fi.iki.mkuokkanen.seda.keyStore.KeyStoreManager;
import fi.iki.mkuokkanen.seda.keyStore.OutEventGenerator;
import fi.iki.mkuokkanen.seda.queue.DisruptorIn;
import fi.iki.mkuokkanen.seda.queue.DisruptorOut;
import fi.iki.mkuokkanen.seda.timed.EventScheduler;

/**
 * Main Application to start server.
 */
public class ServerApp {

    private static Logger logger = LoggerFactory.getLogger(ServerApp.class);

    DisruptorOut out;
    OutEventGenerator outEventGenerator;
    KeyStoreManager keyStore;
    DisruptorIn in;

    EventScheduler scheduler;

    /**
     * Init all components in reverse order from output to input.
     */
    public void startEngines() {
        // Internal outbound queue
        out = new DisruptorOut();

        // Outbound queue event generator, talks to DisruptorOut
        outEventGenerator = new OutEventGenerator(out.getRingBuffer());

        // Business logic module, talks to OutEventGenerator
        keyStore = new KeyStoreManager(outEventGenerator);

        // Inbound queue, talks to KeyStore
        in = new DisruptorIn(keyStore);
    }

    public void startApi() {
        JettyWebsocketServer s = new JettyWebsocketServer();
        try {
            s.setup();
        } catch (Exception e) {
            logger.error("Error happened while starting jetty.", e);
        }
    }

    public static void main(String[] args) {
        logger.info("Starting application");

        ServerApp app = new ServerApp();
        app.startEngines();
        app.setupSingletonTransferrers(app);
        app.startApi();
        app.startSchedulers();

        logger.info("Finished starting");
    }

    private void startSchedulers() {
        scheduler = new EventScheduler();
        scheduler.setup(in.getRingBuffer());
    }

    /**
     * Make sure Singleton data movers between api and disruptors are ready.
     * 
     * @param app
     */
    private void setupSingletonTransferrers(ServerApp app) {
        ApiToDisruptor.instance.setRingbuffer(app.in.getRingBuffer());
        // make sure this is created, could do something more intelligent here
        SessionManager.instance.hashCode();
    }

}
