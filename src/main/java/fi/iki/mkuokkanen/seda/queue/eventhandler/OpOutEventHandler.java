package fi.iki.mkuokkanen.seda.queue.eventhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.event.MessageType;

/**
 * @author mkuokkanen
 */
public class OpOutEventHandler implements EventHandler<Message> {

    private static Logger logger = LoggerFactory.getLogger(OpOutEventHandler.class);

    public OpOutEventHandler() {
      
    }

    @Override
    public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {

        MessageType type = (MessageType) event.type.get();
        logger.debug("Handling event type {} (seq {}).", type, sequence);

        switch (type) {

        case OUT_BROADCAST:
            doBroadcast(event);
            break;
        default:
            logger.error("Unknown event type {}!", type);
            break;
        }
    }

    private void doBroadcast(Message event) {
        int count = event.asBroadcastResponse.keyCount.get();
        
        logger.info("got broadcast message with {} key-value pairs.", count);
        
        for (int i = 0; i < count; i++) {
            logger.debug("key:{}, value:{}",
                    event.asBroadcastResponse.key[i].get(),
                    event.asBroadcastResponse.value[i].get());
        }
    }
}
