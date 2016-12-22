package track.messenger.db;

import track.messenger.User;
import track.messenger.messages.TextMessage;
import track.messenger.messages.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 30.11.16.
 */
public class DBConnectionManager {
    private Connection connection;
    private String tableName;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public DBConnectionManager() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");
        connection.setAutoCommit(false);

    }

    public void connect() throws SQLException {
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("connectionerror" + e);
        }
    }

    public void setsql(String sql) {
        this.sql = sql;
    }

    public void commit() throws SQLException {
        try {
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Commiterror" + e);
        }
    }

    public void setResultset(String sql) throws SQLException {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("error in setResultset" + e);
        }
    }

    public User searchUser() throws SQLException {
        try {
            while (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                Long id = rs.getLong("id");
                User user = new User();
                user.setName(login);
                user.setPassword(password);
                user.setId(id);
                return user;
            }
        } catch (SQLException e) {
            System.err.println("error in searchUser" + e);
        }
        return null;
    }

    public List<Long> searchMessages(Long chatId) throws SQLException {
        try {
            List<Long> msgList = new ArrayList<Long>();
            while (rs.next()) {
                msgList.add(rs.getLong("id"));
            }
            return msgList;
        } catch (SQLException e) {
            List<Long> msgList = null;
            System.err.println("error in searchMessages" + e);
            return msgList;
        }
    }

    public TextMessage getMessage(Long messageId) {
        TextMessage msg = null;
        try {
            msg = new TextMessage();
            while (rs.next()) {
                msg.setId(rs.getLong("id"));
                msg.setText(rs.getString("text"));
                msg.setType(Type.MSG_TEXT);
                msg.setSenderId(rs.getLong("user_id"));
                msg.setTime(rs.getString("timestamp"));
                return msg;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("error in getMessage" + e);
            return msg;
        }
    }

    public List<Long> searchchats(Long userId) {
        try {
            List<Long> userList = new ArrayList<Long>();
            while (rs.next()) {
                userList.add(rs.getLong("user_id"));
            }
        } catch (SQLException e) {
            System.err.println("error in searchchats" + e);
        }
        return null;
    }
}
