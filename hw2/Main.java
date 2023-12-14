import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Account {
    private String currency;
    private double balance;

    public Account(String currency) {
        this.currency = currency;
        this.balance = 0.0;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }
}

class Client {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Account> accounts;

    public Client(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.accounts = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void createAccount(String currency) {
        accounts.add(new Account(currency));
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}

class Bank {
    private Map<String, Client> clients;

    public Bank() {
        this.clients = new HashMap<>();
    }

    public void addClient(String firstName, String lastName, String phoneNumber) {
        clients.put(phoneNumber, new Client(firstName, lastName, phoneNumber));
    }

    public Client getClient(String phoneNumber) {
        return clients.get(phoneNumber);
    }

    public void removeClient(String phoneNumber) {
        clients.remove(phoneNumber);
    }

    public double getTotalBalance() {
        double totalBalance = 0.0;

        for (Client client : clients.values()) {
            for (Account account : client.getAccounts()) {
                totalBalance += account.getBalance();
            }
        }

        return totalBalance;
    }
}

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Добавление клиентов
        bank.addClient("John", "Doe", "123456789");
        bank.addClient("Jane", "Doe", "987654321");

        // Создание счетов
        bank.getClient("123456789").createAccount("USD");
        bank.getClient("987654321").createAccount("EUR");

        // Внесение денег на счета
        bank.getClient("123456789").getAccounts().get(0).deposit(1000);
        bank.getClient("987654321").getAccounts().get(0).deposit(500);

        // Вывод информации о клиентах и общем балансе
        System.out.println("Client 1 Balance: " +
                bank.getClient("123456789").getAccounts().get(0).getBalance());
        System.out.println("Client 2 Balance: " +
                bank.getClient("987654321").getAccounts().get(0).getBalance());

        System.out.println("Total Balance: " + bank.getTotalBalance());
    }
}
