package fi.iki.mkuokkanen.seda.queue.translator;

import com.lmax.disruptor.EventTranslatorOneArg;
import fi.iki.mkuokkanen.seda.queue.event.Message;
import fi.iki.mkuokkanen.seda.queue.event.MessageType;

import java.util.Map;

/**
 * Translates given Map data to Message elements.
 *
 * @author mkuokkanen
 */
public class StoreToMessageTranslator implements EventTranslatorOneArg<Message, Map<String, String>> {

    @Override
    public void translateTo(Message event, long sequence, Map<String, String> data) {
        event.type.set(MessageType.OUT_BROADCAST);

        int count = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            event.asBroadcastResponse.key[count].set(key);
            event.asBroadcastResponse.value[count].set(value);

            count++;
        }

        event.asBroadcastResponse.keyCount.set((short) data.size());
    }

}
