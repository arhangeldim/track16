package track.messenger.store;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by geoolekom on 14.11.16.
 */
public class Store {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultRows;
    private Class dataClass;
    private String tableName;
    private Map<Class, String> classMap;
    private Map<String, Class<?>> typeMap;

    public Store() {

    }

    private Map<Class, String> getClassMap() {
        if (typeMap == null) {
            return null;
        }
        classMap = new HashMap<>();
        ArrayList<Class<?>> keys = (ArrayList<Class<?>>) typeMap.values();
        Set<String> values = typeMap.keySet();
        for (Class key : keys) {
            for (String value : values) {
                if (key.equals(typeMap.get(value))) {
                    classMap.put(key, value);
                }
            }
        }
        return classMap;
    }

    public Store(String className, String dbName) {
        try {
            dataClass = Class.forName(className);
            tableName = dataClass.getSimpleName();
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            typeMap = connection.getTypeMap();
            classMap = getClassMap();
            String sql = createSql();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка инициализации базы данных");
        }
    }

    private String createSql() {
        String sql =
                "create table if not exists '" + tableName +
                "' ('id' integer primary key not null autoincrement";
        for (Field field : dataClass.getDeclaredFields()) {
            sql += ", '" + field.getName() + "' " + classMap.get(field.getType()) + " not null";
        }
        sql += ");";
        return sql;
    }

    private String selectSql(String filter) {
        String sql =
                "select * from " + tableName +
                " where " + filter;
        return sql;
    }

    private String insertSql(Field[] fields) {
        String sql =
                "insert into '" + tableName + "' (";
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                sql += "'" + field.getName() + "', ";
            }
        }
        sql = sql.substring(0, sql.length() - 2) + ") values (";
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                sql += "?, ";
            }
        }
        return sql.substring(0, sql.length() - 2) + ");";
    }

    public LinkedList<Object> get(String filter) throws Exception {
        statement = connection.prepareStatement(selectSql(filter));
        resultRows = statement.executeQuery();
        LinkedList<Object> resultObjects = new LinkedList<>();
        while (resultRows.next()) {
            Object obj = dataClass.newInstance();
            for (Field field : dataClass.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(obj, field.getType().cast(resultRows.getObject(field.getName())));
            }
            resultObjects.add(obj);
        }
        return resultObjects;
    }

    public void save(LinkedList<? extends Object> list) throws Exception {
        Field[] fields = dataClass.getDeclaredFields();
        statement = connection.prepareStatement(insertSql(fields));
        for (Object obj : list) {
            int counter = 0;
            for (Field field : fields) {
                if (field.getName().equals("id")) {
                    continue;
                }
                statement.setObject(counter++, field.get(obj));
            }
        }
        statement.execute();
    }

    public void close() throws SQLException {
        connection.close();
        statement.close();
        resultRows.close();
    }
}
