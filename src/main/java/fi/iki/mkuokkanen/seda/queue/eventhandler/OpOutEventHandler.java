package fi.iki.mkuokkanen.seda.queue.eventhandler;

import static com.google.common.base.Preconditions.checkNotNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

import fi.iki.mkuokkanen.seda.api.session.SessionManager;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.event.MessageType;

/**
 * @author mkuokkanen
 */
public class OpOutEventHandler implements EventHandler<Message> {

    private static Logger logger = LoggerFactory.getLogger(OpOutEventHandler.class);
    private final SessionManager sessionManager;

    public OpOutEventHandler(SessionManager sessionManager) {
        this.sessionManager = checkNotNull(sessionManager);
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

    @SuppressWarnings("unchecked")
    private void doBroadcast(Message event) {
        int count = event.asBroadcastResponse.keyCount.get();
        
        logger.debug("got broadcast message with {} key-value pairs.", count);
        
        // create list
        JSONArray list = new JSONArray();

        for (int i = 0; i < count; i++) {
            JSONObject elem = new JSONObject();
            elem.put("key", event.asBroadcastResponse.key[i].get());
            elem.put("value", event.asBroadcastResponse.value[i].get());

            list.add(elem);
        }

        // add it to root
        JSONObject root = new JSONObject();
        root.put("keyvalues", list);

        sessionManager.sendAll(root.toJSONString());
    }
}
