package track.messenger.store.dao;

import track.messenger.store.UserStore;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */

public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String salt;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword(byte[] rawPassword) {
        this.password = new String(rawPassword);
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "username:\t" + username;
    }
}
