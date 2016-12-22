package track.messenger.db;

import java.sql.*;

/**
 * Created by asus on 01.12.16.
 */
public class CreateDB {
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");

        Statement stmt;
        String sql;

        /*stmt = connection.createStatement();

        sql = "CREATE TABLE IF NOT EXISTS User " +
                "(id SERIAL PRIMARY KEY," +
                " login             VARCHAR(255)    NOT NULL UNIQUE," +
                " password          VARCHAR(255)    NOT NULL)";
        stmt.executeUpdate(sql);
        stmt.close();
        connection.setAutoCommit(false);

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Chat " +
                "(id SERIAL PRIMARY KEY," +
                " owner_id SERIAL references User(id))";
        stmt.executeUpdate(sql);
        stmt.close();
*/
        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Message " +
                "(id      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " text    TEXT    NOT NULL," +
                " chat_id SERIAL references Chat(id)," +
                " user_id SERIAL references User(id)," +
                " timestamp TIMESTAMP NOT NULL DEFAULT current_timestamp)";
        stmt.executeUpdate(sql);
        stmt.close();
/*
        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Chat_User " +
                "(chat_id SERIAL references Chat(id)," +
                " user_id SERIAL references User(id)," +
                "PRIMARY KEY(chat_id, user_id))";
        stmt.executeUpdate(sql);
        stmt.close();
*/
        connection.close();
    }
}
