package track.messenger.messages;

import java.util.List;

public class ChatHistResultMessage extends Message {
    Result result;
    Status status;
    String errorMessage;

    public ChatHistResultMessage() {
        super(Type.MSG_CHAT_HIST_RESULT);
    }

    class Result{
        public List<Long> chatHist;
    }

    enum Status{
        OK,
        FAIL
    }
}
