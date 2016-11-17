package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */

public abstract class Message implements Serializable {

    private Integer id;
    private Integer senderId;
    private Type type;
    private Date timestamp;

    protected Message() {}

    public Message(User sender, Type type) {
        if (sender != null) {
            this.senderId = sender.getId();
        }
        this.type = type;
        //DateFormat dateFormat = new SimpleDateFormat("MMM d, HH:mm:ss");
        this.timestamp = new Date();
    }

    public Type getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, z HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public Date getTimestampAsDate() {
        return this.timestamp;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = Integer.parseInt(senderId);
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(String timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, z HH:mm:ss");
        try {
            this.timestamp = dateFormat.parse(timestamp);
        } catch (ParseException pe) {
            this.timestamp = new Date();
        }
    }
}
