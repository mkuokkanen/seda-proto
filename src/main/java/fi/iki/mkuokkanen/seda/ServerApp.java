package fi.iki.mkuokkanen.seda;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.api.SedaServer;
import fi.iki.mkuokkanen.seda.queue.Queue;
import fi.iki.mkuokkanen.seda.queue.QueueIn;
import fi.iki.mkuokkanen.seda.queue.QueueOut;
import fi.iki.mkuokkanen.seda.timed.EventScheduler;

/**
 * Main Application to start server.
 */
public class ServerApp implements Service {

    private static Logger logger = LoggerFactory.getLogger(ServerApp.class);

    private final SedaServer api;
    private final Queue inQ;
    private final Queue outQ;
    private final EventScheduler scheduler;

    /**
     * Default constructor.
     * 
     * @param api
     * @param inQueue
     * @param outQueue
     * @param scheduler
     */
    @Inject
    public ServerApp(
            SedaServer api,
            QueueIn inQueue,
            QueueOut outQueue,
            EventScheduler scheduler) {

        this.api = api;
        this.inQ = inQueue;
        this.outQ = outQueue;
        this.scheduler = scheduler;
    }

    @Override
    public void start() {
        logger.info("start() - app");

        inQ.start();
        outQ.start();
        api.start();
        scheduler.start();
    }

    @Override
    public void stop() {
        logger.info("stop() - app");

        scheduler.stop();
        api.stop();
        outQ.stop();
        inQ.stop();
    }

}
