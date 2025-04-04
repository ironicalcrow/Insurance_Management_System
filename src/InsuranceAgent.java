import java.util.Date;

public class InsuranceAgent extends Person {
    private static int License=1;
    public InsuranceAgent(String firstName, String lastName, String address, String phone, String email, String NIDnumber, String BirthCertificateNumber, Date dateOfBirth, boolean gender) {
        super(firstName,lastName,address,phone,email,NIDnumber,BirthCertificateNumber,dateOfBirth,gender);
        License++;
    }
    public int getLicense() {
        return License;
    }
}
