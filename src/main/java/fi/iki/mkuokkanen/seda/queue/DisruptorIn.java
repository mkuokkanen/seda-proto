package fi.iki.mkuokkanen.seda.queue;

import javax.inject.Inject;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

import fi.iki.mkuokkanen.seda.keyStore.Storage;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.eventhandler.LoggerEventHandler;
import fi.iki.mkuokkanen.seda.queue.eventhandler.OpInEventHandler;

/**
 * Inbound queue, passes messages from clients to storage.
 * 
 * @author mkuokkanen
 */
class DisruptorIn extends AbstractDisruptor {

    // private static Logger logger =
    // LoggerFactory.getLogger(DisruptorIn.class);

    private final Storage storage;

    /**
     * Default constructor.
     * 
     * @param storage
     */
    @Inject
    DisruptorIn(Storage storage) {
        this.storage = storage;
    }

    /**
     * Creates event handlers and sets their processing order.
     * 
     * @param disruptor
     * @param keyStore
     */
    @SuppressWarnings("unchecked")
    @Override
    void setupEventHandlers(Disruptor<Message> disruptor) {
        EventHandler<Message> logHandler = new LoggerEventHandler("InLog");
        EventHandler<Message> operation = new OpInEventHandler(storage);

        disruptor
                .handleEventsWith(logHandler)
                .then(operation);
    }

}
