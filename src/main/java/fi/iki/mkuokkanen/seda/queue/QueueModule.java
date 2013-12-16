package fi.iki.mkuokkanen.seda.queue;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

/**
 * Guice Module for Queues
 * 
 * @author mkuokkanen
 */
public class QueueModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Queue.class).annotatedWith(QueueIn.class).to(DisruptorIn.class).in(Singleton.class);
        bind(Queue.class).annotatedWith(QueueOut.class).to(DisruptorOut.class).in(Singleton.class);
    }

}
