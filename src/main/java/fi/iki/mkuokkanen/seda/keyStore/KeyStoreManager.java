package fi.iki.mkuokkanen.seda.keyStore;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Business Logic module in this app
 * 
 * @author mkuokkanen
 */
public class KeyStoreManager {

    private static Logger logger = LoggerFactory.getLogger(KeyStoreManager.class);
    private static final int MAX_SIZE = 100;

    /**
     * Internal data structure.
     */
    private Map<String, String> store = new HashMap<>(MAX_SIZE);

    private OutEventGenerator outEventGenerator;

    public KeyStoreManager(OutEventGenerator outEventGenerator) {
        this.outEventGenerator = outEventGenerator;

        if (this.outEventGenerator == null) {
            logger.warn("KeyStore does not have outEventGenerator, no messages will be pushed forward.");
        }
    }

    /**
     * @param key
     * @param value
     * @return success in operation
     */
    public boolean push(String key, String value) {
        logger.info("Push {}:{}", key, value);

        if (store.size() >= MAX_SIZE) {
            logger.warn("KeyStore already has {} items and limit is {}, ignoring push.",
                    store.size(),
                    MAX_SIZE);
            return false;
        }

        String ret = store.put(key, value);

        if (ret == null) {
            logger.debug("Store push, op:add, key:{}, value:{}", key, value);
        } else {
            logger.debug("Store push, op:update, key:{}, value:{}", key, value);
        }

        broadcast();

        return true;
    }

    /**
     * @param key
     * @return
     */
    public boolean delete(String key) {
        String ret = store.remove(key);

        if (ret == null) {
            logger.debug("Store delete, had no key:{}", key);
            return false;
        } else {
            logger.debug("Store delete, key:{}", key);
            return true;
        }
    }

    /**
     * @return
     */
    public boolean broadcast() {
        if (outEventGenerator == null) {
            logger.warn("Ignoring broadcast request because no KeyStore does not know where to put it.");
            return false;
        }

        outEventGenerator.createFullRefreshEvent(store);
        return true;
    }

}
