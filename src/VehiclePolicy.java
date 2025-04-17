import java.util.Date;

public class VehiclePolicy extends Policy {
    private static int VehiclePolicyID;
    private String vehicleType;
    private String plateNumber;
    private double VehiclePrice;
    public VehiclePolicy(String vehicleType, String plateNumber, double VehiclePrice, double premium, Date startDate, Date endDate)
    {
        super(premium, startDate, endDate);
        this.vehicleType = vehicleType;
        this.plateNumber = plateNumber;
        this.VehiclePrice = VehiclePrice;
        VehiclePolicyID++;
    }
    public static int getVehiclePolicyID() {
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
