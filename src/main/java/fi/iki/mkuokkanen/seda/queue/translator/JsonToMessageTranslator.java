package fi.iki.mkuokkanen.seda.queue.translator;

import com.lmax.disruptor.EventTranslatorOneArg;
import fi.iki.mkuokkanen.seda.api.json.JsonReader;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.event.MessageType;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates any incoming Json message to Disruptor data structure.
 *
 * @author mkuokkanen
 */
public class JsonToMessageTranslator implements EventTranslatorOneArg<Message, String> {

    private static Logger logger = LoggerFactory.getLogger(JsonToMessageTranslator.class);

    private static long counter = 0;

    @Override
    public void translateTo(Message event, long sequence, String jsonString) {

        JsonReader json = null;
        try {
            json = new JsonReader(jsonString);
        } catch (ParseException e) {
            logger.error("Error while parsing incoming json, what should I do?");
        }
        String op = json.getOperationType();

        switch (op) {
            case "push":
                event.type.set(MessageType.IN_PUSH);
                event.asPush.key.set(json.getValForKey("key"));
                event.asPush.value.set(json.getValForKey("val"));
                break;
            case "delete":
                event.type.set(MessageType.IN_DELETE);
                event.asDelete.key.set(json.getValForKey("key"));
                break;
            case "broadcast":
                event.type.set(MessageType.IN_BROADCAST);
                event.asBroadcast.isUnion(); // placeholder call, not really needed
                break;
            default:
                break;
        }

        event.id.set(counter);
        counter++;
    }

}
