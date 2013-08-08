package fi.iki.mkuokkanen.seda.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

import fi.iki.mkuokkanen.seda.keyStore.KeyStoreManager;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.eventhandler.LoggerEventHandler;
import fi.iki.mkuokkanen.seda.queue.eventhandler.OpInEventHandler;

/**
 * @author mkuokkanen
 */
public class DisruptorIn extends AbstractDisruptor {

    private static Logger logger = LoggerFactory.getLogger(DisruptorIn.class);

    public DisruptorIn(KeyStoreManager keyStore) {
        super();

        Disruptor<Message> disruptor = createDisruptorWizard();
        setupEventHandlers(disruptor, keyStore);

        // Create actual Ringbuffer from Disruptor Wizard
        ringBuffer = disruptor.start();

        logger.info("Inbound ringbuffer created, {}", ringBuffer);
    }

    /**
     * Creates eventhandlers and sets their processing order.
     * 
     * @param disruptor
     * @param keyStore
     */
    @SuppressWarnings("unchecked")
    private void setupEventHandlers(Disruptor<Message> disruptor, KeyStoreManager keyStore) {

        EventHandler<Message> logHandler = new LoggerEventHandler("InLog");
        EventHandler<Message> operation = new OpInEventHandler(keyStore);

        disruptor
                .handleEventsWith(logHandler)
                .then(operation);
    }

}
