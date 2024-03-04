import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userID;
    private String userPIN;
    private double accountBalance;

    public User(String userID, String userPIN, double accountBalance) {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double newBalance) {
        this.accountBalance = newBalance;
    }
}

class ATM {
    private Map<String, User> users;
    private User currentUser;
    private final int MAX_ATTEMPTS = 3;

    public ATM() {
        this.users = new HashMap<>();
        users.put("290307", new User("290307", "1234", 1000.0));
        users.put("922967", new User("922967", "5678", 500.0));
	users.put("178962", new User("178962", "1357", 10000.0));

    }

    public void start() {
        System.out.println("Welcome to the ATM!");


        authenticateUser();


        while (currentUser != null) {
            showMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void authenticateUser() {
        Scanner scanner = new Scanner(System.in);

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.print("Enter User ID: ");
            String userID = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String userPIN = scanner.nextLine();

            User user = users.get(userID);

            if (user != null && user.getUserPIN().equals(userPIN)) {
                System.out.println("Authentication successful!");
                currentUser = user;
                return; 
            } else {
                System.out.println("Authentication failed. Please try again. Attempts left: " +
                        (MAX_ATTEMPTS - attempt));
            }
        }

        System.out.println("Maximum attempts reached. Exiting...");
        currentUser = null; 
    }

    private void showMenu() {
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. Exit");
    }

    private int getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private void checkBalance() {
        System.out.println("Your current balance: $" + currentUser.getAccountBalance());
    }

    private void withdraw() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getAccountBalance()) {
            double newBalance = currentUser.getAccountBalance() - amount;
            currentUser.setAccountBalance(newBalance);
            System.out.println("Withdrawal successful. Remaining balance: $" + newBalance);
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    private void deposit() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            double newBalance = currentUser.getAccountBalance() + amount;
            currentUser.setAccountBalance(newBalance);
            System.out.println("Deposit successful. New balance: $" + newBalance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private void exit() {
        System.out.println("Exiting the ATM. Thank you for using our services!");
        currentUser = null; 
    }
}

public class task2 {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
