package fi.iki.mkuokkanen.seda.queue;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkState;

/**
 * Abstract root class for queues.
 *
 * @author mkuokkanen
 */
abstract class AbstractDisruptor implements Queue {

    private static Logger logger = LoggerFactory.getLogger(AbstractDisruptor.class);

    private Disruptor<Message> disruptor;
    private RingBuffer<Message> ringBuffer;

    /**
     * Create disruptor, a wizard for creating actual Ringbuffer.
     *
     * @return standard disruptor wizard
     */
    protected Disruptor<Message> createDisruptorWizard() {
        return new Disruptor<>(
                Message.FACTORY,
                128,
                Executors.newCachedThreadPool());
    }

    protected RingBuffer<Message> getRingBuffer() {
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
