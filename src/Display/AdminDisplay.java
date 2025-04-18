package Display;

import DBconfig.QueryAdmin;
import DBconfig.QueryAgent;
import DBconfig.QueryCustomers;
import users.Admin;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminDisplay {

    public void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        QueryAdmin queryAdmin = new QueryAdmin();
        QueryCustomers queryCustomers = new QueryCustomers();
        QueryAgent queryAgent = new QueryAgent();
        Admin admin = new Admin();

        while (true) {
            System.out.println("\n\u001B[35m╔══════════════════════════════════════════╗\u001B[0m");
            System.out.println("\u001B[35m║           \u001B[1;33m🚨 ADMIN CONTROL PANEL 🚨          \u001B[35m║\u001B[0m");
            System.out.println("\u001B[35m╚══════════════════════════════════════════╝\u001B[0m");

            System.out.println("\u001B[1;36m 1. 📋 View All Claims\u001B[0m");
            System.out.println("\u001B[1;34m 2. 🛡️ Set User Role\u001B[0m");
            System.out.println("\u001B[1;32m 3. 🗂️ View Insurance by Filters\u001B[0m");
            System.out.println("\u001B[1;31m 4. ❌ Exit\u001B[0m");

            System.out.print("\n\u001B[1;33mChoose an option: \u001B[0m");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("\u001B[1;36m\n[✔] Showing all claims...\u001B[0m");
                        queryAdmin.printAllHealthPolicyClaims();
                        queryAdmin.AllVehiclePolicyClaims();
                        showAdminMenu();
                        break;
                    case 2:
                        System.out.println("\u001B[1;34m\n[✔] Set user role selected...\u001B[0m");
                        admin.setUserRole();
                        showAdminMenu();
                        break;
                    case 3:
                        System.out.println("\u001B[1;32m\n[✔] Viewing insurance by filters...\u001B[0m");
                        showQueryMenu();
                        break;
                    case 4:
                        System.out.println("\u001B[1;31m\n[👋] Exiting Admin Panel. Goodbye!\u001B[0m");

                        return;
                    default:
                        System.out.println("\u001B[1;31m[!] Invalid option. Please choose between 1–4.\u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[1;31m[!] Invalid input. Please enter a number.\u001B[0m");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

        public static void showQueryMenu() {
            Scanner sc = new Scanner(System.in);
            QueryAgent qa = new QueryAgent();
            QueryCustomers qc = new QueryCustomers();

            while (true) {
                System.out.println("\n╔══════════════════════════════════════════════╗");
                System.out.println("║          🚀 QUERY SELECTION MENU             ║");
                System.out.println("╠══════════════════════════════════════════════╣");
                System.out.println("║ 1️⃣  Vehicle Claim Sold (by License)         ║");
                System.out.println("║ 2️⃣  Health Policy Sold (by License)         ║");
                System.out.println("║ 3️⃣  Vehicle Claims by Month/Year + License  ║");
                System.out.println("║ 4️⃣  Health Claims by Month/Year + License   ║");
                System.out.println("║ 5️⃣  Vehicle Claims by Customer ID           ║");
                System.out.println("║ 6️⃣  Health Policies by Customer ID          ║");
                System.out.println("║ 7️⃣  💰 Agent Salary (by License + Date)      ║");
                System.out.println("║ 0️⃣  ❌ Exit                                  ║");
                System.out.println("╚══════════════════════════════════════════════╝");
                System.out.print("👉 Choose an option: ");

                int choice = sc.nextInt();

                try {
                    switch (choice) {
                        case 1 -> {
                            System.out.print("🔑 Enter License Number: ");
                            int license = sc.nextInt();
                            qa.VehicleClaimSold(license);
                        }
                        case 2 -> {
                            System.out.print("🔑 Enter License Number: ");
                            int license = sc.nextInt();
                            qa.HealthPolicySold(license);
                        }
                        case 3 -> {
                            System.out.print("📅 Enter Start Month: ");
                            int month = sc.nextInt();
                            System.out.print("📅 Enter Start Year: ");
                            int year = sc.nextInt();
                            System.out.print("🔑 Enter License Number: ");
                            int license = sc.nextInt();
                            qa.printVehicleClaimsByStartMonthYearAndLicense(month, year, license);
                        }
                        case 4 -> {
                            System.out.print("📅 Enter Start Month: ");
                            int month = sc.nextInt();
                            System.out.print("📅 Enter Start Year: ");
                            int year = sc.nextInt();
                            System.out.print("🔑 Enter License Number: ");
                            int license = sc.nextInt();
                            qa.printHealthClaimsByStartMonthYearAndLicense(month, year, license);
                        }
                        case 5 -> {
                            System.out.print("🆔 Enter Customer ID: ");
                            int customerId = sc.nextInt();
                            qc.printVehicleClaimsByCustomerId(customerId);
                        }
                        case 6 -> {
                            System.out.print("🆔 Enter Customer ID: ");
                            int customerId = sc.nextInt();
                            qc.CustomerHealthPolicies(customerId);
                        }
                        case 7 -> {
                            System.out.print("🔑 Enter License Number: ");
                            int license = sc.nextInt();
                            System.out.print("📅 Enter Month: ");
                            int month = sc.nextInt();
                            System.out.print("📅 Enter Year: ");
                            int year = sc.nextInt();
                            qa.calculateSalaryForAgent(license, month, year);
                        }
                        case 0 -> {
                            System.out.println("👋 Exiting Query Menu. Have a great day!");
                            return;
                        }
                        default -> System.out.println("⚠️  Invalid choice! Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error: " + e.getMessage());
                }
            }
        }

}
