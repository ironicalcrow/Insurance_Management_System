import java.sql.*;

public class Customer extends Person {
    private int customerID;
    public Customer(String firstName, String lastName, String address, String phone, String email, String NIDnumber, String BirthCertificateNumber, Date dateOfBirth, boolean gender) {
        super(firstName, lastName, address, phone, email, NIDnumber, BirthCertificateNumber, dateOfBirth, gender);
        String sql = "SELECT MAX(C_id) FROM Customers";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt(1);
                if (rs.wasNull()) {
                    this.customerID = 1;
                } else {
                    this.customerID = id + 1;
                }
            } else {
                this.customerID = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCustomerID() {
        return customerID;
    }


}
