package fi.iki.mkuokkanen.seda.queue.event;

import javolution.io.Struct;

public class RequestPushStruct extends Struct {
    public final UTF8String key = new UTF8String(64);
    public final UTF8String value = new UTF8String(64);
}
