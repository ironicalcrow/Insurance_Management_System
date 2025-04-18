package DBconfig;

import Policies.HealthPolicy;
import Policies.VehiclePolicy;

import java.sql.*;

public class PullPolicy {
    public HealthPolicy getHealthPolicyFromDB(int health) {
        String sql = "select * from HealtPolicy where H_P_id = ?";
        try(Connection conn= DBconfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1,health);
            ResultSet rs = pstmt.executeQuery();
            Date startDate = rs.getDate("start_date");
            Date endDate = rs.getDate("end_date");
            double premium = rs.getDouble("premium");
            HealthPolicy healthPolicy= new HealthPolicy(premium,startDate,endDate);
            healthPolicy.setHealthPolicyNumber(health);
            return healthPolicy;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public VehiclePolicy getVehiclePolicyFromDB(int health) {
        String sql = "select * from VehiclePolicy where V_P_id = ?";
        try(Connection conn= DBconfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1,health);
            ResultSet rs = pstmt.executeQuery();
            Date startDate = rs.getDate("start_date");
            Date endDate = rs.getDate("end_date");
            double premium = rs.getDouble("premium");
            String vehicleType = rs.getString("vehicle_type");
            String Plate_number = rs.getString("plate_number");
            double vehiclePrice = rs.getDouble("vehicle_price");
            VehiclePolicy vehiclePolicy= new VehiclePolicy(vehicleType,Plate_number,vehiclePrice,premium,startDate,endDate);
            vehiclePolicy.setVehiclePolicyID(health);
            return vehiclePolicy;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
