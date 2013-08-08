package fi.iki.mkuokkanen.seda.queue.event;

import javolution.io.Struct;

public class ResponseBroadcastStruct extends Struct {
    public final Signed16 keyCount = new Signed16();

    public final UTF8String[] key = array(new UTF8String[100], 64);
    public final UTF8String[] value = array(new UTF8String[100], 64);
}
