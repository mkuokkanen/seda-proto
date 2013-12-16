package fi.iki.mkuokkanen.seda.keyStore;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

/**
 * Guice Module for Storage
 * 
 * @author mkuokkanen
 */
public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Storage.class).to(KeyStoreManager.class).in(Singleton.class);
        bind(OutEventWriter.class).to(OutEventGeneratorImpl.class);
    }

}
