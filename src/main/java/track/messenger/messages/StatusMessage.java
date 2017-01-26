package track.messenger.messages;

/**
 * Created by ksushar on 1/26/17.
 */
public class StatusMessage extends Message {
    private enum Status {
        OK,
        ERROR
    }

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
        return status == message.getStatus();
    }

    @Override
    public String toString() {
        return "StatusMessage{ status=" + status.toString() + "}";
    }

}
