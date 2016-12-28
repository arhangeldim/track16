package track.messenger.messages.server;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

import java.util.List;

public class ChatHistResultMessage extends ResultMessage {
    public ChatHistResultMessage(ChatHistResult result, Status status, String errorMessage) {
        super(Type.MSG_CHAT_HIST_RESULT);
        this.result = result;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    class ChatHistResult extends Result{
        public List<Message> chatHist;
    }
}
