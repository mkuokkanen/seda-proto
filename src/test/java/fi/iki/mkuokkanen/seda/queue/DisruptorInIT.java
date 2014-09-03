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
        JsonToMessageTranslator translator = new JsonToMessageTranslator();
        QueueInImpl in = new QueueInImpl(keyStore, translator);
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

    private void doPush(QueueInImpl in, int i) throws ParseException {
        String str = JsonCreator.createPushMsg(Integer.valueOf(i).toString(), Integer.valueOf(i).toString());
        in.writeJsonToQueue(str);
    }

    private void doDel(QueueInImpl in, int i) throws ParseException {
        String str = JsonCreator.createDeleteMsg(Integer.valueOf(i).toString());
        in.writeJsonToQueue(str);
    }

    private void doBroadcast(QueueInImpl in) throws ParseException {
        String str = JsonCreator.createBroadcastMsg();
        in.writeJsonToQueue(str);
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
        public void broadcast() {
            broadcastCalledCounter++;
        }
    }
}
