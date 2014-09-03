package fi.iki.mkuokkanen.seda.queue;

import java.util.Map;

public interface QueueOut extends Queue {

    /**
     * Write Broadcast message to Queue.
     * 
     * @param data
     */
    void writeBroadcastToQueue(Map<String, String> data);

}