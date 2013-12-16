package fi.iki.mkuokkanen.seda.keyStore;

import java.util.Map;

import javax.inject.Inject;

import fi.iki.mkuokkanen.seda.queue.Queue;
import fi.iki.mkuokkanen.seda.queue.QueueOut;
import fi.iki.mkuokkanen.seda.queue.translator.StoreToMessageTranslator;

/**
 * Used to create events to outbound queue. Acts as components between Business
 * Logic and Distributor, so that KeyStore doesn't need to know Distributor.
 * 
 * @author mkuokkanen
 */
public class OutEventGeneratorImpl implements OutEventWriter {

    // private static Logger logger =
    // LoggerFactory.getLogger(OutEventGenerator.class);

    private final Queue queue;
    private final StoreToMessageTranslator s2jTrans;

    /**
     * Default Constructor
     * 
     * @param ringBuffer
     */
    @Inject
    public OutEventGeneratorImpl(@QueueOut Queue queue) {
        this.queue = queue;
        this.s2jTrans = new StoreToMessageTranslator();
    }

    /* (non-Javadoc)
     * @see fi.iki.mkuokkanen.seda.keyStore.OutEventWriter#createFullRefreshEvent(java.util.Map)
     */
    @Override
    public void createFullRefreshEvent(Map<String, String> store) {
        queue.getRingBuffer().publishEvent(s2jTrans, store);
    }

}
