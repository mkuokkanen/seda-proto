package fi.iki.mkuokkanen.seda.timed;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.queue.QueueIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author mkuokkanen
 */
public class BroadcastPublisher implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(BroadcastPublisher.class);

    private final QueueIn queue;

    public BroadcastPublisher(QueueIn queue) {
        this.queue = checkNotNull(queue);
    }

    @Override
    public void run() {
        try {
            operation();
        } catch (Exception e) {
            logger.error("Something unfortunate happened in queue.", e);
        }
    }

    private void operation() {
        logger.debug("Pushing timed broadcast message");

        String str = JsonCreator.createBroadcastMsg();
        queue.writeJsonToQueue(str);
    }
}