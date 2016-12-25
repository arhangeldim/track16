package track.messenger.messages;

/**
 * Created by Александр on 25.12.2016.
 */
public class InfoResultMessage extends Message {
    Result result;
    Status status;
    String errorMessage;

    public InfoResultMessage(Result result, Status status, String errorMessage) {
        super(Type.MSG_INFO_RESULT);
        this.result = result;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public class Result{
        public Long userId;
        public String userLogin;

        public Result(Long userId, String userLogin) {
            this.userId = userId;
            this.userLogin = userLogin;
        }
    }

    public enum Status{
        OK,
        FAIL
    }
}
