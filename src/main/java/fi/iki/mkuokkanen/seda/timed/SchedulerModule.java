package fi.iki.mkuokkanen.seda.timed;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

/**
 * Guice Module for Timed Operations
 * 
 * @author mkuokkanen
 */
public class SchedulerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventScheduler.class).to(EventSchedulerImpl.class).in(Singleton.class);
    }

}
