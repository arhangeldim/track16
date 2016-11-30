package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;

/**
 * Created by geoolekom on 14.11.16.
 */
public interface Command {
    void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException;
}
