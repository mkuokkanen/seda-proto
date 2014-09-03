package fi.iki.mkuokkanen.seda.queue;

import com.google.inject.AbstractModule;
import fi.iki.mkuokkanen.seda.queue.translator.JsonToMessageTranslator;
import fi.iki.mkuokkanen.seda.queue.translator.StoreToMessageTranslator;

import javax.inject.Singleton;

/**
 * Guice Module for Queues
 *
 * @author mkuokkanen
 */
public class QueueModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JsonToMessageTranslator.class).in(Singleton.class);
        bind(StoreToMessageTranslator.class).in(Singleton.class);

        bind(QueueIn.class).to(QueueInImpl.class).in(Singleton.class);
        bind(QueueOut.class).to(QueueOutImpl.class).in(Singleton.class);
    }

}
