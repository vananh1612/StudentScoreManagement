package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    private static final String DB_NAME = "qld";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    public static Connection connection = getConnection();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Kết nối thành công");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connected");
        }
        return connection;
    }

    public static void main(String[] args) {
        new ConnectDatabase();
    }

}