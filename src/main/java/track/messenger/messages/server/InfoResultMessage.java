package track.messenger.messages.server;

import track.messenger.messages.Type;
import track.messenger.messages.server.ResultMessage;

public class InfoResultMessage extends ResultMessage {
    public InfoResultMessage(InfoResult result, Status status, String errorMessage) {
        super(Type.MSG_INFO_RESULT);
        this.result = result;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static class InfoResult extends Result {
        public Long userId;
        public String userLogin;

        public InfoResult(Long userId, String userLogin) {
            this.userId = userId;
            this.userLogin = userLogin;
        }
    }
}
