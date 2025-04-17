import java.util.Scanner;

public class Admin {

    private final PolicyViewer policyViewer = new PolicyViewer();
    private final ClaimViewer claimViewer = new ClaimViewer();
    private final UserManager userManager = new UserManager();
    private final Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Claims");
            System.out.println("2. Set User Role");
            System.out.println("3. View Insurance by Filters");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> claimViewer.viewAllClaims();
                case 2 -> setUserRole();
                case 3 -> policyViewer.viewInsuranceByFilters();
                case 4 -> {
                    System.out.println("Exiting Admin Panel...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void setUserRole() {
        System.out.print("Enter username to update role: ");
        String username = scanner.nextLine();
        System.out.print("Enter new role (admin/agent/customer): ");
        String newRole = scanner.nextLine();
        if(newRole.equals("agents")) {}
        userManager.setUserRole(username, newRole);
    }
}
