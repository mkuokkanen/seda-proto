package fi.iki.mkuokkanen.seda.api;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.RingBuffer;

import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

/**
 * Message passer. Singleton, so that websocket instance can access it easily.
 * 
 * @author mkuokkanen
 */
public class ApiToDisruptor {

    private static Logger logger = LoggerFactory.getLogger(ApiToDisruptor.class);

    public static final ApiToDisruptor instance;

    static {
        instance = new ApiToDisruptor();
    }
    
    private RingBuffer<Message> ringBuffer;

    public void setRingbuffer(RingBuffer<Message> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    
    public void passMessage(String str) {
        JsonToMessageTranslator t = null;
        try {
            t = new JsonToMessageTranslator(str);
            ringBuffer.publishEvent(t);
        } catch (ParseException e) {
            logger.warn("error while parsing json msg, i will ignore it: {}", str);
        }
    }
    


}
