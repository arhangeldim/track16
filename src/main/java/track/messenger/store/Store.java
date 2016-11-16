package track.messenger.store;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import oracle.jdbc.*;

import javax.annotation.PreDestroy;

/**
 * Created by geoolekom on 14.11.16.
 */

public abstract class Store {

    private String dbName;
    private String className;

    private String tableName;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultRows;

    private Class dataClass;
    private List<String> fieldNames = new LinkedList<>();
    private String field;

    private Map<Class, String> classMap;
    private Map<String, Class> typeMap;

    public Store() {}

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setClassName(String className) {
        this.className = className;
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
        typeMap.put("INTEGER", Integer.class);
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

    public void setField(String fieldName) {
        fieldNames.add(fieldName);
    }

    public void connect() {
        try {
            dataClass = Class.forName(className);
            tableName = dataClass.getSimpleName();
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            typeMap = getTypeMap();
            classMap = getClassMap();

            String sql = createSql();
            statement = connection.prepareStatement(sql);
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

    public LinkedList<Object> get(String filter) throws Exception {
        statement = connection.prepareStatement(selectSql(filter));
        resultRows = statement.executeQuery();
        LinkedList<Object> resultObjects = new LinkedList<>();
        while (resultRows.next()) {
            Object obj = dataClass.newInstance();
            fieldNames.stream()
                    .forEach(fieldName -> {
                        String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        try {
                            dataClass
                                    .getMethod(methodName, String.class)
                                    .invoke(obj, resultRows.getString(fieldName));
                        } catch (Exception e) {
                            return;
                        }
                    });
            dataClass.getMethod("setId", Integer.class).invoke(obj, resultRows.getInt("id"));
            resultObjects.add(obj);
        }
        return resultObjects;
    }

    public void save(List<? extends Object> list) throws Exception {
        statement = connection.prepareStatement(insertSql());
        for (Object obj : list) {
            //Integer id = (Integer) dataClass.getMethod("getId", new Class[] {}).invoke(obj);
            //statement.setInt(1, id);
            List<Object> values = fieldNames.stream()
                    .map(fieldName -> {
                        String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        try {
                            return dataClass
                                    .getMethod(methodName, new Class[] {})
                                    .invoke(obj);
                        } catch (Exception e) {
                            return null;
                        }
                    }).collect(Collectors.toList());
            int counter = 1;
            for (Object value : values) {
                statement.setObject(counter++, value);
            }
            statement.executeUpdate();
        }
    }

    public Integer getMax(String field) throws SQLException {
        statement = connection.prepareStatement("select max(" + field + ") as m from " + tableName + ";");
        resultRows = statement.executeQuery();
        return resultRows.getInt("m");
    }

    @PreDestroy
    public void close() throws SQLException {
        connection.close();
        statement.close();
        resultRows.close();
    }
}
