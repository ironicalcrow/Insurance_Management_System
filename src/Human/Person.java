package Human;

import java.sql.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String NIDnumber;
    private String BirthCertificateNumber;
    private Date dateOfBirth;
    private boolean gender;
    public Person(String firstName,String lastName, String address, String phone, String email, String NIDnumber, String BirthCertificateNumber, Date dateOfBirth, boolean gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.NIDnumber = NIDnumber;
        this.BirthCertificateNumber = BirthCertificateNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getNIDnumber() {
        return NIDnumber;
    }
    public String getBirthCertificateNumber() {
        return BirthCertificateNumber;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public boolean getGender() {
        return gender;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
