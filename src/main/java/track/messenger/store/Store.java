package track.messenger.store;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

/**
 * Created by geoolekom on 14.11.16.
 */

public abstract class Store {

    private String dbName;
    private String className;

    private String tableName;
    private SQLiteConnectionPoolDataSource dataSource;

    private Class dataClass;
    private List<String> fieldNames = new LinkedList<>();
    private String field;

    public Store() {}

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setField(String fieldName) {
        fieldNames.add(fieldName);
    }

    public void connect() {
        try {
            dataSource = new SQLiteConnectionPoolDataSource();
            dataSource.setUrl("jdbc:sqlite:" + dbName);
            dataSource.setDatabaseName(dbName);

            dataClass = Class.forName(className);
            tableName = dataClass.getSimpleName();

            Connection connection = dataSource.getPooledConnection().getConnection();
            PreparedStatement statement = connection.prepareStatement(createSql());
            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException sqle) {
            System.out.println("Ошибка инициализации базы данных: " + sqle.toString());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Ошибка класса модели. ");
        }
    }

    public LinkedList<Object> get(String filter) throws Exception {
        Connection connection = dataSource.getPooledConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(selectSql(filter));
        ResultSet resultRows = statement.executeQuery();
        LinkedList<Object> resultObjects = new LinkedList<>();
        while (resultRows.next()) {
            Object obj = dataClass.newInstance();
            fieldNames
                    .stream().forEach(fieldName -> {
                        String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        try {
                            dataClass.getMethod(methodName, String.class)
                                    .invoke(obj, resultRows.getString(fieldName));
                        } catch (Exception e) {
                            return;
                        }
                    });
            dataClass.getMethod("setId", Integer.class).invoke(obj, resultRows.getInt("id"));
            resultObjects.add(obj);
        }

        resultRows.close();
        statement.close();
        connection.close();
        return resultObjects;
    }

    public void save(List<? extends Object> list) throws Exception {
        Connection connection = dataSource.getPooledConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(insertSql());
        for (Object obj : list) {
            List<Object> values = fieldNames.stream()
                    .map(fieldName -> {
                        String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        try {
                            return dataClass.getMethod(methodName, new Class[] {})
                                    .invoke(obj);
                        } catch (Exception e) {
                            return null;
                        }
                    }).collect(Collectors.toList());
            int counter = 1;
            for (Object value : values) {
                statement.setString(counter++, value.toString());
            }
            statement.executeUpdate();

            statement.close();
            connection.close();
        }
    }

    public Integer getMax(String field) throws SQLException {
        Connection connection = dataSource.getPooledConnection().getConnection();
        PreparedStatement statement = connection
                .prepareStatement("select max(" + field + ") as m from " + tableName + ";");
        ResultSet resultRows = statement.executeQuery();

        resultRows.close();
        statement.close();
        connection.close();
        return resultRows.getInt("m");
    }

    private String createSql() {
        String sql =
                "create table if not exists '" + tableName +
                        "' ('id' integer primary key autoincrement";

        sql += fieldNames.stream()
                .map(fieldName -> ", '" + fieldName + "' varchar")
                .reduce((current, next) -> current + next).orElse("");

        return sql + ");";
    }

    private String selectSql(String filter) {
        String sql =
                "select * from " + tableName +
                        " where " + filter;
        return sql;
    }

    private String insertSql() {
        String sql =
                "insert into '" + tableName + "' (";

        sql += fieldNames.stream()
                .map(fieldName -> "'" + fieldName + "', ")
                .reduce((current, next) -> current + next).orElse("");

        sql = sql.substring(0, sql.length() - 2) + ") values (";

        sql += fieldNames.stream()
                .map(fieldName -> "?, ")
                .reduce((current, next) -> current + next).orElse("");

        return sql.substring(0, sql.length() - 2) + ");";
    }
}
