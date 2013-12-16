package fi.iki.mkuokkanen.seda.queue.event;

/**
 * Event type stored in message.
 * 
 * @author mkuokkanen
 */
public enum MessageType {
    UNKNOWN,
    IN_PUSH,
    IN_DELETE,
    IN_BROADCAST,

    OUT_BROADCAST
}
