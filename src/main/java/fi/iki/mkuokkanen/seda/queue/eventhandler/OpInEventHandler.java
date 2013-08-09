package fi.iki.mkuokkanen.seda.queue.eventhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

import fi.iki.mkuokkanen.seda.keyStore.KeyStoreManager;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.event.MessageType;

/**
 * Acts on given operation described in message.
 * Reads data from message and pushes it forward as normal primitive types.
 * 
 * @author Mikko Kuokkanen
 */
public class OpInEventHandler implements EventHandler<Message> {

    private static Logger logger = LoggerFactory.getLogger(OpInEventHandler.class);

    private KeyStoreManager keyStore;

    public OpInEventHandler(KeyStoreManager keyStore) {
        this.keyStore = keyStore;
    }

    @Override
    public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {

        MessageType type = (MessageType) event.type.get();
        logger.info("Handling event type {} (seq {}).", type, sequence);

        switch (type) {
        case IN_PUSH:
            doPush(event);
            break;
        case IN_DELETE:
            doDelete(event);
            break;
        case IN_BROADCAST:
            doBroadcast();
            break;

        case UNKNOWN:
        case OUT_BROADCAST:
            logger.error("Event type {} should not come to execution! Ignoring it.", type);
            break;

        default:
            logger.error("Unknown event type {}!", type);
            break;
        }
    }

    private void doPush(Message event) {
        String key = event.asPush.key.get();
        String value = event.asPush.value.get();
        keyStore.push(key, value);
    }

    private void doDelete(Message event) {
        String key = event.asDelete.key.get();
        keyStore.delete(key);
    }

    private void doBroadcast() {
        keyStore.broadcast();
    }
}
