import java.sql.*;
import java.time.LocalDate;

public class InsuranceAgentService {

    private double baseSalary=20000;
    private double bonusPerPolicy=1000;

    public InsuranceAgentService() {
    }

    public void setBaseSalary(double newBaseSalary) {
        this.baseSalary = newBaseSalary;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBonusPerPolicy(double bonusPerPolicy) {
        this.bonusPerPolicy = bonusPerPolicy;
    }

    public double getBonusPerPolicy() {
        return bonusPerPolicy;
    }

    public double calculateSalaryForAgent(int license, int month, int year) {
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

        double totalSalary = baseSalary + (policiesSold * bonusPerPolicy);

        System.out.println("Summary for Agent License: " + license);
        System.out.println("Month: " + month + ", Year: " + year);
        System.out.println("Policies Sold: " + policiesSold);
        System.out.println("Base Salary: " + baseSalary);
        System.out.println("Bonus Earned: " + (policiesSold * bonusPerPolicy));
        System.out.println("Total Salary: " + totalSalary);

        return totalSalary;
    }

}
