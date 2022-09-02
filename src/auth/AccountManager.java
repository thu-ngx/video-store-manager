package auth;

import utils.AccountIO;
import utils.AccountUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import core.Manager;
import core.Application;
import core.Constant;
import errors.AccountException;

public class AccountManager extends Manager<Account> {
    public void start() {
        // Initalize account I/O helper
        var helper = new AccountIO(this, new AccountUtils());
        var fileName = Constant.getAccountsFileName();

        // Set the manager's I/O helper and file name to operate on
        setIO(helper, fileName);

        // Load the accounts from the local storage
        load();
    }

    public void stop() {
        // Save the accounts to the local storage
        save();
    }

    public Account getAccountById(String id) {
        for (Account account : getAll())
            if (account.getId().equals(id))
                return account;

        return null;
    }

    public Account getAccountByUsername(String username) {
        for (Account account : getAll())
            if (account.getUsername().equals(username))
                return account;

        return null;
    }

    public boolean authenticate(String username, String password) {
        Account account = getAccountByUsername(username);
        if (account == null)
            return false;
        else
            return account.authenticate(password);
    }

    private String generateId() {
        // IDs have the format:
        // C-000 to C-999 for customers

        // Check for unused IDs
        for (int i = 1; i < 1000; i++) {
            // Pad ID with leading zeros
            String id = "C" + String.format("%03d", i);

            if (!isIdUsed(id))
                return id;
        }

        // No IDs available.
        return null;
    }

    public Account createAccount(String username, String password) throws AccountException {
        // Check if account already exists
        if (getAccountByUsername(username) != null)
            throw new AccountException("Account already exists.");
        else {
            // Generate ID
            String id = generateId();

            if (id == null)
                throw new AccountException("No IDs available.");

            // Create account
            var account = new Account(id, username, password);
            add(account);

            return account;
        }
    }

    public void displayAll() {
        for (Account account : getAll()) {
            System.out.println(account.toString());
        }
    }

    public void displayAccounts(ArrayList<Account> accounts) {
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

    // this method only display information needed when search account
    public void displayAccountsInfo(ArrayList<Account> accounts) {
        for (Account account : accounts) {
            System.out.println(account.getName()
                    + ", " + account.getId()
                    + ", " + account.getPhone()
                    + ", " + account.getAddress());
        }
    }

    // display all accounts sorted by id
    public void displayAccountsSortedById() {
        var accounts = getCopy();
        accounts.sort(Comparator.comparing(Account::getId));
        displayAccounts(accounts);
    }

    // display all accounts sorted by name
    public void displayAccountsSortedByName() {
        var accounts = getCopy();
        accounts.sort(Comparator.comparing(Account::getName));
        displayAccounts(accounts);
    }

    // display a group of account according to role
    public void displayAccountsByRole(String role) {
        for (Account account : getAll()) {
            if (account.getRole().equals(role)) {
                System.out.println(account);
            }
        }
    }

    boolean isIdUsed(String id) {
        for (Account account : getAll()) {
            if (account.getId().equals(id))
                return true;
        }
        return false;
    }

    public void levelUp(Account account) {
        if (Objects.equals(account.getRole(), "VIP"))
            return;

        var app = Application.getInstance();
        var transactionManager = app.getTransactionManager();
        var resolvedAmount = transactionManager.countTransactions(account, true);

        if (Objects.equals(account.getRole(), "REGULAR") && resolvedAmount >= 5) {
            account.setRole("VIP");
            return;
        }

        if (Objects.equals(account.getRole(), "GUEST") && resolvedAmount >= 3) {
            account.setRole("REGULAR");
        }
    }

    public ArrayList<Account> searchAccount(String input) throws AccountException {
        ArrayList<Account> list = new ArrayList<Account>();
        for (Account a : getAll()) {
            if (input.equals(a.getName()) || input.equals(a.getId())) {
                list.add(a);
            }
        }
        if (list.size() == 0) {
            throw new AccountException("No result matched");
        }
        return list;
    }
}
