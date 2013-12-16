package fi.iki.mkuokkanen.seda.timed;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.queue.Queue;
import fi.iki.mkuokkanen.seda.queue.QueueIn;

/**
 * Schedules events. At this time only broadcast events.
 * 
 * @author mkuokkanen
 */
class EventSchedulerImpl implements EventScheduler {

    private static Logger logger = LoggerFactory.getLogger(EventSchedulerImpl.class);

    private static final int POOL_SIZE = 5;

    private final ScheduledThreadPoolExecutor stpe;
    private final Queue queue;

    /**
     * Default constructor
     * 
     * @param ringBuffer
     */
    @Inject
    public EventSchedulerImpl(@QueueIn Queue queue) {
        this.queue = checkNotNull(queue);
        this.stpe = new ScheduledThreadPoolExecutor(POOL_SIZE);
    }


    @Override
    public void start() {
        logger.info("Starting Scheduled operations");

        stpe.scheduleAtFixedRate(
                new BroadcastPublisher(queue),
                5,
                1,
                TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        stpe.shutdownNow();
        logger.info("scheduler shut down: {}", stpe.isShutdown());
    }
}
