package Display;

import DBconfig.PullHuman;
import DBconfig.QueryAgent;
import Human.InsuranceAgent;
import users.User;

import java.util.Scanner;

public class AgentDisplay {
 public void display(User user) {
     Scanner scanner = new Scanner(System.in);
     PullHuman p= new PullHuman();
     InsuranceAgent agent= p.getInsuranceAgentFromDB(user);
     QueryAgent ag= new QueryAgent();
     int month, year;

     // Print the Agent Dashboard in a modern and cool style
     System.out.println("\u001B[36m" + "======================================" + "\u001B[0m");
     System.out.println("\u001B[33m" + "  === AGENT DASHBOARD ===" + "\u001B[0m");
     System.out.println("\u001B[36m" + "======================================" + "\u001B[0m");

     System.out.println("\n\033[1;32m 1. View All Sold Policies\033[0m");
     System.out.println("\033[1;34m 2. Filter Policies by Month and Year\033[0m");
     System.out.println("\033[1;35m 3. View Salary for Specific Month and Year\033[0m");
     System.out.println("\033[1;31m 4. Exit\033[0m");

     System.out.print("\n\033[1;33mChoose an option: \033[0m");
     int choice = scanner.nextInt();

     // Switch case to handle the choices (for demonstration)
     switch (choice) {
         case 1:
             System.out.println("You chose to view all sold policies.");
             ag.HealthPolicySold(agent.getLicense());
             ag.VehicleClaimSold(agent.getLicense());
             display(user);
             break;
         case 2:
             System.out.println("You chose to filter policies by month and year.");
             System.out.println("Enter month: ");
             month = scanner.nextInt();
             System.out.println("Enter year: ");
             year = scanner.nextInt();
             ag.printHealthClaimsByStartMonthYearAndLicense(month, year,agent.getLicense());
             ag.printVehicleClaimsByStartMonthYearAndLicense(month, year,agent.getLicense());
             display(user);
             break;
         case 3:
             System.out.println("You chose to view the salary for a specific month and year.");
             System.out.println("Enter month: ");
             month = scanner.nextInt();
             System.out.println("Enter year: ");
             year = scanner.nextInt();
             ag.calculateSalaryForAgent(agent.getLicense(), month, year);
             display(user);
             break;
         case 4:
             System.out.println("Exiting...");
             return;
         default:
             System.out.println("Invalid option.");
     }
 }
}
