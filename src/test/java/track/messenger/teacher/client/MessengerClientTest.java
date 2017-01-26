package track.messenger.teacher.client;

import org.junit.Test;
import track.messenger.messages.client.TextMessage;

import static org.junit.Assert.*;


public class MessengerClientTest {

    @Test
    public void parseMessages() throws Exception {
        MessengerClient messengerClient = new MessengerClient();

        String[] textMessageTokens = {"/text", "10", "text"};
        TextMessage textMessage = new TextMessage(10L, "text");
        assertEquals(textMessage, messengerClient.parseToMessage(textMessageTokens));
    }

}