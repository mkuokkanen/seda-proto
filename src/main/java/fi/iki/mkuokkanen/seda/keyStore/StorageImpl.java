package fi.iki.mkuokkanen.seda.keyStore;

import fi.iki.mkuokkanen.seda.queue.QueueOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple Business Logic module in this app
 *
 * @author mkuokkanen
 */
class StorageImpl implements Storage {

    private static final int MAX_SIZE = 100;
    private static Logger logger = LoggerFactory.getLogger(StorageImpl.class);
    /**
     * Internal data structure.
     */
    private final Map<String, String> store;

    private final QueueOut queue;

    /**
     * Default Constructor.
     */
    @Inject
    public StorageImpl(QueueOut queue) {
        this.queue = checkNotNull(queue);
        this.store = new HashMap<>(MAX_SIZE);
    }

    @Override
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
        return true;
    }

    @Override
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

    @Override
    public void broadcast() {
        queue.writeBroadcastToQueue(store);
    }

}
