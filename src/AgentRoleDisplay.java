import java.util.Scanner;

public class AgentRoleDisplay implements Display {

    @Override
    public void display(String username) {
        UserService userService = new UserService();
        InsuranceAgent agent = userService.loadAgentInfo(username);
        PolicyViewer policyViewer = new PolicyViewer();
        InsuranceAgentService agentService = new InsuranceAgentService();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Agent Dashboard ===");
            System.out.println("1. View all sold policies");
            System.out.println("2. Filter policies by month and year");
            System.out.println("3. View salary for specific month and year");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    policyViewer.viewPoliciesByAgent(agent.getLicense());
                }
                case 2 -> {
                    System.out.print("Enter month (1-12): ");
                    int month = scanner.nextInt();
                    System.out.print("Enter year (e.g., 2025): ");
                    int year = scanner.nextInt();
                    policyViewer.viewPoliciesByAgentAndMonth(agent.getLicense(), month, year);
                }
                case 3 -> {
                    System.out.print("Enter month (1-12): ");
                    int month = scanner.nextInt();
                    System.out.print("Enter year (e.g., 2025): ");
                    int year = scanner.nextInt();
                    agentService.calculateSalaryForAgent(agent.getLicense(), month, year);
                }
                case 4 -> {
                    System.out.println("Logging out...");
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
