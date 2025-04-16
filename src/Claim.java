public class Claim {
    private Customer customer;
    private Policy policy;
    private static int claimID=1;
    private int claimStatus;
    public Claim(Customer customer, Policy policy) {
        this.customer = customer;
        this.policy = policy;
        claimID++;
        claimStatus=0;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Policy getPolicy() {
        return policy;
    }
    public int getClaimID() {
        return claimID;
    }
    public int getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(int claimStatus) {
        this.claimStatus = claimStatus;
    }
}
