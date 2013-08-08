package fi.iki.mkuokkanen.seda.queue.event;

import javolution.io.Struct;

public class RequestDeleteStruct extends Struct {
    public final UTF8String key = new UTF8String(64);
}
