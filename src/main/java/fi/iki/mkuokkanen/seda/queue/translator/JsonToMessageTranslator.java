package fi.iki.mkuokkanen.seda.queue.translator;

import org.json.simple.parser.ParseException;

import com.lmax.disruptor.EventTranslator;

import fi.iki.mkuokkanen.seda.api.json.JsonReader;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.event.MessageType;

/**
 * sfda
 * 
 * @author mkuokkanen
 */
public class JsonToMessageTranslator implements EventTranslator<Message> {

    private static long counter = 0;

    private JsonReader json;

    public JsonToMessageTranslator(String sourceJson) throws ParseException {
        json = new JsonReader(sourceJson);
    }

    @Override
    public void translateTo(Message event, long sequence) {

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
            break;
        default:
            break;
        }

        event.id.set(counter);
        counter++;
    }

}
