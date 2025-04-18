package DBconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryCustomers {
    public void CustomerHealthPolicies(int customerId) throws SQLException
    {

        String sql = """
        SELECT c.C_id, c.firstName, c.phone, c.address, hp.H_P_id, hp.premium, chp.claimStatus
        FROM Claim_H_P chp
        JOIN Customers c ON chp.c_id = c.C_id
        JOIN HealthPolicy hp ON chp.h_p_id = hp.H_P_id
        WHERE chp.c_id = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1,customerId);
            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | Phone       | Address                     | H_P_ID | Premium | Claim Status");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int cid = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int healthPolicyId = rs.getInt("H_P_id");
                double premium = rs.getDouble("premium");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-11s | %-27s | %-6d | %-7.2f | %-12d%n",
                        cid, firstName, phone, address, healthPolicyId, premium, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printVehicleClaimsByCustomerId(int customerId) {
        String sql = """
        SELECT c.C_id, c.firstName, vp.V_P_id, vp.plate_number, vp.vehicle_price, vp.premium, cvp.claimStatus
        FROM Claim_V_P cvp
        JOIN Customers c ON cvp.c_id = c.C_id
        JOIN VehiclePolicy vp ON cvp.v_p_id = vp.V_P_id
        WHERE cvp.c_id = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | V_P_ID | Plate Number | Vehicle Price | Premium | Claim Status");
            System.out.println("---------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int cid = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                int vpid = rs.getInt("V_P_id");
                String plate = rs.getString("plate_number");
                double price = rs.getDouble("vehicle_price");
                double premium = rs.getDouble("premium");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-6d | %-12s | %-13.2f | %-7.2f | %-12d%n",
                        cid, firstName, vpid, plate, price, premium, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
