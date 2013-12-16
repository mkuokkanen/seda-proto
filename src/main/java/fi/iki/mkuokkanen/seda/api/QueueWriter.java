package fi.iki.mkuokkanen.seda.api;

import javax.inject.Inject;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.mkuokkanen.seda.queue.Queue;
import fi.iki.mkuokkanen.seda.queue.QueueIn;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

/**
 * Writes given string to queue.
 * 
 * @author mkuokkanen
 */
public class QueueWriter {

    private static Logger logger = LoggerFactory.getLogger(QueueWriter.class);
    
    private final Queue q;

    /**
     * Default constructor.
     * 
     * @param queue
     */
    @Inject
    public QueueWriter(@QueueIn Queue queue) {
        this.q = queue;
    }
    
    /**
     * Converts and writes message to queue.
     * 
     * @param str
     */
    public void passMessage(String str) {
        JsonToMessageTranslator t = null;
        try {
            t = new JsonToMessageTranslator(str);
            q.getRingBuffer().publishEvent(t);
        } catch (ParseException e) {
            logger.warn("error while parsing json msg, i will ignore it: {}", str);
        }
    }

}
