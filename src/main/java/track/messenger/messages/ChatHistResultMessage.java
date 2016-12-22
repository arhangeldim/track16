package track.messenger.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.store.Usermessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 21.11.16.
 */
public class ChatHistResultMessage extends Message{
    List<String> text;
    List<String> name;
    static Logger log = LoggerFactory.getLogger(ChatHistResultMessage.class);
    public ChatHistResultMessage() {
        super(null);
    }
    public void setHistory(List<Usermessage> history) {
        text = new ArrayList<>();
        name = new ArrayList<>();
        for (int i = 0; i < history.size(); i++) {
            text.add(history.get(i).getMessage());
            name.add(history.get(i).getname());
        }
        log.info("text: {}", text);
        log.info("name: {}", name);
    }

    public List<String> getMessage() {
        return text;
    }

    public List<String> getName() {
        return name;
    }
}
