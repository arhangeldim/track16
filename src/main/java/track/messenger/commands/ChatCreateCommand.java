package track.messenger.commands;

import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.dao.ChatRelation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatCreateCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException {
        ChatRelationStore chatRelations = (ChatRelationStore) stores.get(ChatRelation.class);
        ChatCreateMessage msg = (ChatCreateMessage) message;
        List<Integer> participants = msg.getParticipants();
        try {
            if (participants.size() == 1) {
                Chat foundChat = chatRelations.getChat(msg.getSenderId(), participants.get(0));
                if (foundChat != null) {
                    String foundChatId = foundChat.getId().toString();
                    session.send(new StatusMessage(session.getUser(), Status.CHAT_EXISTS, foundChatId));
                    return;
                }
            }
            participants.add(msg.getSenderId());
            Integer chatId = chatRelations.getNewChatId();
            List<ChatRelation> relations = participants.stream()
                    .map(partId -> new ChatRelation(msg.getSenderId(), chatId, partId))
                    .collect(Collectors.toList());
            chatRelations.saveRelations(relations);
            session.send(new StatusMessage(session.getUser(), Status.CHAT_CREATED, chatId.toString()));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка создания беседы. " + e.toString());
        }

    }
}
