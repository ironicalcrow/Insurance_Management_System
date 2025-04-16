public class Main {
    public static void main(String[] args) {
        Authentication auth = new Authentication();
        boolean isConnected = auth.testConnection();

        if (isConnected) {
            System.out.println("Ready to use the database.");
        } else {
            System.out.println("Check your DB credentials or server.");
        }
    }
}
