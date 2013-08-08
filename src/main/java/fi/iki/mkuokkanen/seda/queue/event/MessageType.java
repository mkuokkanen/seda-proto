package fi.iki.mkuokkanen.seda.queue.event;

public enum MessageType {
    UNKNOWN,
    IN_PUSH,
    IN_DELETE,
    IN_BROADCAST,

    OUT_BROADCAST
}
