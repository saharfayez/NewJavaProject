package item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    Connection connection;

    public ItemManager(Connection connection) {
        this.connection = connection;
    }

    public void insertItem(String itemName, String itemCode) {
        String insertSql = "insert into Item(itemName, itemCode) values(?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, itemCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item findItem(String itemCode) {
        Item item = new Item();
        String selectCodeSql = "select * from Item where itemCode = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectCodeSql);
            preparedStatement.setString(1, itemCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                item.setItemId(resultSet.getInt("itemId"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemCode(resultSet.getString("itemCode"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    public List<Item> findItemsByNameContains(String name) {
        List<Item> items = new ArrayList<>();
        String selectNameSql = "select * from Item where itemName LIKE ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectNameSql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getInt("itemId"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemCode(resultSet.getString("itemCode"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
