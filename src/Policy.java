import java.util.Date;

public abstract class Policy {
    private double premium;
    private Date startDate;
    private Date endDate;
    public Policy(double premium, Date startDate, Date endDate) {
        this.premium = premium;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
