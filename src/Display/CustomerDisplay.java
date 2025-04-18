package Display;

import DBconfig.PullHuman;
import DBconfig.QueryCustomers;
import Human.Customer;
import Policies.RegisterPolicies;
import users.User;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerDisplay {
    public void displayCustomer(User user) throws SQLException {
        PullHuman human = new PullHuman();
        Customer customer = human.getCustomerFromDB(user);
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\u001B[36m=========================================\u001B[0m");
        System.out.println("\u001B[33m          === CUSTOMER DASHBOARD ===     \u001B[0m");
        System.out.println("\u001B[36m=========================================\u001B[0m");

        System.out.println("\n\u001B[1;32m 1. Show Claims\u001B[0m");
        System.out.println("\u001B[1;34m 2. Register Claims\u001B[0m");
        System.out.println("\u001B[1;31m 3. Exit\u001B[0m");

        System.out.print("\n\u001B[1;33mChoose an option: \u001B[0m");

        int choice= sc.nextInt();
        while (true) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.print("\u001B[1;31mInvalid choice. Try again: \u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.print("\u001B[1;31mPlease enter a valid number: \u001B[0m");
            }
        }

        switch (choice) {
            case 1:
                System.out.println("\u001B[1;32m\n[✔] Displaying your Claims...\u001B[0m");
                System.out.println("\n\u001B[1;32m 1. Show Health Claims\u001B[0m");
                System.out.println("\u001B[1;34m 2. Show Vehicle Claims\u001B[0m");
                choice= sc.nextInt();
                sc.next();
                showPolicies(choice, customer.getCustomerID());
                displayCustomer(user);
                break;
            case 2:
                System.out.println("\u001B[1;34m\n[✍] Redirecting to Claims registration...\u001B[0m");
                System.out.println("\n\u001B[1;32m 1. Register Health Claims\u001B[0m");
                System.out.println("\u001B[1;34m 2. Register Vehicle Claims\u001B[0m");
                choice= sc.nextInt();
                chooseClaim(choice, user);
                displayCustomer(user);
                break;
            case 3:
                System.out.println("\u001B[1;31m\n[👋] Exiting Customer Dashboard...\u001B[0m");
                return;
        }
    }

    public void showPolicies(int choice, int c_id) throws SQLException {
        QueryCustomers q = new QueryCustomers();
        switch (choice) {
            case 1:
                q.CustomerHealthPolicies(c_id);
                break;
            case 2:
                q.printVehicleClaimsByCustomerId(c_id);
        }
    }
    public void chooseClaim(int choice, User user ) throws SQLException {
        RegisterPolicies rp = new RegisterPolicies();
        switch (choice) {
            case 1:
                rp.registerHealthClaim(user);
                break;
            case 2:
                rp.registerVehicleClaim(user);
                break;
        }
    }

}
