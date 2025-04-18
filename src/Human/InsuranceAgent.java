package Human;

import DBconfig.DBconfig;

import java.sql.*;

public class InsuranceAgent {
    private Person person;
    private int License;

    public InsuranceAgent(Person person) {
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
    public void setLicense(int license) {
        this.License = license;
    }
    public Person getPerson() {
        return person;
    }
}
