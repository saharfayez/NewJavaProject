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
        StringBuilder insertQueryBuilder = new StringBuilder();
        insertQueryBuilder.append("INSERT INTO " + tableName + "(");
        for (int i = 0; i < columnNames.length; i++) {
            insertQueryBuilder.append(columnNames[i].getName() + ",");
        }
        insertQueryBuilder.deleteCharAt(insertQueryBuilder.length() - 1);
        insertQueryBuilder.append(") VALUES (");
        for (int i = 0; i < columnNames.length; i++) {
            insertQueryBuilder.append("?,");
        }
        insertQueryBuilder.deleteCharAt(insertQueryBuilder.length() - 1);
        insertQueryBuilder.append(")");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQueryBuilder.toString());
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
