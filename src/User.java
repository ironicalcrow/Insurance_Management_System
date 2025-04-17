import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String username;
    private String email;
    private String role;
    User(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
    User(){}
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public void showUserCustomersAndClaims(String username) {
        try (Connection conn = DBconfig.getConnection()) {

            String customerQuery = "SELECT c.C_id, c.firstName, c.lastName, chp.claimStatus, hp.premium " +
                    "FROM userXcustomer uc " +
                    "JOIN Customers c ON uc.c_id = c.C_id " +
                    "LEFT JOIN Claim_H_P chp ON c.C_id = chp.c_id " +
                    "LEFT JOIN HealthPolicy hp ON chp.h_p_id = hp.H_P_id " +
                    "WHERE uc.username = ? " +
                    "UNION " +
                    "SELECT c.C_id, c.firstName, c.lastName, cvp.claimStatus, vp.vehicle_price " +
                    "FROM userXcustomer uc " +
                    "JOIN Customers c ON uc.c_id = c.C_id " +
                    "LEFT JOIN Claim_V_P cvp ON c.C_id = cvp.c_id " +
                    "LEFT JOIN VehiclePolicy vp ON cvp.v_p_id = vp.V_P_id " +
                    "WHERE uc.username = ?";

            try (PreparedStatement stmt = conn.prepareStatement(customerQuery)) {
                stmt.setString(1, username);
                stmt.setString(2, username);

                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("Customer Details for User: " + username);
                    while (rs.next()) {
                        int cid = rs.getInt("C_id");
                        String firstName = rs.getString("firstName");
                        String lastName = rs.getString("lastName");
                        int claimStatus = rs.getInt("claimStatus");
                        double amount = rs.getDouble("premium"); // premium or vehicle_price

                        System.out.println("Customer ID: " + cid + ", Name: " + firstName + " " + lastName +
                                ", Claim Status: " + (claimStatus == 0 ? "Pending" : "Approved") +
                                ", Amount: " + amount);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showAgentCustomersAndClaims(int license) {
        try (Connection conn = DBconfig.getConnection()) {

            String agentQuery = "SELECT c.C_id, c.firstName, c.lastName, chp.claimStatus, hp.premium " +
                    "FROM agent_hp ah " +
                    "JOIN HealthPolicy hp ON ah.H_P_id = hp.H_P_id " +
                    "JOIN Claim_H_P chp ON hp.H_P_id = chp.h_p_id " +
                    "JOIN Customers c ON chp.c_id = c.C_id " +
                    "WHERE ah.License = ? " +
                    "UNION " +
                    "SELECT c.C_id, c.firstName, c.lastName, cvp.claimStatus, vp.vehicle_price " +
                    "FROM agent_vp av " +
                    "JOIN VehiclePolicy vp ON av.V_P_id = vp.V_P_id " +
                    "JOIN Claim_V_P cvp ON vp.V_P_id = cvp.v_p_id " +
                    "JOIN Customers c ON cvp.c_id = c.C_id " +
                    "WHERE av.License = ?";

            try (PreparedStatement stmt = conn.prepareStatement(agentQuery)) {
                stmt.setInt(1, license);
                stmt.setInt(2, license);

                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("Customer Details for Agent License: " + license);
                    while (rs.next()) {
                        int cid = rs.getInt("C_id");
                        String firstName = rs.getString("firstName");
                        String lastName = rs.getString("lastName");
                        int claimStatus = rs.getInt("claimStatus");
                        double amount = rs.getDouble("premium"); // premium or vehicle_price

                        System.out.println("Customer ID: " + cid + ", Name: " + firstName + " " + lastName +
                                ", Claim Status: " + (claimStatus == 0 ? "Pending" : "Approved") +
                                ", Amount: " + amount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
