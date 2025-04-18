package DBconfig;

import Human.Customer;
import Policies.HealthPolicy;
import Policies.VehiclePolicy;
import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

    public void insertHealthClaim(User user, HealthPolicy policy, int license) {
        String sql = "INSERT INTO Claim_H_P (c_id, h_p_id, License, claimStatus) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            PullHuman p = new PullHuman();
            Customer c = p.getCustomerFromDB(user);

            ps.setInt(1, c.getCustomerID());
            ps.setInt(2, policy.getHealthPolicyNumber());
            ps.setInt(3, license);
            ps.setInt(4, 1);  // Correctly setting the claimStatus
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertVehicleClaim(User user, VehiclePolicy policy, int license) {
        String sql = "INSERT INTO Claim_V_P (c_id, v_p_id, License, claimStatus) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            PullHuman p = new PullHuman();
            Customer c = p.getCustomerFromDB(user);

            ps.setInt(1, c.getCustomerID());
            ps.setInt(2, policy.getVehiclePolicyID());
            ps.setInt(3, license);
            ps.setInt(4, 1);  // Correctly setting the claimStatus
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertHealthPolicy(HealthPolicy policy) throws SQLException {
        String sql = "INSERT INTO HealthPolicy (H_P_id, start_date, end_date, premium) VALUES (?, ?, ?, ?)";

        int policyID = policy.getHealthPolicyNumber();
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, policyID);
            stmt.setDate(2, new java.sql.Date(policy.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(policy.getEndDate().getTime()));
            stmt.setDouble(4, policy.getPremium());
            stmt.executeUpdate();
        }
    }

    public void insertVehiclePolicy(VehiclePolicy policy) throws SQLException {
        String sql = "INSERT INTO VehiclePolicy (V_P_id, vehicle_type, plate_number, vehicle_price) VALUES (?, ?, ?, ?)";

        int policyID = policy.getVehiclePolicyID(); // Removed -1; assume getVehiclePolicyID() gives correct value
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, policyID);
            stmt.setString(2, policy.getVehicleType());
            stmt.setString(3, policy.getPlateNumber());
            stmt.setDouble(4, policy.getVehiclePrice());
            stmt.executeUpdate();
        }
    }
}
