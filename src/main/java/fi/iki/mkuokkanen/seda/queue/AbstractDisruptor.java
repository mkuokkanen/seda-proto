package fi.iki.mkuokkanen.seda.queue;

import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import fi.iki.mkuokkanen.seda.queue.event.Message;

/**
 * Abstract root class for queues.
 * 
 * @author mkuokkanen
 */
public abstract class AbstractDisruptor implements Queue {

    private static Logger logger = LoggerFactory.getLogger(AbstractDisruptor.class);

    /**
     * Disruptor Wizard.
     */
    private Disruptor<Message> disruptor;

    /**
     * RingBuffer store
     */
    protected RingBuffer<Message> ringBuffer;

    /**
     * Create disruptor, a wizard for creating actual Ringbuffer.
     * 
     * @return
     */
    protected Disruptor<Message> createDisruptorWizard() {
        Disruptor<Message> disruptor = new Disruptor<Message>(
                Message.FACTORY,
                128,
                Executors.newCachedThreadPool());
        return disruptor;
    }

    @Override
    public RingBuffer<Message> getRingBuffer() {
        checkState(ringBuffer != null, "Trying to fetch RingBuffer without starting disruptor.");
        return ringBuffer;
    }

    @Override
    public void start() {
        logger.info("start() - disruptor queue");

        disruptor = createDisruptorWizard();
        setupEventHandlers(disruptor);
        ringBuffer = disruptor.start();
    }

    @Override
    public void stop() {
        logger.info("stop() - disruptor queue");
        disruptor.shutdown();
    }
    
    /**
     * Configure Disruptor Wizard before creating.
     * 
     * @param disruptor
     */
    abstract void setupEventHandlers(Disruptor<Message> disruptor);
}
