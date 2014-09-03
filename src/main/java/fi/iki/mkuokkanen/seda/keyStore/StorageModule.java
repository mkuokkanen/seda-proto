package fi.iki.mkuokkanen.seda.keyStore;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

/**
 * Guice Module for Storage
 *
 * @author mkuokkanen
 */
public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Storage.class).to(StorageImpl.class).in(Singleton.class);
    }

}
