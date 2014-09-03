package fi.iki.mkuokkanen.seda.queue.event;

import com.google.common.base.MoreObjects;
import com.lmax.disruptor.EventFactory;
import javolution.io.Struct;

import java.nio.ByteBuffer;

/**
 * Descibes one message, that is stored in disruptor queueu. Provides
 * preallocated memory.
 *
 * @author mkuokkanen
 */
public class Message extends Struct {

    public final Signed64 id = new Signed64();

    public final Enum32<MessageType> type = new Enum32<MessageType>(MessageType.values());

    public final RequestPushStruct asPush = inner(new RequestPushStruct());
    public final RequestDeleteStruct asDelete = inner(new RequestDeleteStruct());
    public final RequestBroadcastStruct asBroadcast = inner(new RequestBroadcastStruct());
    public final ResponseBroadcastStruct asBroadcastResponse = inner(new ResponseBroadcastStruct());

    /**
     * Private constructor, these won't be created from outside
     */
    private Message() {
        super();
        type.set(MessageType.UNKNOWN);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("id", id)
                .add("type", type)
                .toString();
    }

    public final static EventFactory<Message> FACTORY = new EventFactory<Message>() {
        @Override
        public Message newInstance() {
            Message newMsg = new Message();
            newMsg.setByteBuffer(ByteBuffer.allocate(newMsg.size()), 0);
            return newMsg;
        }
    };

}
