import java.sql.*;
import java.sql.Date;

public class UserService {

    public InsuranceAgent loadAgentInfo(String username) {
        String sql = "SELECT firstName, lastName, address, phone, email, NIDnumber, BirthCertificateNumber, dateOfBirth, gender " +
                "FROM Agents WHERE username = ?";

        try (Connection conn = DBconfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String NIDnumber = rs.getString("NIDnumber");
                String BirthCertificateNumber = rs.getString("BirthCertificateNumber");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                boolean gender = rs.getBoolean("gender"); // assuming true=male, false=female

                InsuranceAgent agent = new InsuranceAgent(
                        firstName, lastName, address, phone, email,
                        NIDnumber, BirthCertificateNumber, dateOfBirth, gender
                );
                System.out.println("Agent loaded successfully: " + firstName + " " + lastName);
                return agent;
            } else {
                System.out.println("Agent not found in database for username: " + username);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
