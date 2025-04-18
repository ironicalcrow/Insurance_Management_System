package DBconfig;

import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete {
    public boolean deleteCustomerByUsername(User user) {
        String username = user.getUsername();
        String findSql = "SELECT c_id FROM userXcustomer WHERE username = ?";
        String deleteUserXCustomerSql = "DELETE FROM userXcustomer WHERE username = ?";
        String deleteCustomerSql = "DELETE FROM Customers WHERE C_id = ?";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(findSql);
             PreparedStatement deleteUserXCustomerStmt = conn.prepareStatement(deleteUserXCustomerSql);
             PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSql);
        ) {
            findStmt.setString(1, username);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("c_id");

                // 1. Delete from userXcustomer (child table)
                deleteUserXCustomerStmt.setString(1, username);
                deleteUserXCustomerStmt.executeUpdate();

                // 2. Now delete from Customers (parent table)
                deleteCustomerStmt.setInt(1, customerId);
                deleteCustomerStmt.executeUpdate();

                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAgentByUsername(User user) {
        String username = user.getUsername();
        String findSql = "SELECT License FROM userXagent WHERE username = ?";
        String deleteAgentSql = "DELETE FROM Insurance_Agent WHERE License = ?";
        String deleteUserXAgentSql = "DELETE FROM userXagent WHERE username = ?";
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(findSql);
             PreparedStatement deleteAgentStmt = conn.prepareStatement(deleteAgentSql);
             PreparedStatement deleteUserXAgentStmt = conn.prepareStatement(deleteUserXAgentSql);
        ) {
            findStmt.setString(1, username);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int license = rs.getInt("License");
                deleteAgentStmt.setInt(1, license);
                deleteAgentStmt.executeUpdate();
                deleteUserXAgentStmt.setString(1, username);
                deleteUserXAgentStmt.executeUpdate();

                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
