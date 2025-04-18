package DBconfig;

import java.sql.*;

public class QueryAgent {

    public void VehicleClaimSold(int license) {
        String sql = """
        SELECT c.C_id, c.firstName, vp.V_P_id, vp.plate_number, vp.vehicle_price, vp.premium, cvp.claimStatus
        FROM Claim_V_P cvp
        JOIN Customers c ON cvp.c_id = c.C_id
        JOIN VehiclePolicy vp ON cvp.v_p_id = vp.V_P_id
        WHERE cvp.License = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, license);
            ResultSet rs = statement.executeQuery();

            System.out.println("CustomerID | First Name | V_P_ID | Plate Number | Vehicle Price | Premium | Claim Status");
            System.out.println("--------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int customerId = rs.getInt("C_id");
                String firstName = rs.getString("firstName");
                int vehiclePolicyId = rs.getInt("V_P_id");
                String plateNumber = rs.getString("plate_number");
                double vehiclePrice = rs.getDouble("vehicle_price");
                double premium = rs.getDouble("premium");
                int claimStatus = rs.getInt("claimStatus");

                System.out.printf("%-11d | %-10s | %-6d | %-12s | %-13.2f | %-7.2f | %-12d%n",
                        customerId, firstName, vehiclePolicyId, plateNumber, vehiclePrice, premium, claimStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void HealthPolicySold(int license) {
        String sql = """
        SELECT c.C_id, c.firstName, c.phone, c.address, hp.H_P_id, hp.premium, chp.claimStatus
        FROM Claim_H_P chp
        JOIN Customers c ON chp.c_id = c.C_id
        JOIN HealthPolicy hp ON chp.h_p_id = hp.H_P_id
        WHERE chp.License = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, license);
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

    public void printVehicleClaimsByStartMonthYearAndLicense(int startMonth, int startYear, int licenseNumber) {
        String sql = """
        SELECT c.C_id, c.firstName, vp.V_P_id, vp.plate_number, vp.vehicle_price, vp.premium, vp.start_date, vp.end_date, cvp.claimStatus
        FROM Claim_V_P cvp
        JOIN Customers c ON cvp.c_id = c.C_id
        JOIN VehiclePolicy vp ON cvp.v_p_id = vp.V_P_id
        JOIN Insurance_Agent ia ON cvp.License = ia.License
        WHERE MONTH(vp.start_date) = ? AND YEAR(vp.start_date) = ? 
        AND ia.License = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the parameters for start month, start year, and license number
            statement.setInt(1, startMonth);  // starting month
            statement.setInt(2, startYear);   // starting year
            statement.setInt(3, licenseNumber);  // provided license number

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

    public void printHealthClaimsByStartMonthYearAndLicense(int startMonth, int startYear, int licenseNumber) {
        String sql = """
        SELECT c.C_id, c.firstName, c.phone, c.address, hp.H_P_id, hp.start_date, hp.end_date, hp.premium, chp.claimStatus
        FROM Claim_H_P chp
        JOIN Customers c ON chp.c_id = c.C_id
        JOIN HealthPolicy hp ON chp.h_p_id = hp.H_P_id
        JOIN Insurance_Agent ia ON chp.License = ia.License
        WHERE MONTH(hp.start_date) = ? AND YEAR(hp.start_date) = ?
        AND ia.License = ?
    """;

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the parameters for start month, start year, and license number
            statement.setInt(1, startMonth);  // starting month
            statement.setInt(2, startYear);   // starting year
            statement.setInt(3, licenseNumber);  // provided license number

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
    public void calculateSalaryForAgent(int license, int month, int year) {
        int policiesSold = 0;

        try (Connection conn = DBconfig.getConnection()) {

            // Count Health Policies sold
            String healthPolicyQuery = "SELECT COUNT(*) FROM agent_hp ah " +
                    "JOIN HealthPolicy hp ON ah.H_P_id = hp.H_P_id " +
                    "WHERE ah.License = ? AND MONTH(hp.start_date) = ? AND YEAR(hp.start_date) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(healthPolicyQuery)) {
                stmt.setInt(1, license);
                stmt.setInt(2, month);
                stmt.setInt(3, year);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        policiesSold += rs.getInt(1);
                    }
                }
            }
            String vehiclePolicyQuery = "SELECT COUNT(*) FROM agent_vp av " +
                    "JOIN VehiclePolicy vp ON av.V_P_id = vp.V_P_id " +
                    "WHERE av.License = ? AND MONTH(vp.start_date) = ? AND YEAR(vp.start_date) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(vehiclePolicyQuery)) {
                stmt.setInt(1, license);
                stmt.setInt(2, month);
                stmt.setInt(3, year);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        policiesSold += rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int baseSalary= 200000;
        int bonusPerPolicy= 1000;
        double totalSalary = baseSalary + (policiesSold * bonusPerPolicy);

        System.out.println("Summary for Agent License: " + license);
        System.out.println("Month: " + month + ", Year: " + year);
        System.out.println("Policies Sold: " + policiesSold);
        System.out.println("Base Salary: " + baseSalary);
        System.out.println("Bonus Earned: " + (policiesSold * bonusPerPolicy));
        System.out.println("Total Salary: " + totalSalary);
    }

}
