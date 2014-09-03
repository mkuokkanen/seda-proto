package fi.iki.mkuokkanen.seda.timed;

import fi.iki.mkuokkanen.seda.queue.QueueIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Schedules events. At this time only broadcast events.
 *
 * @author mkuokkanen
 */
class EventSchedulerImpl implements EventScheduler {

    private static final int POOL_SIZE = 5;
    private static Logger logger = LoggerFactory.getLogger(EventSchedulerImpl.class);
    private final ScheduledThreadPoolExecutor stpe;
    private final QueueIn queue;

    /**
     * Default constructor
     *
     * @param queue
     */
    @Inject
    public EventSchedulerImpl(QueueIn queue) {
        this.queue = checkNotNull(queue);
        this.stpe = new ScheduledThreadPoolExecutor(POOL_SIZE);
    }


    @Override
    public void start() {
        logger.info("start() - timed operations");

        stpe.scheduleAtFixedRate(
                new BroadcastPublisher(queue),
                5,
                1,
                TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        logger.info("stop() - timed operations");
        stpe.shutdownNow();
        logger.info("scheduler shut down: {}", stpe.isShutdown());
    }
}
