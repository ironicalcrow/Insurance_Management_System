import java.sql.*;

public class VehiclePolicy extends Policy {
    private int VehiclePolicyID;
    private String vehicleType;
    private String plateNumber;
    private double VehiclePrice;
    public VehiclePolicy(String vehicleType, String plateNumber, double vehiclePrice, double premium, Date startDate, Date endDate) {
        super(premium, startDate, endDate);
        this.vehicleType = vehicleType;
        this.plateNumber = plateNumber;
        this.VehiclePrice = vehiclePrice;

        String sql = "SELECT MAX(V_P_id) FROM VehiclePolicy";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt(1);
                if (rs.wasNull()) {
                    this.VehiclePolicyID = 1;
                } else {
                    this.VehiclePolicyID = id + 1;
                }
            } else {
                this.VehiclePolicyID = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getVehiclePolicyID() {
        return VehiclePolicyID;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public double getVehiclePrice() {
        return VehiclePrice;
    }
}
