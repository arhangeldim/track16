package track.messenger.messages;

import track.messenger.store.dao.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatCreateMessage extends Message {

    private List<Integer> participants;

    public List<Integer> getParticipants() {
        return participants;
    }

    public ChatCreateMessage() {
        super(null, Type.MSG_CHAT_CREATE);
    }

    public ChatCreateMessage(User sender, String data) throws InstantiationException {
        super(sender,Type.MSG_CHAT_CREATE);
        if (sender == null) {
            throw new InstantiationException("Вы не авторизованы. ");
        }
        try {
            participants = Arrays.stream(data.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new InstantiationException("Введены не только иды участников. ");
        }
    }
}
