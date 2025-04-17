import java.sql.*;
import java.util.Date;

public class RegistrationSystem {

    public boolean registerCustomer(Customer customer) {
        String query = "INSERT INTO Customers (C_id, firstName, lastName, address, phone, email, NID, BirthCertificate, birthdate, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getNIDnumber());
            stmt.setString(8, customer.getBirthCertificateNumber());
            stmt.setDate(9, new java.sql.Date(customer.getDateOfBirth().getTime()));
            stmt.setBoolean(10, customer.gender);

            stmt.executeUpdate();
            System.out.println("Customer registered: " + customer);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerAgent(InsuranceAgent agent) {
        String query = "INSERT INTO Insurance_Agent (License, firstName, lastName, address, phone, email, NID, BirthCertificate, birthdate, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, agent.getLicense());
            stmt.setString(2, agent.getFirstName());
            stmt.setString(3, agent.getLastName());
            stmt.setString(4, agent.getAddress());
            stmt.setString(5, agent.getPhone());
            stmt.setString(6, agent.getEmail());
            stmt.setString(7, agent.getNIDnumber());
            stmt.setString(8, agent.getBirthCertificateNumber());
            stmt.setDate(9, new java.sql.Date(agent.getDateOfBirth().getTime()));
            stmt.setBoolean(10, agent.gender);

            stmt.executeUpdate();
            System.out.println("Agent registered: " + agent);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
