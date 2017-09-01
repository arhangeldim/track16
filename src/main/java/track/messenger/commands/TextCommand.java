package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

public class TextCommand implements Command {
    @Override
    public void execute(Session session, Message message, MessageStore messageStore, UserStore userStore) {
        TextMessage textMessage = (TextMessage) message;
    }
}
