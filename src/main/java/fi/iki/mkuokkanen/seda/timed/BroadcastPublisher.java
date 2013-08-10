package fi.iki.mkuokkanen.seda.timed;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.RingBuffer;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

/**
 * @author mkuokkanen
 */
public class BroadcastPublisher implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(EventScheduler.class);

    /**
     * 
     */
    private RingBuffer<Message> ringBuffer;

    /**
     * Constructor
     * 
     * @param ringBuffer
     */
    public BroadcastPublisher(RingBuffer<Message> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public void run() {
        logger.info("Pushing timed broadcast message.");

        String str = JsonCreator.createBroadcastMsg();

        try {
            JsonToMessageTranslator t = new JsonToMessageTranslator(str);
            ringBuffer.publishEvent(t);
        } catch (ParseException e) {
            logger.error("error while creating broadcast message", e);
        }
    }

}