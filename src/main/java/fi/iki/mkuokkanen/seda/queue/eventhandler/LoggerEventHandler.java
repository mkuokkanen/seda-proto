package fi.iki.mkuokkanen.seda.queue.eventhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

import fi.iki.mkuokkanen.seda.queue.event.Message;

/**
 * Simple eventhandler to log messages with slf4j.
 * 
 * @author Mikko Kuokkanen
 */
public class LoggerEventHandler implements EventHandler<Message> {

    private static Logger logger = LoggerFactory.getLogger(LoggerEventHandler.class);

    private final String name;

    public LoggerEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {
        logger.info("Name: {}, seq: {}, event: {}", name, sequence, event);
    }

}
