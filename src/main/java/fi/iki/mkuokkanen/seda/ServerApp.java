package fi.iki.mkuokkanen.seda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.api.ApiToDisruptor;
import fi.iki.mkuokkanen.seda.api.websocket.JettyWebsocketServer;
import fi.iki.mkuokkanen.seda.keyStore.KeyStoreManager;
import fi.iki.mkuokkanen.seda.keyStore.OutEventGenerator;
import fi.iki.mkuokkanen.seda.queue.DisruptorIn;
import fi.iki.mkuokkanen.seda.queue.DisruptorOut;

/**
 * Main Application to start server.
 */
public class ServerApp {

    private static Logger logger = LoggerFactory.getLogger(ServerApp.class);

    DisruptorOut out;
    OutEventGenerator outEventGenerator;
    KeyStoreManager keyStore;
    DisruptorIn in;

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

        ApiToDisruptor.instance.setRingbuffer(app.in.getRingBuffer());

        app.startApi();

        logger.info("Finished starting");
    }

}
