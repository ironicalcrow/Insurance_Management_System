package DBconfig;

import Human.Customer;
import Human.InsuranceAgent;
import Human.Person;
import users.User;

import java.sql.*;

public class PullHuman {

    public Customer getCustomerFromDB(User user) {
        String sql = "SELECT c_id FROM userXcustomer WHERE username = ?";
        String customerData = "SELECT * FROM Customers WHERE C_id = ?";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement customerStmt = conn.prepareStatement(customerData)) {

            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();

            int c_id;
            if (rs.next()) {
                c_id = rs.getInt("c_id");
            } else {
                throw new SQLException("No customer ID found for username: " + user.getUsername());
            }

            customerStmt.setInt(1, c_id);
            ResultSet rs2 = customerStmt.executeQuery();

            if (rs2.next()) {
                int c_id2 = rs2.getInt("C_id");
                String firstName = rs2.getString("firstname");
                String lastName = rs2.getString("lastname");
                String address = rs2.getString("address");
                String phone = rs2.getString("phone");
                String email = rs2.getString("email");
                String NIDnumber = rs2.getString("NID");
                String birthCertificateNumber = rs2.getString("BirthCertificate");
                Date dateOfBirth = rs2.getDate("birthdate");
                boolean gender = rs2.getBoolean("gender");

                Person person = new Person(firstName, lastName, address, phone, email, NIDnumber, birthCertificateNumber, dateOfBirth, gender);
                Customer customer = new Customer(person);
                customer.setCustomerID(c_id2);
                return customer;
            } else {
                throw new SQLException("No customer found with ID: " + c_id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public InsuranceAgent getInsuranceAgentFromDB(User user) {
        String sql = "SELECT License FROM userXagent WHERE username = ?";
        String agentData = "SELECT * FROM Insurance_Agent WHERE License = ?";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement agentStmt = conn.prepareStatement(agentData)) {

            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();

            int license;
            if (rs.next()) {
                license = rs.getInt("License");
            } else {
                throw new SQLException("No license found for username: " + user.getUsername());
            }

            agentStmt.setInt(1, license);
            ResultSet rs2 = agentStmt.executeQuery();

            if (rs2.next()) {
                int license2 = rs2.getInt("License");
                String firstName = rs2.getString("firstname");
                String lastName = rs2.getString("lastname");
                String address = rs2.getString("address");
                String phone = rs2.getString("phone");
                String email = rs2.getString("email");
                String NIDnumber = rs2.getString("NID");
                String birthCertificateNumber = rs2.getString("BirthCertificate");
                Date dateOfBirth = rs2.getDate("birthdate");
                boolean gender = rs2.getBoolean("gender");

                Person person = new Person(firstName, lastName, address, phone, email, NIDnumber, birthCertificateNumber, dateOfBirth, gender);
                InsuranceAgent agent = new InsuranceAgent(person);
                agent.setLicense(license2);
                return agent;
            } else {
                throw new SQLException("No insurance agent found with license: " + license);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserFromDB(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, username);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String role = rs.getString("role");
                return new User(username, email, role);
            } else {
                throw new SQLException("User not found with username: " + username);
            }

        }
    }
}
