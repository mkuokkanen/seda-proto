package fi.iki.mkuokkanen.seda.queue;

import javax.inject.Inject;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

import fi.iki.mkuokkanen.seda.api.session.SessionManager;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.eventhandler.LoggerEventHandler;
import fi.iki.mkuokkanen.seda.queue.eventhandler.OpOutEventHandler;

/**
 * Outbound queue, passes messages from store to clients.
 * 
 * @author mkuokkanen
 */
class DisruptorOut extends AbstractDisruptor {

    // private static Logger logger =
    // LoggerFactory.getLogger(DisruptorOut.class);
    private final SessionManager sessionManager;

    /**
     * Constructor
     * 
     * @param sessionManager
     */
    @Inject
    DisruptorOut(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Creates eventhandlers and sets their processing order.
     * 
     * @param disruptor
     * @param sessionManager
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
}
