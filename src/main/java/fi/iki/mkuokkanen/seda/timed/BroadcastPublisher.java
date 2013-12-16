package fi.iki.mkuokkanen.seda.timed;

import static com.google.common.base.Preconditions.checkNotNull;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.queue.Queue;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

/**
 * @author mkuokkanen
 */
public class BroadcastPublisher implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(BroadcastPublisher.class);

    private final Queue queue;

    /**
     * Constructor
     * 
     * @param queue
     */
    public BroadcastPublisher(Queue queue) {
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
        logger.info("Pushing timed broadcast message");

        String str = JsonCreator.createBroadcastMsg();

        try {
            JsonToMessageTranslator t = new JsonToMessageTranslator(str);
            queue.getRingBuffer().publishEvent(t);
        } catch (ParseException e) {
            logger.error("error while creating broadcast message", e);
        }
    }
}