package fi.iki.mkuokkanen.seda;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.queue.DisruptorIn;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

/**
 * Unit test for simple App.
 */
public class ServerAppTest {

    @Test
    public void simpleTest() throws ParseException {
        ServerApp app = new ServerApp();
        app.startEngines();

        for (int i = 0; i < 101; i++) {
            doPush(app.in);
        }

        doDel(app.in);

        doBroadcast(app.in);

        // pause, junit calls system.exit() or something in end.
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
