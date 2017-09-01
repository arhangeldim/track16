package track.messenger.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;
import track.messenger.messages.MessageFactory;
import track.messenger.messages.Type;

/**
 * Created by ksushar on 1/26/17.
 */
public class JsonProtocol implements Protocol {

    static Logger log = LoggerFactory.getLogger(JsonProtocol.class);

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String typeString = mapper.readTree(bytes).get("type").asText();
            return mapper.readValue(bytes, MessageFactory.get(Type.valueOf(typeString)));

        } catch (Exception e) {
            throw new ProtocolException(e);
        }
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            throw new ProtocolException(e);
        }
    }
}
