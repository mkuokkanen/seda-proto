package fi.iki.mkuokkanen.seda.queue;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import fi.iki.mkuokkanen.seda.keyStore.Storage;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.eventhandler.LoggerEventHandler;
import fi.iki.mkuokkanen.seda.queue.eventhandler.OpInEventHandler;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

import javax.inject.Inject;

/**
 * Inbound queue, passes messages from clients to storage.
 *
 * @author mkuokkanen
 */
class QueueInImpl extends AbstractDisruptor implements QueueIn {

    private final Storage storage;
    private final JsonToMessageTranslator translator;

    /**
     * Default constructor.
     *
     * @param storage
     */
    @Inject
    QueueInImpl(Storage storage, JsonToMessageTranslator translator) {
        this.storage = storage;
        this.translator = translator;
    }

    /**
     * Creates event handlers and sets their processing order.
     *
     * @param disruptor
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

    @Override
    public void writeJsonToQueue(String json) {
        getRingBuffer().publishEvent(translator, json);
    }
}
