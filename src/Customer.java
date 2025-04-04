import java.util.Date;

public class Customer extends Person {
    private static int customerID = 1;
    public Customer(String firstName,String lastName, String address, String phone, String email, String NIDnumber, String BirthCertificateNumber, Date dateOfBirth, boolean gender) {
        super(firstName,lastName,address,phone,email,NIDnumber,BirthCertificateNumber,dateOfBirth,gender);
        customerID++;
    }
    public int getCustomerID() {
        return customerID;
    }

}
