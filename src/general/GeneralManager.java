package general;

import item.Item;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeneralManager<T> {

    Connection connection;

    public GeneralManager(Connection connection) {
        this.connection = connection;
    }

    public void insert(T obj) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Class objClass = obj.getClass();
        String tableName = objClass.getSimpleName();
        Field[] columnNames = objClass.getDeclaredFields();
        columnNames[0].setAccessible(true);
        Object codeValue = columnNames[0].get(obj);
        columnNames[1].setAccessible(true);
        Object nameValue = columnNames[1].get(obj);
        String firstColumnName = columnNames[0].getName();
        String secondColumnName = columnNames[1].getName();
        String insertSql = "insert into " + tableName + " (" + firstColumnName + ", " + secondColumnName + ")" + " values (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, codeValue.toString());
            preparedStatement.setString(2, nameValue.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
