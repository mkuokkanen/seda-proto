package fi.iki.mkuokkanen.seda.queue;

import com.lmax.disruptor.RingBuffer;

import fi.iki.mkuokkanen.seda.Service;
import fi.iki.mkuokkanen.seda.queue.event.Message;

public interface Queue extends Service {

    /**
     * Readily created RingBuffer, to create and publish new events to it.
     * 
     * @return
     */
    RingBuffer<Message> getRingBuffer();

}