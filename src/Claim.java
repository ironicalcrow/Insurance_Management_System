public class Claim {
    private Customer customer;
    private Policy policy;
    private static int claimID=1;
    public Claim(Customer customer, Policy policy) {
        this.customer = customer;
        this.policy = policy;
        claimID++;
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

}
