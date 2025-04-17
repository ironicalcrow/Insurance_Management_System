import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {
    public boolean setUserRole(String username, String newRole) {
        String sql = "UPDATE users SET role = ? WHERE username = ?";
        try (Connection conn = DBconfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newRole);
            stmt.setString(2, username);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Role updated for user: " + username);
                return true;
            } else {
                System.out.println("User not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
