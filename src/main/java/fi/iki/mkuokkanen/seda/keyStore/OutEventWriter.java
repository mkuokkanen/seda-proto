package fi.iki.mkuokkanen.seda.keyStore;

import java.util.Map;

public interface OutEventWriter {

    /**
     * @param store
     */
    public abstract void createFullRefreshEvent(Map<String, String> store);

}