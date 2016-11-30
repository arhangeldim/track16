package track.messenger.store;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;
import java.util.List;

/**
 * Created by geoolekom on 17.11.16.
 */
public abstract class AbstractStore<T> {

    private ComboPooledDataSource dataSource;
    private String databaseName;
    private String tableName;

    public void connect() {
        dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("org.sqlite.JDBC");
            dataSource.setJdbcUrl("jdbc:sqlite:" + databaseName);
            dataSource.setInitialPoolSize(4);
            dataSource.setMinPoolSize(4);
            dataSource.setUnreturnedConnectionTimeout(10000);
            dataSource.setMaxPoolSize(32);
            dataSource.setMaxStatements(256);
            dataSource.setMaxStatementsPerConnection(16);
        } catch (Exception e) {
            dataSource = null;
        }
    }

    protected List<T> get(String filter) {
        List<T> objects;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                            "select * from " + tableName + " where " + filter + ";",
                            ResultSet.CLOSE_CURSORS_AT_COMMIT
            )) {
            ResultSet resultSet = statement.executeQuery();
            objects = fill(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(this.getClass() + ": ошибка запроса. " + e.toString());
            objects = null;
        }
        return objects;
    }

    protected void save(List<T> objects) {
        if (objects == null || objects.size() == 0) {
            System.out.println(this.getClass() + ": попытка сохранить пустой список. ");
            return;
        }

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "insert into '" + tableName + "' " +
                            columns() + " values " + values() + ";",
                    ResultSet.CLOSE_CURSORS_AT_COMMIT
            )) {
            objects.forEach(object -> {
                try {
                    setup(statement, object);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    //
                }
            });
        } catch (SQLException e) {
            System.out.println(this.getClass() + ": ошибка добавления в базу. "  + e.toString());
        }
    }

    protected Integer getMax(String field) {
        Integer maxId;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select max(" + field + ") as m from " + tableName + ";",
                    ResultSet.CLOSE_CURSORS_AT_COMMIT
            )) {
            ResultSet resultRows = statement.executeQuery();
            maxId = resultRows.getInt("m");
            resultRows.close();
        } catch (SQLException e) {
            maxId = 1;
        }
        return maxId;
    }

    public void close() {
        dataSource.close();
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public abstract List<T> fill(ResultSet resultSet) throws SQLException;

    public abstract String values() throws SQLException;

    public abstract String columns();

    public abstract void setup(PreparedStatement statement, T object) throws SQLException;

    public abstract Class getDataClass();

}