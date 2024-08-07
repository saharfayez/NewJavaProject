package general;
import item.Item;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

//     public void select(T obj) throws IllegalAccessException {
//
//         Class objClass = obj.getClass();
//         String tableName = objClass.getSimpleName();
//         Field[] columnNames = objClass.getDeclaredFields();
//         for (Field field : columnNames) {
//             field.setAccessible(true);
//             if (field.get(obj)!=null) {
//                 String fieldName = field.getName();
//                 String selectCodeSql = "select * from" + tableName + "where" + fieldName+ "= ?";
//                 try {
//                     PreparedStatement preparedStatement = connection.prepareStatement(selectCodeSql);
//                     preparedStatement.setString(1, fieldName);
//                     ResultSet resultSet = preparedStatement.executeQuery();
//                     while (resultSet.next()) {
//                     }
//                 } catch (SQLException e) {
//                     throw new RuntimeException(e);
//                 }
//             }
//         }
//     }
}
