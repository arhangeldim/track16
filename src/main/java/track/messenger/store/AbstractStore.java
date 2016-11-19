package track.messenger.store;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.sql.*;
import java.util.List;

/**
 * Created by geoolekom on 17.11.16.
 */
public abstract class AbstractStore<T> {

    private SQLiteConnectionPoolDataSource dataSource;
    private String databaseName;
    private String tableName;

    public void connect() {
        dataSource = new SQLiteConnectionPoolDataSource();
        dataSource.setUrl("jdbc:sqlite:" + databaseName);
        dataSource.setDatabaseName(databaseName);
    }

    protected List<T> get(String filter) {
        List<T> objects;
        try (Connection connection = dataSource.getPooledConnection().getConnection();
             PreparedStatement statement = connection
                        .prepareStatement("select * from " + tableName + " where " + filter + ";")) {
            ResultSet resultSet = statement.executeQuery();
            objects = fill(resultSet);
        } catch (SQLException e) {
            objects = null;
        }
        return objects;
    }

    protected void save(List<T> objects) {
        if (objects == null || objects.size() == 0) {
            System.out.println(this.getClass() + ": попытка сохранить пустой список. ");
            return;
        }
        try (Connection connection = dataSource.getPooledConnection().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into '" + tableName + "' " +
                             columns() + " values " + values(objects) + ";")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(this.getClass() + ": ошибка добавления в базу. ");
        }
    }

    protected Integer getMax(String field) {
        Integer maxId;
        try (Connection connection = dataSource.getPooledConnection().getConnection();
             PreparedStatement statement = connection
                        .prepareStatement("select max(" + field + ") as m from " + tableName + ";")) {
            ResultSet resultRows = statement.executeQuery();
            maxId = resultRows.getInt("m");
        } catch (SQLException e) {
            maxId = 1;
        }
        return maxId;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public abstract List<T> fill(ResultSet resultSet) throws SQLException;

    public abstract String values(List<T> objects) throws SQLException;

    public abstract String columns();

    public abstract Class getDataClass();

}
