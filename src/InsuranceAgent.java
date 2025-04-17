import java.sql.*;
import java.util.Scanner;

public class InsuranceAgent extends Person {
    private int License;

    public InsuranceAgent(String firstName, String lastName, String address, String phone, String email, String NIDnumber, String BirthCertificateNumber, Date dateOfBirth, boolean gender) {
        super(firstName, lastName, address, phone, email, NIDnumber, BirthCertificateNumber, dateOfBirth, gender);
        String sql = "SELECT MAX(License) FROM Insurance_Agent";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt(1);
                if (rs.wasNull()) {
                    this.License = 1;
                } else {
                    this.License = id + 1;
                }
            } else {
                this.License = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public int getLicense() {
        return License;
    }
    public static void createInsuranceAgent() {
        System.out.println("Enter Insurance Agent details:");
        Scanner scanner = new Scanner(System.in);
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("NID: ");
        String NID = scanner.nextLine();
        System.out.print("Birth Certificate: ");
        String birthCertificate = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String birthDateString = scanner.nextLine();
        Date birthDate = Date.valueOf(birthDateString); // Use java.sql.Date
        System.out.print("Gender (true for Male, false for Female): ");
        boolean gender = scanner.nextBoolean();
        scanner.nextLine();  // Consume the newline character
        InsuranceAgent agent = new InsuranceAgent(firstName, lastName, address, phone, email, NID, birthCertificate, birthDate, gender);
        RegistrationSystem rs= new RegistrationSystem();
        rs.registerAgent(agent);
    }
}
