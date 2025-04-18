package DBconfig;

import Human.Customer;
import Human.InsuranceAgent;
import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Push {

    public boolean registerCustomer(Customer customer) {
        String query = "INSERT INTO Customers (C_id, firstName, lastName, address, phone, email, NID, BirthCertificate, birthdate, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getPerson().getFirstName());
            stmt.setString(3, customer.getPerson().getLastName());
            stmt.setString(4, customer.getPerson().getAddress());
            stmt.setString(5, customer.getPerson().getPhone());
            stmt.setString(6, customer.getPerson().getEmail());
            stmt.setString(7, customer.getPerson().getNIDnumber());
            stmt.setString(8, customer.getPerson().getBirthCertificateNumber());
            stmt.setDate(9, new java.sql.Date(customer.getPerson().getDateOfBirth().getTime()));
            stmt.setBoolean(10, customer.getPerson().getGender());

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
            stmt.setString(2, agent.getPerson().getFirstName());
            stmt.setString(3, agent.getPerson().getLastName());
            stmt.setString(4, agent.getPerson().getAddress());
            stmt.setString(5, agent.getPerson().getPhone());
            stmt.setString(6, agent.getPerson().getEmail());
            stmt.setString(7, agent.getPerson().getNIDnumber());
            stmt.setString(8, agent.getPerson().getBirthCertificateNumber());
            stmt.setDate(9, new java.sql.Date(agent.getPerson().getDateOfBirth().getTime()));
            stmt.setBoolean(10, agent.getPerson().getGender());

            stmt.executeUpdate();
            System.out.println("Agent registered: " + agent);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void UserXCustomer(Customer customer, User user) {
        String sql= "insert into userXcustomer (username, c_id) values (?, ?)";
        try(Connection conn= DBconfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
           stmt.setString(1, user.getUsername());
           stmt.setInt(2, customer.getCustomerID());
           stmt.executeUpdate();
           Update update = new Update();
           update.UpdateUserStatus(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UserXAgent(InsuranceAgent agent, User user) {
        String sql= "insert into userXagent (username, License) values (?, ?)";
        String sqlUp= "Update users set status = true where username = ?";
        try(Connection conn= DBconfig.getConnection();
        PreparedStatement stmt= conn.prepareStatement(sql);
        PreparedStatement stmtUp = conn.prepareStatement(sqlUp)) {
            stmt.setString(1, user.getUsername());
            stmt.setInt(2, agent.getLicense());
            stmt.executeUpdate();
            stmtUp.setString(1, user.getUsername());
            stmtUp.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
