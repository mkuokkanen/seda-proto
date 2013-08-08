package fi.iki.mkuokkanen.seda.keyStore;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.RingBuffer;

import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.translator.StoreToMessageTranslator;

/**
 * Used to create events to outbound queue. Acts as components between Business
 * Logic and Distributor, so that KeyStore doesn't need to know Distributor.
 * 
 * @author mkuokkanen
 */
public class OutEventGenerator {

    private static Logger logger = LoggerFactory.getLogger(OutEventGenerator.class);

    private RingBuffer<Message> ringBuffer;
    private StoreToMessageTranslator s2jTrans;

    public OutEventGenerator(RingBuffer<Message> ringBuffer) {
        this.ringBuffer = ringBuffer;
        this.s2jTrans = new StoreToMessageTranslator();
    }

    public void createFullRefreshEvent(Map<String, String> store) {
        ringBuffer.publishEvent(s2jTrans, store);
    }

}
