package track.messenger.commands;

import track.messenger.messages.ChatListResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.dao.ChatRelation;

import java.util.List;

/**
 * Created by geoolekom on 17.11.16.
 */
public class ChatListCommand implements Command {


    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException {
        ChatRelationStore chatRelations = (ChatRelationStore) stores.get(ChatRelation.class);
        try {
            List<Integer> chatIds = chatRelations.getUserChats(session.getUser().getId());
            session.send(new ChatListResultMessage(session.getUser(), chatIds));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка получения списка чатов. ");
        }
    }
}
