package users;

import DBconfig.Delete;
import DBconfig.PullHuman;
import DBconfig.Push;
import Human.Customer;
import Human.InsuranceAgent;
import Human.Person;

import java.sql.Date;
import java.util.Scanner;

public class Register {
    Register() {
    }

    public Person createPerson() {
        System.out.println("Enter details:");
        Scanner scanner = new Scanner(System.in);
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("NID: ");
        String NID = scanner.nextLine();
        System.out.print("Birth Certificate: ");
        String birthCertificate = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String birthDateString = scanner.nextLine();
        Date birthDate = Date.valueOf(birthDateString);
        System.out.print("Gender (true for Male, false for Female): ");
        boolean gender = scanner.nextBoolean();
        scanner.nextLine();
        Person p = new Person(firstName, lastName, address, phone, email, NID,birthCertificate ,birthDate,gender);
        return p;
    }
    public void InsuranceAgent(User user) {
        PullHuman pullHuman = new PullHuman();
        Customer customer = pullHuman.getCustomerFromDB(user);
        if (customer == null || customer.getPerson() == null) {
            System.out.println("❌ Failed to fetch customer or person data. Cannot proceed.");
            return;
        }

        InsuranceAgent insuranceAgent = new InsuranceAgent(customer.getPerson());
        Push push = new Push();
        push.registerAgent(insuranceAgent);
        push.UserXAgent(insuranceAgent, user);
        Delete delete = new Delete();
        delete.deleteCustomerByUsername(user);
    }

    public void Customer(User user) {
        Customer customer = new Customer(createPerson());
        Push push = new Push();
        push.registerCustomer(customer);
        push.UserXCustomer(customer, user);
    }
}
