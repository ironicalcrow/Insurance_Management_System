import java.util.Scanner;

public class Console {
    public void display() {
        Authentication auth = new Authentication();
        System.out.println("Welcome To Insurance Management System");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter Username");
                String username = scanner.next();
                System.out.println("Enter Password");
                String password = scanner.next();
                String s= auth.login(username, password);
                caller(s, username);
                break;
            case 2:
                System.out.println("Enter Username");
                String user = scanner.next();
                System.out.println("Enter Email");
                String email = scanner.next();
                System.out.println("Enter Password");
                String pass = scanner.next();
                auth.register(user, email, pass);
                display();
                break;
            case 3:
                System.out.println("Exitting..............");
                return;
        }
    }

    private void caller(String role, String username)
    {
        if(role.equals("admin"))
        {
            Admin admin = new Admin();
            admin.showAdminMenu();
        }
        else if(role.equals("customer"))
        {
            CustomerRoleDisplay customerRoleDisplay = new CustomerRoleDisplay();
            customerRoleDisplay.display(username);
        } else if (role.equals("agent")){
            AgentRoleDisplay agentRoleDisplay = new AgentRoleDisplay();
            agentRoleDisplay.display(username);

        }
    }
}
