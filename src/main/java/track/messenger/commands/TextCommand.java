package track.messenger.commands;

import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;
import track.messenger.store.dao.ChatRelation;
import track.messenger.store.dao.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.MessageStore;

/**
 * Created by geoolekom on 14.11.16.
 */
public class TextCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException {
        ChatRelationStore chatRelations = (ChatRelationStore) stores.get(ChatRelation.class);
        MessageStore messages = (MessageStore) stores.get(TextMessage.class);
        try {
            User user = session.getUser();
            TextMessage msg = (TextMessage) message;
            if (user != null) {
                Chat chat = chatRelations.getChat(msg.getChatId());
                if (chat != null && chat.contains(user)) {
                    messages.saveMessage(msg);
                    session.setActiveChatId(msg.getChatId());
                    session.send(new StatusMessage(user, Status.MESSAGE_DELIVERED, chat.getId().toString()));
                    /*
                    ChatHistMessage chatHistMessage;
                    for (Integer userId : chat.getParticipants()) {
                        chatHistMessage = new ChatHistMessage();
                        chatHistMessage.setSenderId(userId);
                    }
                    */
                } else {
                    session.send(new StatusMessage(user, Status.WRONG_DESTINATION));
                }
            } else {
                session.send(new StatusMessage(user, Status.NOT_AUTHORIZED));
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка отправки сообщения. ");
        }
    }
}
