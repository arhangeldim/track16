package track.messenger.store;

import track.messenger.User;

/**
 * Created by asus on 22.12.16.
 */
public class Usermessage {
    private User user;
    private String text;

    public void setUser (User user) {
        this.user = user;
    }

    public void setMessage (String text) {
        this.text = text;
    }

    public String getMessage () {
        return text;
    }

    public String getname () {
        return user.getName();
    }

    public Long getId () {
        return user.getId();
    }
}
