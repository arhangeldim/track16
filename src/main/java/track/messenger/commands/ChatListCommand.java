package track.messenger.commands;

import track.messenger.messages.ChatListResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.store.ChatRelationStore;

import java.util.List;

/**
 * Created by geoolekom on 17.11.16.
 */
public class ChatListCommand implements Command {

    private ChatRelationStore chatRelations;

    public ChatListCommand(ChatRelationStore chatRelations) {
        this.chatRelations = chatRelations;
    }


    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            List<Integer> chatIds = chatRelations.getUserChats(session.getUser().getId());
            session.send(new ChatListResultMessage(session.getUser(), chatIds));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка получения списка чатов. ");
        }
    }
}
