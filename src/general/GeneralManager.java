package general;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralManager<T> {

    Connection connection;

    public GeneralManager(Connection connection) {
        this.connection = connection;
    }

    public void insert(T obj) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Class<?> objClass = obj.getClass();
        String tableName = objClass.getSimpleName();
        Field[] fields = objClass.getDeclaredFields();

        String columnNames = Arrays.stream(fields).peek(field -> field.setAccessible(true)).map(Field::getName).collect(Collectors.joining(","));

        String placeholders = String.join(",", Collections.nCopies(fields.length, "?"));

        String insertQuery = new StringBuilder().append("INSERT INTO ").append(tableName).append(" (").append(columnNames).append(") VALUES (").append(placeholders).append(")").toString();

        System.out.println(insertQuery);

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (int i = 0; i < fields.length; i++) {
                preparedStatement.setObject(i + 1, fields[i].get(obj));
            }
            preparedStatement.execute();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public List<T> select(T obj) throws IllegalAccessException, SQLException, InstantiationException {
        Class objClass = obj.getClass();
        String tableName = objClass.getSimpleName();
        Field[] fields = objClass.getDeclaredFields();

        List<Field> nonNullFields = Arrays.stream(fields).peek(field -> field.setAccessible(true)).filter(field -> {
            try {
                return field.get(obj) != null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());

        String columnNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(","));

        String whereClause = nonNullFields.stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(" AND "));

        String selectQuery = new StringBuilder()
                .append("SELECT ")
                .append(columnNames)
                .append(" FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(whereClause).toString();
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        for (int i = 0; i < nonNullFields.size(); i++) {
            try {
                preparedStatement.setObject(i + 1, nonNullFields.get(i).get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        List<T> results = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            Object object = objClass.newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }
            results.add((T) object);

        }
        return results;
    }
}
