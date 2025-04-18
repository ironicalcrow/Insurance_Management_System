package Policies;

import DBconfig.Insert;
import users.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class RegisterPolicies {

    public HealthPolicy registerHealthPolicy() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registering Health Policy");

        System.out.print("Starting date (YYYY-MM-DD): ");
        String startingDateString = scanner.nextLine();
        Date startingDate = Date.valueOf(startingDateString);

        System.out.print("Ending date (YYYY-MM-DD): ");
        String endingDateString = scanner.nextLine();
        Date endingDate = Date.valueOf(endingDateString);

        System.out.print("Enter Premium: ");
        double premium = scanner.nextDouble();
        scanner.nextLine(); // Clear the leftover newline

        HealthPolicy hp = new HealthPolicy(premium, startingDate, endingDate);
        Insert insert = new Insert();
        insert.insertHealthPolicy(hp);

        return hp;
    }

    public VehiclePolicy registerVehiclePolicy() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registering Vehicle Policy");

        System.out.print("Enter Vehicle Type: ");
        String vehicleType = scanner.nextLine();

        System.out.print("Enter Vehicle Plate Number: ");
        String vehiclePlateNumber = scanner.nextLine();

        System.out.print("Enter Vehicle Price: ");
        double vehiclePrice = scanner.nextDouble();

        System.out.print("Enter Premium: ");
        double premium = scanner.nextDouble();
        scanner.nextLine(); // Clear the leftover newline

        System.out.print("Starting date (YYYY-MM-DD): ");
        String startingDateString = scanner.nextLine();
        Date startingDate = Date.valueOf(startingDateString);

        System.out.print("Ending date (YYYY-MM-DD): ");
        String endingDateString = scanner.nextLine();
        Date endingDate = Date.valueOf(endingDateString);

        VehiclePolicy vp = new VehiclePolicy(vehicleType, vehiclePlateNumber, vehiclePrice, premium, startingDate, endingDate);
        Insert insert = new Insert();
        insert.insertVehiclePolicy(vp);

        return vp;
    }

    public void registerVehicleClaim(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registering Vehicle Claim");

        System.out.print("Enter Insurance Agent License Number: ");
        int license = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        VehiclePolicy vp = registerVehiclePolicy();
        Insert insert = new Insert();
        insert.insertVehicleClaim(user, vp, license);
    }

    public void registerHealthClaim(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registering Health Claim");

        System.out.print("Enter Insurance Agent License Number: ");
        int license = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        HealthPolicy hp = registerHealthPolicy();
        Insert insert = new Insert();
        insert.insertHealthClaim(user, hp, license);
    }
}
