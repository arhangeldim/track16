package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.MessageStore;

/**
 * Created by geoolekom on 14.11.16.
 */
public class TextCommand implements Command {

    private ChatRelationStore chats;
    private MessageStore messages;

    public TextCommand(ChatRelationStore chats, MessageStore messages) {
        this.chats = chats;
        this.messages = messages;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            User user = session.getUser();
            TextMessage msg = (TextMessage) message;
            if (user != null) {
                Chat chat = chats.getChat(msg.getChatId());
                if (chat != null && chat.contains(user)) {
                    messages.saveMessage(msg);
                    session.send(new StatusMessage(user, Status.MESSAGE_DELIVERED, chat.getId().toString()));
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
