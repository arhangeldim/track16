package track.messenger.commands;

import track.messenger.Chat;
import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.client.ChatCreateMessage;
import track.messenger.messages.server.ChatCreateResultMessage;
import track.messenger.messages.server.ResultMessage;
import track.messenger.messages.server.StatusMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;
import track.messenger.store.UserStore;

import java.io.IOException;
import java.util.stream.Collectors;

public class ChatCreateCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        if (! session.hasLogined()) {
            session.send(new StatusMessage(
                    ResultMessage.Status.FAIL,
                    "You need to login")
            );
            return;
        }
        ChatCreateMessage chatCreateMessage = (ChatCreateMessage) message;
        MessageStore messageStore = (MessageStore) storeFactory.get(Type.MESSAGE_STORE);
        UserStore userStore = (UserStore) storeFactory.get(Type.USER_STORE);
        if (chatCreateMessage.getUserIds().size() == 1) {
            Long userId = chatCreateMessage.getUserIds().get(0);
            //TODO
            return;
        }
        if (chatCreateMessage.getUserIds().size() > 1) {
            Chat chat = messageStore.addChat(
                    new Chat(chatCreateMessage
                            .getUserIds()
                            .stream()
                            .map(userStore::getUserById)
                            .collect(Collectors.toList())
                    )
            );
            session.send(new ChatCreateResultMessage(new ChatCreateResultMessage.ChatCreateResult(chat.getId()),
                    ResultMessage.Status.OK,
                    null));
        }
    }
}
