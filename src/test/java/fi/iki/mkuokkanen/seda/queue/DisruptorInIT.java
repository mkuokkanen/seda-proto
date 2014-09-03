package fi.iki.mkuokkanen.seda.queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.keyStore.Storage;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;

/**
 * Test that big number of events go through Disruptor properly.
 * 
 * @author mkuokkanen
 */
public class DisruptorInIT {

    private int pushCalledCounter;
    private int delCalledCounter;
    private int broadcastCalledCounter;
    private boolean pushOk;
    private boolean deleteOk;

    @Test
    public void testPushCreation() throws ParseException, InterruptedException {

        pushCalledCounter = 0;
        delCalledCounter = 0;
        broadcastCalledCounter = 0;

        pushOk = true;
        deleteOk = true;

        Storage keyStore = new MockStorage();
        DisruptorIn in = new DisruptorIn(keyStore);
        in.start();

        for (int i = 0; i < 5000; i++) {
            doPush(in, i);
            doDel(in, i);
        }

        doBroadcast(in);

        Thread.sleep(2000);

        assertTrue(pushOk);
        assertTrue(deleteOk);

        assertEquals(5000, pushCalledCounter);
        assertEquals(5000, delCalledCounter);
        assertEquals(1, broadcastCalledCounter);

        in.stop();
    }

    private void doPush(DisruptorIn in, int i) throws ParseException {
        String str = JsonCreator.createPushMsg(Integer.valueOf(i).toString(), Integer.valueOf(i).toString());
        JsonToMessageTranslator t = new JsonToMessageTranslator(str);
        in.getRingBuffer().publishEvent(t);
    }

    private void doDel(DisruptorIn in, int i) throws ParseException {
        String str = JsonCreator.createDeleteMsg(Integer.valueOf(i).toString());
        JsonToMessageTranslator t = new JsonToMessageTranslator(str);
        in.getRingBuffer().publishEvent(t);
    }

    private void doBroadcast(DisruptorIn in) throws ParseException {
        String str = JsonCreator.createBroadcastMsg();
        JsonToMessageTranslator t = new JsonToMessageTranslator(str);
        in.getRingBuffer().publishEvent(t);
    }

    class MockStorage implements Storage {
        @Override
        public boolean push(String key, String value) {
            // check that push got proper value;
            if (Integer.valueOf(key).intValue() != pushCalledCounter) {
                pushOk = false;
            }
            pushCalledCounter++;
            // return just something
            return true;
        }

        @Override
        public boolean delete(String key) {
            // check that push got proper value;
            if (Integer.valueOf(key).intValue() != delCalledCounter) {
                deleteOk = false;
            }
            delCalledCounter++;
            // return just something
            return true;
        }

        @Override
        public boolean broadcast() {
            broadcastCalledCounter++;
            return true;
        }
    }
}
