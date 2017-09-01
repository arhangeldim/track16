package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

/**
 * Created by ksushar on 1/26/17.
 */
public interface Command {
    public void execute(Session session, Message message, MessageStore messageStore, UserStore userStore);
}
