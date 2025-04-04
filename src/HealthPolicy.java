import java.util.Date;

public class HealthPolicy extends Policy {
    private static int HealthPolicyID=1;
    public HealthPolicy(double premium, Date startDate, Date endDate) {
        super(premium,startDate,endDate);
        HealthPolicyID++;
    }

    public static int getHealthPolicyNumber() {
        return HealthPolicyID;
    }
}
