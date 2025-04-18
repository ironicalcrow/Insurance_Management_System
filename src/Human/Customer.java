package Human;

import DBconfig.DBconfig;
import java.sql.*;

public class Customer{
    private Person person;
    private int customerID;
    public Customer(Person person) {
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
