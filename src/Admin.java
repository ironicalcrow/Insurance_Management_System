import java.sql.*;
import java.util.Scanner;

public class Admin {

    public void viewAllClaims() {
        try (Connection conn = DBconfig.getConnection()) {
            System.out.println("---- Health Policy Claims ----");
            String healthSql = "SELECT c.firstName, c.lastName, c.email, h.premium, ch.claimStatus " +
                    "FROM Claim_H_P ch " +
                    "JOIN Customers c ON ch.c_id = c.C_id " +
                    "JOIN HealthPolicy h ON ch.h_p_id = h.H_P_id";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(healthSql)) {
                while (rs.next()) {
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    double premium = rs.getDouble("premium");
                    int status = rs.getInt("claimStatus");

                    System.out.printf("Customer: %s %s | Email: %s | Premium: %.2f | Claim Status: %d\n",
                            firstName, lastName, email, premium, status);
                }
            }

            System.out.println("---- Vehicle Policy Claims ----");
            String vehicleSql = "SELECT c.firstName, c.lastName, c.email, v.vehicle_price, cv.claimStatus " +
                    "FROM Claim_V_P cv " +
                    "JOIN Customers c ON cv.c_id = c.C_id " +
                    "JOIN VehiclePolicy v ON cv.v_p_id = v.V_P_id";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(vehicleSql)) {
                while (rs.next()) {
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    double vehiclePrice = rs.getDouble("vehicle_price");
                    int status = rs.getInt("claimStatus");

                    double premium = vehiclePrice * 0.5;

                    System.out.printf("Customer: %s %s | Email: %s | Vehicle Price: %.2f | Premium: %.2f | Claim Status: %d\n",
                            firstName, lastName, email, vehiclePrice, premium, status);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
