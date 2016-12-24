package track.messenger.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import track.messenger.messages.ChatListMessage;
import track.messenger.messages.InfoMessage;
import track.messenger.messages.Message;

import static org.junit.Assert.*;

public class JsonProtocolTest {
    @Test
    public void decode() throws Exception {
        JsonProtocol protocol = new JsonProtocol();
        String jsonString = "{\"id\":null," +
                "\"senderId\":null," +
                "\"type\":\"MSG_INFO\"," +
                "\"userId\":10}";
        assertArrayEquals(protocol.decode(jsonString.getBytes()).toString().getBytes(), jsonString.getBytes());
    }

    @Test
    public void encode() throws Exception {
        JsonProtocol protocol = new JsonProtocol();
        InfoMessage message = new InfoMessage();
        message.setUserId(10);
        assertArrayEquals(protocol.encode(message),
                "{\"id\":null,\"senderId\":null,\"type\":\"MSG_INFO\",\"userId\":10}".getBytes());
    }
}