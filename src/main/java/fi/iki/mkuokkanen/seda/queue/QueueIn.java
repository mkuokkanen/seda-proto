package fi.iki.mkuokkanen.seda.queue;

public interface QueueIn extends Queue {

    /**
     * Write Json to Queue.
     *
     * @param json
     */
    void writeJsonToQueue(String json);

}