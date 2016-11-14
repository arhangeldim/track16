package track.messenger.store;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;

import oracle.jdbc.*;


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
    private Map<String, Class> typeMap;

    public Store() {

    }

    private Map<String, Class> getTypeMap() {
        typeMap = new HashMap<>();
        typeMap.put("CHAR", String.class);
        typeMap.put("VARCHAR", String.class);
        typeMap.put("LONGVARCHAR", String.class);
        typeMap.put("NUMERIC", java.math.BigDecimal.class);
        typeMap.put("DECIMAL", java.math.BigDecimal.class);
        typeMap.put("BIT", boolean.class);
        typeMap.put("BOOLEAN", boolean.class);
        typeMap.put("TINYINT", byte.class);
        typeMap.put("SMALLINT", short.class);
        typeMap.put("INTEGER", int.class);
        typeMap.put("BIGINT", long.class);
        typeMap.put("REAL", float.class);
        typeMap.put("FLOAT", double.class);
        typeMap.put("DOUBLE", double.class);
        typeMap.put("BINARY", byte[].class);
        typeMap.put("VARBINARY", byte[].class);
        typeMap.put("LONGVARBINARY", byte[].class);
        typeMap.put("DATE", java.sql.Date.class);
        typeMap.put("TIME", java.sql.Time.class);
        typeMap.put("TIMESTAMP", java.sql.Timestamp.class);
        typeMap.put("CLOB", java.sql.Clob.class);
        typeMap.put("BLOB", java.sql.Blob.class);
        typeMap.put("ARRAY", java.sql.Array.class);
        typeMap.put("STRUCT", java.sql.Struct.class);
        typeMap.put("REF", Ref.class);
        typeMap.put("DATALINK", java.net.URL.class);
        typeMap.put("JAVA_OBJECT", dataClass);
        return typeMap;
    }

    private Map<Class, String> getClassMap() {
        if (typeMap == null) {
            return null;
        }
        classMap = new HashMap<>();
        ArrayList<Class> keys = new ArrayList<Class>(typeMap.values());
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
            typeMap = getTypeMap();
            classMap = getClassMap();
            String sql = createSql();
            statement = connection.prepareStatement(sql);
            /*
            int counter = 0;
            for (Field field : dataClass.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                    continue;
                }
                statement.setObject(counter++, field.getName());
                statement.setObject(counter++, classMap.get(field.getType()));
            }*/
            statement.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("Ошибка инициализации базы данных: " + sqle.toString());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Ошибка класса");
        }
    }

    private String createSql() {
        String sql =
                "create table if not exists '" + tableName +
                "' ('id' integer primary key autoincrement";
        for (Field field : dataClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                continue;
            }
            //sql += ", '?' ?";
            sql += ", '" + field.getName() + "' " + classMap.get(field.getType());
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
            if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                continue;
            }
            sql += "'" + field.getName() + "', ";
        }
        sql = sql.substring(0, sql.length() - 2) + ") values (";
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                continue;
            }
            sql += "?, ";
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
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
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
        System.out.println(insertSql(fields));
        for (Object obj : list) {
            int counter = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                    continue;
                }
                statement.setObject(counter++, field.get(obj));
            }
        }
        statement.executeUpdate();
    }

    public void close() throws SQLException {
        connection.close();
        statement.close();
        resultRows.close();
    }
}
