package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;
import org.apache.commons.lang.SerializationUtils;

/**
 * Created by geoolekom on 13.11.16.
 */
public class ObjectProtocol implements Protocol {
    private static Logger log = LoggerFactory.getLogger(ObjectProtocol.class);

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        Message msg = (Message) SerializationUtils.deserialize(bytes);
        log.info("decoded: {}", msg);
        return msg;
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        log.info("encoded: {}", msg);
        return SerializationUtils.serialize(msg);
    }
}
