package track.messenger.teacher.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import track.messenger.messages.TextMessage;

import static org.junit.Assert.*;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class MessengerClientTest {

    @Test
    public void parseMessages() throws Exception {
        MessengerClient messengerClient = new MessengerClient();

        String[] textMessageTokens = {"/text", "10", "text"};
        TextMessage textMessage = new TextMessage();
        textMessage.setText("text");
        textMessage.setChatId(10L);
        assertEquals(textMessage, messengerClient.parseToMessage(textMessageTokens));
    }

}