package fi.iki.mkuokkanen.seda.timed;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.RingBuffer;

import fi.iki.mkuokkanen.seda.queue.event.Message;

/**
 * Pushes events to inbound ringbuffer at scheduled times.
 * 
 * @author mkuokkanen
 */
public class EventScheduler {

    private static Logger logger = LoggerFactory.getLogger(EventScheduler.class);

    private static final int POOL_SIZE = 5;



    private ScheduledThreadPoolExecutor stpe;

    public void setup(RingBuffer<Message> ringBuffer) {

        stpe = new ScheduledThreadPoolExecutor(POOL_SIZE);

        stpe.scheduleAtFixedRate(
                new BroadcastPublisher(ringBuffer),
                5,
                2,
                TimeUnit.SECONDS);
    }

    /**
     * Stops scheduler
     * 
     * @param stpe
     */
    public void close() {
        stpe.shutdownNow();
        logger.info("scheduler shut down: {}", stpe.isShutdown());
    }
}
