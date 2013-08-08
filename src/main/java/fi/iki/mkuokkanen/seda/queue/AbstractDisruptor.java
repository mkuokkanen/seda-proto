package fi.iki.mkuokkanen.seda.queue;

import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import fi.iki.mkuokkanen.seda.queue.event.Message;

/**
 * Abstract root class for queues.
 * 
 * @author mkuokkanen
 */
public abstract class AbstractDisruptor {

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

    /**
     * Readily created RingBuffer, to create and publish new events to it.
     * 
     * @return
     */
    public RingBuffer<Message> getRingBuffer() {
        return ringBuffer;
    }
}
