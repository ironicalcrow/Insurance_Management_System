package users;

import DBconfig.PullHuman;

import java.sql.SQLException;
import java.util.Scanner;

public class Admin{

    public void setUserRole() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username to update role: ");
        String username = scanner.nextLine();
        System.out.print("Enter new role (admin/agent/customer): ");
        String newRole = scanner.nextLine();
        if(newRole.equals("agent")) {
            Register register = new Register();
            PullHuman pullHuman = new PullHuman();
            User user = pullHuman.getUserFromDB(username);
            register.InsuranceAgent(user);
        }
    }

}
