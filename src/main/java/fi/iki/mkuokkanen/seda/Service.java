package fi.iki.mkuokkanen.seda;

/**
 * Class that implements I/O stuff. We want to separate I/O start from
 * constructions.
 * 
 * @author mkuokkanen
 */
public interface Service {

    /**
     * Start operations.
     */
    void start();

    /**
     * Stop operations.
     */
    void stop();
}
