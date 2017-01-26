package track.messenger.messages.server;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

/**
 * Created on 13.01.2017.
 */
public class ChatCreateResultMessage extends ResultMessage {

    public ChatCreateResult result;

    public ChatCreateResultMessage() {
        super(Type.MSG_CHAT_CREATE_RESULT);
    }

    public ChatCreateResultMessage(ChatCreateResult result, Status status, String errorMessage) {
        super(Type.MSG_CHAT_CREATE_RESULT);
        this.result = result;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static class ChatCreateResult extends Result {
        public ChatCreateResult() {
        }

        public ChatCreateResult(Long chatId) {
            this.chatId = chatId;
        }

        public Long chatId;
    }
}
