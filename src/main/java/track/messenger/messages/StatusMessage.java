package track.messenger.messages;

/**
 * Created by ivan on 21.11.16.
 */
public class StatusMessage extends Message{
    private String status;

    public StatusMessage() {
        super(null);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
