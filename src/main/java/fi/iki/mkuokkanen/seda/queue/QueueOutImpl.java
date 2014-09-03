package fi.iki.mkuokkanen.seda.queue;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import fi.iki.mkuokkanen.seda.api.session.SessionManager;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.eventhandler.LoggerEventHandler;
import fi.iki.mkuokkanen.seda.queue.eventhandler.OpOutEventHandler;
import fi.iki.mkuokkanen.seda.queue.translator.StoreToMessageTranslator;

import javax.inject.Inject;
import java.util.Map;

/**
 * Outbound queue, passes messages from store to clients.
 *
 * @author mkuokkanen
 */
class QueueOutImpl extends AbstractDisruptor implements QueueOut {

    // private static Logger logger =
    // LoggerFactory.getLogger(DisruptorOut.class);

    private final SessionManager sessionManager;
    private final StoreToMessageTranslator translator;

    /**
     * Constructor
     *
     * @param sessionManager
     */
    @Inject
    QueueOutImpl(SessionManager sessionManager, StoreToMessageTranslator translator) {
        this.sessionManager = sessionManager;
        this.translator = translator;
    }

    /**
     * Creates eventhandlers and sets their processing order.
     *
     * @param disruptor
     */
    @SuppressWarnings("unchecked")
    @Override
    void setupEventHandlers(Disruptor<Message> disruptor) {
        EventHandler<Message> log = new LoggerEventHandler("OutLog1");
        EventHandler<Message> opOut = new OpOutEventHandler(sessionManager);

        disruptor
                .handleEventsWith(log)
                .handleEventsWith(opOut);
    }

    @Override
    public void writeBroadcastToQueue(Map<String, String> data) {
        getRingBuffer().publishEvent(translator, data);
    }
}
