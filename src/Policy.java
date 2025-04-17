import java.sql.Date;
import java.util.concurrent.TimeUnit;

public abstract class Policy {
    private double premium;
    private Date startDate;
    private Date endDate;
    public Policy(double premium, Date startDate, Date endDate) {
        this.premium = premium;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public double getPremium() {
        return premium;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public static double calculateBaserate(Date startDate, Date endDate, double premium) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int durationInMonths = (int) Math.ceil(diffInDays / 30.0); // Rough monthly estimate

        double baserate = premium/durationInMonths;

        return baserate;
    }
}
