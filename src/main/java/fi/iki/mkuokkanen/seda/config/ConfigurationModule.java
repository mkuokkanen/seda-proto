package fi.iki.mkuokkanen.seda.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Guice module for configuration, accessible through
 * "@Named("blaa") String value"
 * 
 * @author mkuokkanen
 */
public class ConfigurationModule extends AbstractModule {

    private static Logger logger = LoggerFactory.getLogger(ConfigurationModule.class);
    private static final String PROPERTIES_NAME = "/application.properties";

    @Override
    protected void configure() {
        Properties properties;

        try (InputStream in = ConfigurationModule.class.getResourceAsStream(PROPERTIES_NAME)) {
            properties = new Properties();
            properties.load(in);
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            logger.error("Could not load properties from file '{}', software will not function properly.",
                    PROPERTIES_NAME, e);
        }
    }
}