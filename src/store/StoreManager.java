package store;

import connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreManager {
    Connection connection;

    public StoreManager(Connection connection) {
        this.connection = connection;
    }

    public void insertStore(String storeName, String storeCode) {
        String insertSql = "insert into Store(storeName,storeCode) values(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, storeName);
            preparedStatement.setString(2, storeCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Store findStore(String storeCode) {
        Store store = new Store();
        String selectSql = "select * from Store where storeCode = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1, storeCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                store.setStoreId(resultSet.getInt("storeId"));
                store.setStoreName(resultSet.getString("storeName"));
                store.setStoreCode(resultSet.getString("storeCode"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return store;
    }
}
