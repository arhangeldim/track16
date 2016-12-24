package track.messenger.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.*;

import java.io.IOException;



public class JsonProtocol implements Protocol {
    static Logger log = LoggerFactory.getLogger(JsonProtocol.class);
    
    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String typeString = mapper.readTree(bytes).get("type").asText();
            return mapper.readValue(bytes, MessageClassFactory.get(
                    Type.valueOf(typeString)));
        } catch (IOException e) {
            throw new ProtocolException(e);
        }
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(msg);
            log.info(jsonString);
            return jsonString.getBytes();
        } catch (JsonProcessingException e) {
            throw new ProtocolException(e);
        }
    }
}
