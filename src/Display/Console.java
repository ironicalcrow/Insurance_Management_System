package Display;

import users.Authentication;
import users.User;

import java.sql.SQLException;
import java.util.Scanner;

public class Console {

    private final Scanner in = new Scanner(System.in);
    private final Authentication auth = new Authentication();

    public void display() throws SQLException {
        printBanner();

        while (true) {
            printMainMenu();

            int choice = getUserChoice(1, 3);

            switch (choice) {
                case 1 -> {
                    User user = auth.log();
                    System.out.println("\n✅ Login successful! Welcome, " + user.getUsername().toUpperCase());
                    chooseDisplayType(user);
                }
                case 2 -> {
                    auth.Reg();
                    System.out.println("\n🎉 Registration complete. You can now login.");
                }
                case 3 -> {
                    System.out.println("\n👋 Thank you for using the Insurance Management System. Goodbye!");
                    return;
                }
            }
        }
    }

    private void chooseDisplayType(User user) throws SQLException {
        String role = user.getRole().toLowerCase();

        System.out.println("\n🔐 Role Detected: " + role.toUpperCase());

        switch (role) {
            case "admin" -> {
                AdminDisplay admin = new AdminDisplay();
                admin.showAdminMenu();
            }
            case "customer" -> {
                CustomerDisplay customer = new CustomerDisplay();
                customer.displayCustomer(user);
            }
            case "agent" -> {
                AgentDisplay agent = new AgentDisplay();
                agent.display(user);
            }
            default -> System.out.println("⚠️ Unknown role. Access denied.");
        }
    }

    private void printBanner() {
        System.out.println("====================================================");
        System.out.println("   🚀 WELCOME TO INSURANCE MANAGEMENT SYSTEM 🚀");
        System.out.println("====================================================\n");
    }

    private void printMainMenu() {
        System.out.println("\n📋 MAIN MENU");
        System.out.println("1️⃣  Login");
        System.out.println("2️⃣  Register");
        System.out.println("3️⃣  Exit");
        System.out.print("👉 Please select an option (1-3): ");
    }

    private int getUserChoice(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(in.nextLine().trim());
                if (choice >= min && choice <= max) return choice;
            } catch (NumberFormatException ignored) {}
            System.out.print("❌ Invalid input. Try again (" + min + "-" + max + "): ");
        }
    }
}
