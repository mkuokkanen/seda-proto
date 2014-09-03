package fi.iki.mkuokkanen.seda.keyStore;

import fi.iki.mkuokkanen.seda.queue.QueueOut;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KeyStoreManagerTest {

    @Test
    public void push() {
        Storage ks = new StorageImpl(new QueueOut() {
            @Override
            public void writeBroadcastToQueue(Map<String, String> data) {
                // ignore
            }

            @Override
            public void start() {
                // ignore
            }

            @Override
            public void stop() {
                // ignore
            }
        });

        assertTrue(ks.push("abc", "def"));
        assertTrue(ks.push("abc", "defg"));
        assertTrue(ks.push("abc", "defgh"));
        assertTrue(ks.push("abcd", "def"));

        assertTrue(ks.delete("abc"));
        assertFalse(ks.delete("abc"));
        assertTrue(ks.delete("abcd"));
    }

}
