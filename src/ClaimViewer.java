//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClaimViewer {
    public ClaimViewer() {
    }

    public void viewAllClaims() {
        try (Connection conn = DBconfig.getConnection()) {
            this.viewHealthPolicyClaims(conn);
            this.viewVehiclePolicyClaims(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void viewHealthPolicyClaims(Connection conn) throws SQLException {
        System.out.println("\n---- Health Policy Claims ----");
        String sql = "SELECT c.firstName, c.lastName, c.email, h.premium, ch.claimStatus FROM Claim_H_P ch JOIN Customers c ON ch.c_id = c.C_id JOIN HealthPolicy h ON ch.h_p_id = h.H_P_id";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while(rs.next()) {
                this.printClaimDetails(rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getDouble("premium"), rs.getInt("claimStatus"));
            }
        }

    }

    private void viewVehiclePolicyClaims(Connection conn) throws SQLException {
        System.out.println("\n---- Vehicle Policy Claims ----");
        String sql = "SELECT c.firstName, c.lastName, c.email, v.vehicle_price, cv.claimStatus FROM Claim_V_P cv JOIN Customers c ON cv.c_id = c.C_id JOIN VehiclePolicy v ON cv.v_p_id = v.V_P_id";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while(rs.next()) {
                double vehiclePrice = rs.getDouble("vehicle_price");
                double premium = vehiclePrice * (double)0.5F;
                this.printClaimDetails(rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), premium, rs.getInt("claimStatus"));
            }
        }

    }

    private void printClaimDetails(String firstName, String lastName, String email, double premium, int status) {
        System.out.printf("Customer: %s %s | Email: %s | Premium: %.2f | Claim Status: %d\n", firstName, lastName, email, premium, status);
    }
}
