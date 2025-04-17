import java.sql.*;

public class Authentication{

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/insurance_mangement_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    // Login Method
    public String login(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean register(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password,role) VALUES (?, ?, ?,'customer')";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Test Connection Method
    public boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return false;
        }
    }
}
