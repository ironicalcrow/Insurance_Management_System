import java.sql.*;

public class HealthPolicy extends Policy {
    private int HealthPolicyID;
    public HealthPolicy(double premium, Date startDate, Date endDate) {
        super(premium, startDate, endDate);
        String sql = "SELECT MAX(H_P_id) FROM HealthPolicy";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt(1);
                if (rs.wasNull()) {
                    this.HealthPolicyID = 1;
                } else {
                    this.HealthPolicyID = id + 1;
                }
            } else {
                this.HealthPolicyID = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getHealthPolicyNumber() {
        return HealthPolicyID;
    }
}
