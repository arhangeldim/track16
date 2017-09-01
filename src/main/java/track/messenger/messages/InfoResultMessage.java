package track.messenger.messages;

import track.messenger.User;

public class InfoResultMessage extends Message {
    private Long userId;
    private String info;

    public InfoResultMessage() {
        this.setType(Type.MSG_INFO_RESULT);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public InfoResultMessage(Long userId, String info) {
        this.userId = userId;
        this.info = info;
        this.setType(Type.MSG_INFO_RESULT);
    }

    @Override
    public String toString() {
        return "InfoResultMessage{" +
                "userId='" + Long.toString(userId) + "', " +
                "info='" + info +
                "'}";
    }
}
