package fi.iki.mkuokkanen.seda.keyStore;

/**
 * Kind of store API of this application, provides methods for storing and
 * fetching key-value pairs.
 * 
 * @author mkuokkanen
 */
public interface Storage {

    /**
     * Add key-val to storage.
     * 
     * @param key
     * @param value
     * @return true if added, false otherwise
     */
    boolean push(String key, String value);

    /**
     * Remove key-val from stroge.
     * 
     * @param key
     * @return true if deleted, false otherwise
     */
    boolean delete(String key);

    /**
     * Push state from storage forwards. Maybe not best place for this? Replace
     * with get-all and implements broadcast somewhere else?
     * 
     * @return
     */
    boolean broadcast();

}