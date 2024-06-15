import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ATMSystem {
    private static Map<Integer, User> users = new HashMap<>();

    public static void main(String[] args) {
        users.put(1, new User(1, "1234", 1000.0));
        users.put(2, new User(2, "5678", 2000.0));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        User currentUser = findUserByIdAndPin(userId, pin);
        if (currentUser != null) {
            ATM atm = new ATM(currentUser);
            atm.showMenu();
        } else {
            System.out.println("Invalid user ID or PIN.");
        }
    }

    public static User findUserById(int id) {
        return users.get(id);
    }

    private static User findUserByIdAndPin(int id, String pin) {
        User user = users.get(id);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        } else {
            return null;
        }
    }
}

class ATM {
    private User currentUser;
    private ArrayList<Transaction> transactions;

    public ATM(User user) {
        this.currentUser = user;
        this.transactions = new ArrayList<>();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (currentUser.getAccount().withdraw(amount)) {
            transactions.add(new Transaction("Withdraw", amount));
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        currentUser.getAccount().deposit(amount);
        transactions.add(new Transaction("Deposit", amount));
        System.out.println("Deposit successful.");
    }

    private void transfer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipient user ID: ");
        int recipientId = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        User recipient = ATMSystem.findUserById(recipientId);
        if (recipient != null && currentUser.getAccount().withdraw(amount)) {
            recipient.getAccount().deposit(amount);
            transactions.add(new Transaction("Transfer to user " + recipientId, amount));
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Insufficient funds or recipient not found.");
        }
    }
}

class User {
    private int id;
    private String pin;
    private Account account;

    public User(int id, String pin, double initialBalance) {
        this.id = id;
        this.pin = pin;
        this.account = new Account(initialBalance);
    }

    public int getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Type: " + type + ", Amount: " + amount;
    }
}
