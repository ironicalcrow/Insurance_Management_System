import java.sql.*;
import java.util.Scanner;

public class PolicyViewer {

    public void viewAllPolicies() {
        try (Connection conn = DBconfig.getConnection()) {
            viewHealthPolicies(conn);
            viewVehiclePolicies(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewClaimsByMonth(String month) {
        try (Connection conn = DBconfig.getConnection()) {
            viewHealthClaimsByMonth(conn, month);
            viewVehicleClaimsByMonth(conn, month);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewHealthClaimsByMonth(Connection conn, String month) throws SQLException {
        System.out.println("\n---- Health Claims for Month: " + month + " ----");
        String sql = "SELECT claim_id, c_id, h_p_id, claim_date, claim_amount " +
                "FROM Claim_H_P " +
                "WHERE DATE_FORMAT(claim_date, '%Y-%m') = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, month);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int claimId = rs.getInt("claim_id");
                int customerId = rs.getInt("c_id");
                int healthPolicyId = rs.getInt("h_p_id");
                Date claimDate = rs.getDate("claim_date");
                double amount = rs.getDouble("claim_amount");

                System.out.printf("Claim ID: %d | Customer ID: %d | Health Policy ID: %d | Date: %s | Amount: %.2f\n",
                        claimId, customerId, healthPolicyId, claimDate, amount);
            }
        }
    }

    private void viewVehicleClaimsByMonth(Connection conn, String month) throws SQLException {
        System.out.println("\n---- Vehicle Claims for Month: " + month + " ----");
        String sql = "SELECT claim_id, c_id, v_p_id, claim_date, claim_amount " +
                "FROM Claim_V_P " +
                "WHERE DATE_FORMAT(claim_date, '%Y-%m') = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, month);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int claimId = rs.getInt("claim_id");
                int customerId = rs.getInt("c_id");
                int vehiclePolicyId = rs.getInt("v_p_id");
                Date claimDate = rs.getDate("claim_date");
                double amount = rs.getDouble("claim_amount");

                System.out.printf("Claim ID: %d | Customer ID: %d | Vehicle Policy ID: %d | Date: %s | Amount: %.2f\n",
                        claimId, customerId, vehiclePolicyId, claimDate, amount);
            }
        }
    }

    private void viewHealthPolicies(Connection conn) throws SQLException {
        System.out.println("\n---- Health Policies ----");
        String sql = "SELECT H_P_id, start_date, end_date, premium FROM HealthPolicy";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                printHealthPolicyDetails(rs);
            }
        }
    }

    private void viewVehiclePolicies(Connection conn) throws SQLException {
        System.out.println("\n---- Vehicle Policies ----");
        String sql = "SELECT V_P_id, vehicle_type, plate_number, vehicle_price FROM VehiclePolicy";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                printVehiclePolicyDetails(rs);
            }
        }
    }

    private void printHealthPolicyDetails(ResultSet rs) throws SQLException {
        int id = rs.getInt("H_P_id");
        Date start = rs.getDate("start_date");
        Date end = rs.getDate("end_date");
        double premium = rs.getDouble("premium");
        System.out.printf("Health Policy ID: %d | Start: %s | End: %s | Premium: %.2f\n", id, start, end, premium);
    }

    private void printVehiclePolicyDetails(ResultSet rs) throws SQLException {
        int id = rs.getInt("V_P_id");
        String type = rs.getString("vehicle_type");
        String plate = rs.getString("plate_number");
        double price = rs.getDouble("vehicle_price");
        double premium = price * 0.5;
        System.out.printf("Vehicle Policy ID: %d | Type: %s | Plate: %s | Vehicle Price: %.2f | Premium: %.2f\n",
                id, type, plate, price, premium);
    }

    public void viewPoliciesByAgent(int license) {
        try (Connection conn = DBconfig.getConnection()) {
            viewHealthPoliciesByAgent(conn, license);
            viewVehiclePoliciesByAgent(conn, license);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewHealthPoliciesByAgent(Connection conn, int license) throws SQLException {
        System.out.println("\nHealth Policies for Agent License: " + license);
        String sql = "SELECT h.H_P_id, h.start_date, h.end_date, h.premium " +
                "FROM HealthPolicy h JOIN agent_hp ah ON h.H_P_id = ah.H_P_id " +
                "WHERE ah.License = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, license);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                printHealthPolicyDetails(rs);
            }
        }
    }

    private void viewVehiclePoliciesByAgent(Connection conn, int license) throws SQLException {
        System.out.println("\nVehicle Policies for Agent License: " + license);
        String sql = "SELECT v.V_P_id, v.vehicle_type, v.plate_number, v.vehicle_price " +
                "FROM VehiclePolicy v JOIN agent_vp av ON v.V_P_id = av.V_P_id " +
                "WHERE av.License = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, license);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                printVehiclePolicyDetails(rs);
            }
        }
    }

    public void viewPoliciesByAgentAndMonth(int license, int month, int year) {
        try (Connection conn = DBconfig.getConnection()) {
            viewHealthPoliciesByAgentAndMonth(conn, license, month, year);
            viewVehiclePoliciesByAgentAndMonth(conn, license, month, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewHealthPoliciesByAgentAndMonth(Connection conn, int license, int month, int year) throws SQLException {
        String sql = "SELECT h.H_P_id, h.start_date, h.end_date, h.premium " +
                "FROM HealthPolicy h JOIN agent_hp ah ON h.H_P_id = ah.H_P_id " +
                "WHERE ah.License = ? AND MONTH(h.start_date) = ? AND YEAR(h.start_date) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, license);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nAgent's Health Policies for " + month + "/" + year + ":");
            while (rs.next()) {
                printHealthPolicyDetails(rs);
            }
        }
    }

    private void viewVehiclePoliciesByAgentAndMonth(Connection conn, int license, int month, int year) throws SQLException {
        String sql = "SELECT v.V_P_id, v.vehicle_type, v.plate_number, v.vehicle_price " +
                "FROM VehiclePolicy v JOIN agent_vp av ON v.V_P_id = av.V_P_id " +
                "WHERE av.License = ? AND MONTH(v.start_date) = ? AND YEAR(v.start_date) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, license);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nAgent's Vehicle Policies for " + month + "/" + year + ":");
            while (rs.next()) {
                printVehiclePolicyDetails(rs);
            }
        }
    }

    public void viewClaimsByCustomer(int customerId) {
        try (Connection conn = DBconfig.getConnection()) {
            System.out.println("\nClaims for Customer ID: " + customerId);

            String healthClaimsQuery = "SELECT claim_id, claim_date, claim_amount FROM Claim_H_P WHERE c_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(healthClaimsQuery)) {
                stmt.setInt(1, customerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int claimId = rs.getInt("claim_id");
                    Date claimDate = rs.getDate("claim_date");
                    double amountClaimed = rs.getDouble("claim_amount");
                    System.out.printf("Health Policy Claim ID: %d | Date: %s | Amount Claimed: %.2f\n", claimId, claimDate, amountClaimed);
                }
            }

            String vehicleClaimsQuery = "SELECT claim_id, claim_date, claim_amount FROM Claim_V_P WHERE c_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(vehicleClaimsQuery)) {
                stmt.setInt(1, customerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int claimId = rs.getInt("claim_id");
                    Date claimDate = rs.getDate("claim_date");
                    double amountClaimed = rs.getDouble("claim_amount");
                    System.out.printf("Vehicle Policy Claim ID: %d | Date: %s | Amount Claimed: %.2f\n", claimId, claimDate, amountClaimed);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewInsuranceByFilters() {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DBconfig.getConnection()) {
            System.out.println("\n--- Insurance Filter Options ---");
            System.out.println("1. View Policies by Month");
            System.out.println("2. View Policies by Customer");
            System.out.println("3. View Policies by Agent");
            System.out.print("Enter your choice (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Month (YYYY-MM): ");
                    String month = scanner.nextLine().trim();
                    viewPoliciesByMonthInternal(conn, month);
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    viewPoliciesByCustomerInternal(conn, customerId);
                    break;
                case 3:
                    System.out.print("Enter Agent License: ");
                    int license = scanner.nextInt();
                    viewPoliciesByAgentInternal(conn, license);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewPoliciesByMonthInternal(Connection conn, String month) throws SQLException {
        viewHealthPolicies(conn);
        viewVehiclePolicies(conn);
    }

    private void viewPoliciesByCustomerInternal(Connection conn, int customerId) throws SQLException {
        viewClaimsByCustomer(customerId);
    }

    private void viewPoliciesByAgentInternal(Connection conn, int license) throws SQLException {
        viewPoliciesByAgent(license);
    }
}
