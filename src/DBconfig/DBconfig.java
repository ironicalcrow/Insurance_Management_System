package DBconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconfig {
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/insurance_mangement_system";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}
