package DBconfig;

import java.sql.*;

public class QueryAdmin {
    public void AllVehiclePolicyClaims() {
        String sql = """
        SELECT c.C_id, c.firstName, vp.V_P_id, vp.plate_number, vp.vehicle_price, vp.premium, cvp.claimStatus
        FROM Claim_V_P cvp
        JOIN Customers c ON cvp.c_id = c.C_id
        JOIN VehiclePolicy vp ON cvp.v_p_id = vp.V_P_id
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | V_P_ID | Plate Number | Vehicle Price | Premium | Claim Status");
            System.out.println("---------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int customerId = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                int vpid = rs.getInt("V_P_id");
                String plate = rs.getString("plate_number");
                double vehiclePrice = rs.getDouble("vehicle_price");
                double premium = rs.getDouble("premium");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-6d | %-12s | %-13.2f | %-7.2f | %-12d%n",
                        customerId, firstName, vpid, plate, vehiclePrice, premium, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printAllHealthPolicyClaims() {
        String sql = """
        SELECT c.C_id, c.firstName, c.phone, c.address, hp.H_P_id, hp.premium, chp.claimStatus
        FROM Claim_H_P chp
        JOIN Customers c ON chp.c_id = c.C_id
        JOIN HealthPolicy hp ON chp.h_p_id = hp.H_P_id
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | Phone       | Address                     | H_P_ID | Premium | Claim Status");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int customerId = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int healthPolicyId = rs.getInt("H_P_id");
                double premium = rs.getDouble("premium");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-11s | %-27s | %-6d | %-7.2f | %-12d%n",
                        customerId, firstName, phone, address, healthPolicyId, premium, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printHealthClaimsByStartMonthAndYear(int startMonth, int startYear) {
        String sql = """
        SELECT c.C_id, c.firstName, c.phone, c.address, hp.H_P_id, hp.start_date, hp.end_date, hp.premium, chp.claimStatus
        FROM Claim_H_P chp
        JOIN Customers c ON chp.c_id = c.C_id
        JOIN HealthPolicy hp ON chp.h_p_id = hp.H_P_id
        WHERE MONTH(hp.start_date) = ? AND YEAR(hp.start_date) = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the parameters for start month and year
            statement.setInt(1, startMonth);  // starting month
            statement.setInt(2, startYear);   // starting year

            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | Phone       | Address                     | H_P_ID | Start Date | End Date | Premium | Claim Status");
            System.out.println("-------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int customerId = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int healthPolicyId = rs.getInt("H_P_id");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                double premium = rs.getDouble("premium");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-11s | %-27s | %-6d | %-11s | %-9s | %-7.2f | %-12d%n",
                        customerId, firstName, phone, address, healthPolicyId, startDate, endDate, premium, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printVehicleClaimsByStartMonthAndYear(int startMonth, int startYear) {
        String sql = """
        SELECT c.C_id, c.firstName, vp.V_P_id, vp.plate_number, vp.vehicle_price, vp.premium, vp.start_date, vp.end_date, cvp.claimStatus
        FROM Claim_V_P cvp
        JOIN Customers c ON cvp.c_id = c.C_id
        JOIN VehiclePolicy vp ON cvp.v_p_id = vp.V_P_id
        WHERE MONTH(vp.start_date) = ? AND YEAR(vp.start_date) = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the parameters for start month and year
            statement.setInt(1, startMonth);  // starting month
            statement.setInt(2, startYear);   // starting year

            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | V_P_ID | Plate Number | Vehicle Price | Premium | Start Date | End Date | Claim Status");
            System.out.println("------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int customerId = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                int vehiclePolicyId = rs.getInt("V_P_id");
                String plateNumber = rs.getString("plate_number");
                double vehiclePrice = rs.getDouble("vehicle_price");
                double premium = rs.getDouble("premium");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-6d | %-12s | %-13.2f | %-7.2f | %-11s | %-9s | %-12d%n",
                        customerId, firstName, vehiclePolicyId, plateNumber, vehiclePrice, premium, startDate, endDate, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
