package fi.iki.mkuokkanen.seda.queue;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.keyStore.KeyStoreManager;
import fi.iki.mkuokkanen.seda.queue.DisruptorIn;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

public class DisruptorInTest {

    @Test
    public void testPushCreation() throws ParseException {

        KeyStoreManager keyStore = new KeyStoreManager(null);
        DisruptorIn in = new DisruptorIn(keyStore);

        for (int i = 0; i < 5000; i++) {
            doPush(in);
        }

        for (int i = 0; i < 5000; i++) {
            doDel(in);
        }

        doBroadcast(in);

        assertTrue(true);
    }

    private void doPush(DisruptorIn in) throws ParseException {
        String str = JsonCreator.createPushMsg(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
        JsonToMessageTranslator t = new JsonToMessageTranslator(str);
        in.getRingBuffer().publishEvent(t);
    }

    private void doDel(DisruptorIn in) throws ParseException {
        String str = JsonCreator.createDeleteMsg(UUID.randomUUID().toString());
        JsonToMessageTranslator t = new JsonToMessageTranslator(str);
        in.getRingBuffer().publishEvent(t);
    }

    private void doBroadcast(DisruptorIn in) throws ParseException {
        String str = JsonCreator.createBroadcastMsg();
        JsonToMessageTranslator t = new JsonToMessageTranslator(str);
        in.getRingBuffer().publishEvent(t);
    }

}
