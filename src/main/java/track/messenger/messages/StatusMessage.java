package track.messenger.messages;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by ksushar on 1/26/17.
 */
public class StatusMessage extends Message {
    public enum Result {
        OK,
        FAIL
    }

    private Result result;
    private String status;

    public StatusMessage() {
        this.setType(Type.MSG_STATUS);
    }

    ;

    public StatusMessage(String resultString) {
        this.setType(Type.MSG_STATUS);
        result = Result.valueOf(resultString);
    }

    public StatusMessage(String stringResult, String status) {
        this.setType(Type.MSG_STATUS);
        result = Result.valueOf(stringResult);
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        StatusMessage message = (StatusMessage) other;
        if (result != message.getResult()) {
            return false;
        }
        return status.equals(message.getStatus());
    }

    @Override
    public String toString() {
        return "StatusMessage{ result=" + result.toString() + ", " +
                "status=" + status + " }";
    }

}
