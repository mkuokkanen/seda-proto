package fi.iki.mkuokkanen.seda.keyStore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class KeyStoreManagerTest {

    @Test
    public void push() {
        Storage ks = new KeyStoreManager(new OutEventWriter() {
            @Override
            public void createFullRefreshEvent(Map<String, String> store) {
                return;
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
