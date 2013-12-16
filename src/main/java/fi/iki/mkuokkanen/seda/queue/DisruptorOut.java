package fi.iki.mkuokkanen.seda.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

import fi.iki.mkuokkanen.seda.api.session.SessionManager;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.eventhandler.LoggerEventHandler;
import fi.iki.mkuokkanen.seda.queue.eventhandler.OpOutEventHandler;

public class DisruptorOut extends AbstractDisruptor {

    private static Logger logger = LoggerFactory.getLogger(DisruptorOut.class);

    /**
     * Constructor
     * 
     * @param sessionManager
     */
    public DisruptorOut(SessionManager sessionManager) {
        super();

        Disruptor<Message> disruptor = createDisruptorWizard();
        setupEventHandlers(disruptor, sessionManager);

        // Create actual Ringbuffer from Disruptor Wizard
        ringBuffer = disruptor.start();

        logger.info("Outbound ringbuffer created, {}", ringBuffer);
    }

    /**
     * Creates eventhandlers and sets their processing order.
     * 
     * @param disruptor
     * @param sessionManager
     */
    @SuppressWarnings("unchecked")
    protected void setupEventHandlers(Disruptor<Message> disruptor, SessionManager sessionManager) {
        EventHandler<Message> log = new LoggerEventHandler("OutLog1");
        EventHandler<Message> opOut = new OpOutEventHandler(sessionManager);

        disruptor
                .handleEventsWith(log)
                .handleEventsWith(opOut);
    }
}
