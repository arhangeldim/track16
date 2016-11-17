package track.messenger.commands;

import track.messenger.messages.*;
import track.messenger.store.dao.User;
import track.messenger.net.Session;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatHistCommand implements Command {

    private ChatRelationStore chatRelations;
    private MessageStore messages;
    private UserStore users;

    public ChatHistCommand(ChatRelationStore chatRelations, MessageStore messages, UserStore users) {
        this.chatRelations = chatRelations;
        this.messages = messages;
        this.users = users;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {
        ChatHistMessage msg = (ChatHistMessage) message;
        try {
            Chat chat = chatRelations.getChat(msg.getChatId());
            if (chat != null && chat.contains(msg.getSenderId())) {
                List<String> history = messages.getChatHistory(chat.getId())
                        .stream()
                        .sorted((first, second) -> first.getTimestampAsDate().compareTo(second.getTimestampAsDate()))
                        .map(textMessage -> {
                            User user = users.getUser(textMessage.getSenderId());
                            return textMessage.getTimestamp() + " " + user.getUsername() + ": " + textMessage.getText();
                        }).collect(Collectors.toList());
                session.send(new ChatHistResultMessage(session.getUser(), history));
            } else {
                session.send(new StatusMessage(session.getUser(), Status.WRONG_DESTINATION));
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка получения истории переписки. " + e.toString());
        }
    }
}
