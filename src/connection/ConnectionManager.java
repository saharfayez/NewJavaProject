package connection;

import java.sql.*;

public class ConnectionManager {

    private static String url = "jdbc:mysql://127.0.0.1:3306/StockManagementSchema";
    private static String userName = "abstract-programmer";
    private static String password = "example-password";
    private static Connection con;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
