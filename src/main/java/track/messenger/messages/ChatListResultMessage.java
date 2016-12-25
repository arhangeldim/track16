package track.messenger.messages;

import java.util.List;

/**
 * Created by Александр on 25.12.2016.
 */
public class ChatListResultMessage extends Message {
    Result result;
    Status status;
    String errorMessage;

    public ChatListResultMessage() {
        super(Type.MSG_CHAT_LIST_RESULT);
    }

    class Result{
        List<Long> chatList;
    }

    enum Status{
        OK,
        FAIL
    }
}
