package track.messenger.net;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by user on 21.11.16.
 */
public class SqliteManager {

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public SqliteManager() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/home/user/Загрузки/idea-IU-162.2032.8/bin/MessengerDB.sqlite");
            // Db connected
            System.out.println("Db was connected");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void connectToDb() {
    }

    public ResultSet getItemWithSameLogin(String login) throws SQLException {

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        return resultSet;
    }

    public void postMessageToFrom(Long sendToId, Long senderId, String text) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String query = "INSERT INTO Chat" + sendToId.toString() + " (Pers" + senderId.toString() + ")"
                    + " VALUES ('" + text + "')";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CreateTableFor(Long loginId, ArrayList<Long> idToDo) {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM sqlite_master as tables where type='table';");
            Long number = Long.valueOf(resultSet.getInt(1)) + 1l;
            String query = "CREATE TABLE Chat" + number.toString() + " ( id INTEGER PRIMARY KEY NOT NULL, Pers" + loginId.toString() + " TEXT, ";
            Long[] id = idToDo.toArray(new Long[idToDo.size()]);
            for (int i = 0; i < id.length; i++) {
                String adding;
                if (i + 1 == id.length)
                    adding = "Pers" + id[i].toString() + " TEXT );";
                else
                    adding = "Pers" + id[i].toString() + " TEXT, ";
                query += adding;
            }
            try {
                statement = connection.createStatement();
                statement.execute(query);
                statement = connection.createStatement();
                query = "INSERT INTO UserChats" + loginId.toString() + " (Chatids) VALUES (" + number.toString() + ");";
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Long contains(String login) throws SQLException {
        statement = connection.createStatement();
        String query = "SELECT * FROM users WHERE login like '%" + login + "%' ;";
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.isAfterLast())
            return 0l;
        else {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            return resultSet.getLong(1) + 1l;
        }
    }

    public void addNewUser(String login, String password) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String query = "INSERT INTO users (login, password) VALUES ('" + login + "', '" + password + "');";
            statement.execute(query);

            Long id = getContainsId(login);
            statement = connection.createStatement();
            query = "CREATE TABLE UserChats" + id.toString() + " ( id INTEGER PRIMARY KEY NOT NULL, Chatids INTEGER );";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Long getContainsId(String login) {

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE login like '%" + login + "%' ;";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getLong("id");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    return 123l;
}

    public ResultSet getChatList(Long loginId) {

        try {
            statement = connection.createStatement();
            String query = "SELECT ChatIds FROM UserChats" + loginId.toString();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ResultSet getChatHist(Long loginId) {

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM Chat" + loginId.toString();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
}
