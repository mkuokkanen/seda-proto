package fi.iki.mkuokkanen.seda.queue;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.keyStore.Storage;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

public class DisruptorInTest {

    @Test
    public void testPushCreation() throws ParseException {

        Storage keyStore = new Storage() {

            @Override
            public boolean push(String key, String value) {
                return true;
            }

            @Override
            public boolean delete(String key) {
                return true;
            }

            @Override
            public boolean broadcast() {
                return true;
            }
        };
        DisruptorIn in = new DisruptorIn(keyStore);
        in.start();

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
