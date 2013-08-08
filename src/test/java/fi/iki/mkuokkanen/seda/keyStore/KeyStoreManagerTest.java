package fi.iki.mkuokkanen.seda.keyStore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.iki.mkuokkanen.seda.keyStore.KeyStoreManager;

public class KeyStoreManagerTest {

    @Test
    public void push() {
        KeyStoreManager ks = new KeyStoreManager(null);

        assertTrue(ks.push("abc", "def"));
        assertTrue(ks.push("abc", "defg"));
        assertTrue(ks.push("abc", "defgh"));
        assertTrue(ks.push("abcd", "def"));

        assertTrue(ks.delete("abc"));
        assertFalse(ks.delete("abc"));
        assertTrue(ks.delete("abcd"));
    }

}
