import java.sql.*;
import java.util.Date;

public class ClaimRegistrationSystem {

    public boolean registerClaim(Claim claim) {
        Policy policy = claim.getPolicy();
        int policyID = -1;

        try (Connection conn = DBconfig.getConnection()) {
            if (policy instanceof HealthPolicy) {
                policyID = insertHealthPolicy((HealthPolicy) policy, conn);
            } else if (policy instanceof VehiclePolicy) {
                policyID = insertVehiclePolicy((VehiclePolicy) policy, conn);
            } else {
                System.out.println("Unknown policy type.");
                return false;
            }

            // 2. Insert Claim into correct table
            String claimSql = (policy instanceof HealthPolicy)
                    ? "INSERT INTO Claim_H_P (c_id, h_p_id, claimStatus) VALUES (?, ?, ?)"
                    : "INSERT INTO Claim_V_P (c_id, v_p_id, claimStatus) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(claimSql)) {
                stmt.setInt(1, claim.getCustomer().getCustomerID());
                stmt.setInt(2, policyID);
                stmt.setInt(3, claim.getClaimStatus());

                stmt.executeUpdate();
                System.out.println("Claim registered successfully.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int insertHealthPolicy(HealthPolicy policy, Connection conn) throws SQLException {
        String sql = "INSERT INTO HealthPolicy (H_P_id, start_date, end_date, premium) VALUES (?, ?, ?, ?)";

        int policyID = policy.getHealthPolicyNumber();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, policyID);
            stmt.setDate(2, new java.sql.Date(policy.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(policy.getEndDate().getTime()));
            stmt.setDouble(4, policy.getPremium());
            stmt.executeUpdate();
        }
        System.out.println("HealthPolicy registered with ID: " + policyID);
        return policyID;
    }

    private int insertVehiclePolicy(VehiclePolicy policy, Connection conn) throws SQLException {
        String sql = "INSERT INTO VehiclePolicy (V_P_id, vehicle_type, plate_number, vehicle_price) VALUES (?, ?, ?, ?)";

        int policyID = policy.getVehiclePolicyID() - 1;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, policyID);
            stmt.setString(2, policy.getVehicleType());
            stmt.setString(3, policy.getPlateNumber());
            stmt.setDouble(4, policy.getVehiclePrice());
            stmt.executeUpdate();
        }
        System.out.println("VehiclePolicy registered with ID: " + policyID);
        return policyID;
    }
}
