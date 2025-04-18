package DBconfig;

import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public void UpdateUserStatus(User user) {
        String sqlUp= "Update users set status = true where username = ?";
        try(Connection conn= DBconfig.getConnection();
            PreparedStatement stmtUp = conn.prepareStatement(sqlUp)
        ) {
            stmtUp.setString(1, user.getUsername());
            stmtUp.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateHealthClaimStatus(int health) {
        String sqlUp= "Update Claim_H_P set claimstatus = 2 where h_p_id = ?";

        try(Connection conn= DBconfig.getConnection();
            PreparedStatement stmtUp = conn.prepareStatement(sqlUp)
        ) {
            stmtUp.setInt(1,health);
            stmtUp.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void UpdateVehicleClaimStatus(int health) {
        String sqlUp= "Update Claim_V_P set claimstatus = 2 where h_p_id = ?";

        try(Connection conn= DBconfig.getConnection();
            PreparedStatement stmtUp = conn.prepareStatement(sqlUp)
        ) {
            stmtUp.setInt(1,health);
            stmtUp.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
