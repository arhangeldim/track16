package track.messenger.messages;

import java.util.Objects;

/**
 * Created by user on 03.12.16.
 */
public class ChatInfMessage extends Message {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatInfMessage(Long senderID, Long ID, Type type)
    {
        setSenderId(senderID);
        setId(ID);
        setType(type);
    }

    @Override
    public String toString() {
        return "ChatInfMessage{" +
                "text='" + getText() + '\'' +
                '}';
    }
}
