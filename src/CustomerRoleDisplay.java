import java.sql.*;
import java.sql.Date;
import java.util.Scanner;

public class CustomerRoleDisplay implements Display {

    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void display(String username) {
        User user= new User();
        System.out.println("1. Show Customers");
        System.out.println("2. Register Customer");
        int choice;
        Scanner input = new Scanner(System.in);
        choice = input.nextInt();
        if (choice == 1) {
            user.showUserCustomersAndClaims(username);
        }
        else if (choice == 2) {
            registerClaim();
        }
    }

    private Customer fetchCustomerByID(int customerId) {
        Customer customer = null;
        String query = "SELECT * FROM Customers WHERE C_id = ?";

        try (Connection connection = DBconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);  // Set the customer ID
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String NID = resultSet.getString("NID");
                String birthCertificate = resultSet.getString("BirthCertificate");
                Date birthdate = resultSet.getDate("birthdate");
                boolean gender = resultSet.getBoolean("gender");

                customer = new Customer(firstName, lastName, address, phone, email, NID, birthCertificate, birthdate, gender);
            } else {
                System.out.println("No customer found with ID: " + customerId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // Method to register a claim for an existing or new customer
    public void registerClaim() {
        System.out.println("Do you want to register for an existing customer or a new customer?");
        System.out.println("1. Existing Customer");
        System.out.println("2. New Customer");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        Customer customer = null;
        if (choice == 1) {
            // Fetch customer by ID if user wants to use an existing customer
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
            customer = fetchCustomerByID(customerId);
        } else if (choice == 2) {
            // Register a new customer
            System.out.println("Enter customer details:");
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
            Date birthDate = Date.valueOf(birthDateString); // Use java.sql.Date
            System.out.print("Gender (true for Male, false for Female): ");
            boolean gender = scanner.nextBoolean();
            scanner.nextLine();

            RegistrationSystem rs= new RegistrationSystem();
            customer = new Customer(firstName, lastName, address, phone, email, NID, birthCertificate, birthDate, gender);
            rs.registerCustomer(customer);
        }

        if (customer != null) {
            System.out.println("Customer details: " + customer.getFirstName() + " " + customer.getLastName());

            System.out.println("Do you want to register a Health or Vehicle Policy?");
            System.out.println("1. Health Policy");
            System.out.println("2. Vehicle Policy");
            int policyChoice = scanner.nextInt();
            scanner.nextLine();

            if (policyChoice == 1) {
                registerHealthPolicy(customer);
            } else if (policyChoice == 2) {
                registerVehiclePolicy(customer);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerHealthPolicy(Customer customer) {
        System.out.println("Enter Health Policy details:");

        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDateString = scanner.nextLine();
        Date startDate = Date.valueOf(startDateString);

        System.out.print("End Date (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();
        Date endDate = Date.valueOf(endDateString);

        System.out.print("Premium: ");
        double premium = scanner.nextDouble();
        scanner.nextLine();
        HealthPolicy hp = new HealthPolicy(premium,startDate,endDate);
        String query = "INSERT INTO HealthPolicy (H_P_id,start_date, end_date, premium) VALUES (?,?, ?, ?)";
        try (Connection connection = DBconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1,hp.getHealthPolicyNumber());
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setDouble(4, premium);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {;  // Get the generated Health Policy ID
                    registerHealthClaim(customer,hp.getHealthPolicyNumber());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to register a Health Claim for the customer
    private void registerHealthClaim(Customer customer, int h_p_id) {
        System.out.println("Enter Claim details for Health Policy (ID: " + h_p_id + "):");

        System.out.println("Enter claim status (0: Pending, 1: Approved, 2: Denied):");
        int claimStatus = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Insert the claim into the Claim_H_P table
        String query = "INSERT INTO Claim_H_P (c_id, h_p_id, claimStatus) VALUES (?, ?, ?)";
        try (Connection connection = DBconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customer.getCustomerID());
            statement.setInt(2, h_p_id);
            statement.setInt(3, claimStatus);

            statement.executeUpdate();
            System.out.println("Claim registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to register a Vehicle Policy
    private void registerVehiclePolicy(Customer customer) {
        System.out.println("Enter Vehicle Policy details:");

        System.out.print("Vehicle Type: ");
        String vehicleType = scanner.nextLine();

        System.out.print("Plate Number: ");
        String plateNumber = scanner.nextLine();

        System.out.print("Vehicle Price: ");
        double vehiclePrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDateString = scanner.nextLine();
        Date startDate = Date.valueOf(startDateString);

        System.out.print("End Date (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();
        Date endDate = Date.valueOf(endDateString);

        System.out.print("Premium: ");
        double premium = scanner.nextDouble();
        scanner.nextLine();

        VehiclePolicy vp = new VehiclePolicy(vehicleType,plateNumber,vehiclePrice,premium,startDate,endDate);
        // Insert Vehicle Policy into the database
        String query = "INSERT INTO VehiclePolicy (vehicle_type, plate_number, vehicle_price, premium, start_date, end_date) VALUES (?, ?, ?)";
        try (Connection connection = DBconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, vp.getVehiclePolicyID());
            statement.setString(2, vehicleType);
            statement.setString(3, plateNumber);
            statement.setDouble(4, vehiclePrice);
            statement.setDouble(5, premium);
            statement.setDate(6, startDate);
            statement.setDate(7, endDate);


            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int v_p_id = generatedKeys.getInt(1);  // Get the generated Vehicle Policy ID
                    registerVehicleClaim(customer, v_p_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to register a Vehicle Claim for the customer
    private void registerVehicleClaim(Customer customer, int v_p_id) {
        System.out.println("Enter Claim details for Vehicle Policy (ID: " + v_p_id + "):");

        System.out.println("Enter claim status (0: Pending, 1: Approved, 2: Denied):");
        int claimStatus = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Insert the claim into the Claim_V_P table
        String query = "INSERT INTO Claim_V_P (c_id, v_p_id, claimStatus) VALUES (?, ?, ?)";
        try (Connection connection = DBconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customer.getCustomerID());
            statement.setInt(2, v_p_id);
            statement.setInt(3, claimStatus);

            statement.executeUpdate();
            System.out.println("Claim registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
