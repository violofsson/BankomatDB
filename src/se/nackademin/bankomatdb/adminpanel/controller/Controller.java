package se.nackademin.bankomatdb.adminpanel.controller;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.adminpanel.repository.VRepository;
import se.nackademin.bankomatdb.adminpanel.repository.Repository;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// TODO
public class Controller {
    Repository repository;
    Map<Integer, DTOCustomer> customers = new HashMap<>();
    Map<Integer, DTOAccount> accounts = new HashMap<>();
    Map<Integer, DTOLoan> loans = new HashMap<>();
    Map<Integer, Collection<DTOTransaction>> transactions = new HashMap<>();

    Controller() throws DatabaseConnectionException {
        this.repository = new VRepository();
        reloadAll();
    }

    private void reloadAll() throws DatabaseConnectionException {
        customers.clear();
        accounts.clear();
        loans.clear();
        transactions.clear();
        customers = repository.getCustomerData().stream().collect(Collectors.toMap(DTOCustomer::getCustomerId, c -> c));
        for (DTOCustomer c : customers.values()) {
            try {
                accounts.putAll(repository.getAccountData(c).stream().collect(Collectors.toMap(DTOAccount::getAccountId, acc -> acc)));
                loans.putAll(repository.getLoanData(c).stream().collect(Collectors.toMap(DTOLoan::getLoanId, l -> l)));
            } catch (NoSuchRecordException ignored) {
                // ???
            }
        }
        for (DTOAccount acc : accounts.values()) {
            try {
                transactions.put(acc.getAccountId(), repository.getTransactionHistory(acc));
            } catch (NoSuchRecordException ignored) {
                // ???
            }
        }
    }

    DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException, InvalidInsertException {
        DTOCustomer newCustomer = repository.addCustomer(name, personalId, pin);
        customers.put(newCustomer.getCustomerId(), newCustomer);
        return newCustomer;
    }

    DTOCustomer updateCustomer(DTOCustomer customer, String newName, String newPin) throws DatabaseConnectionException, NoSuchRecordException {
        DTOCustomer updated = repository.updateCustomer(customer, newName, newPin);
        customers.put(updated.getCustomerId(), updated);
        return updated;
    }

    boolean deleteCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        customers.remove(customer.getCustomerId());
        return repository.deleteCustomer(customer.getCustomerId());
    }

    DTOAccount openAccount(DTOCustomer customer, double interestRate) throws DatabaseConnectionException, NoSuchRecordException, InvalidInsertException {
        DTOAccount newAccount = repository.openAccount(customer.getCustomerId(), 0, interestRate);
        accounts.put(newAccount.getAccountId(), newAccount);
        return newAccount;
    }

    boolean closeAccount(DTOAccount account) throws DatabaseConnectionException {
        accounts.remove(account.getAccountId());
        return repository.closeAccount(account.getAccountId());
    }

    void deposit(DTOAccount account, double amount) throws DatabaseConnectionException, NoSuchRecordException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to deposit a negative amount");
        repository.deposit(account.getAccountId(), amount);
    }

    void withdraw(DTOAccount account, double amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchRecordException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to withdraw a negative amount");
        repository.withdraw(account.getAccountId(), -amount);
    }

    DTOAccount updateInterestRate(DTOAccount account, double newRate) throws DatabaseConnectionException, NoSuchRecordException {
        DTOAccount updated = repository.setAccountInterestRate(account.getAccountId(), newRate);
        accounts.put(updated.getAccountId(), updated);
        return updated;
    }

    DTOLoan approveLoan(DTOCustomer customer, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchRecordException, InvalidInsertException {
        DTOLoan approved = repository.approveLoan(customer.getCustomerId(), sum, interestRate, deadline);
        loans.put(approved.getLoanId(), approved);
        return approved;
    }

    DTOLoan updateLoan(DTOLoan loan, double newInterestRate, LocalDate newDeadline) throws DatabaseConnectionException, NoSuchRecordException {
        DTOLoan updated = repository.updateLoan(loan, newInterestRate, newDeadline);
        loans.put(updated.getLoanId(), updated);
        return updated;
    }

    // TODO Mappa till vymodell
    Collection<DTOCustomer> getCustomers() {
        return customers.values()
                .stream()
                .sorted(Comparator.comparingInt(DTOCustomer::getCustomerId))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<DTOAccount> getCustomerAccounts(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        return accounts.values()
                .stream()
                .filter(acc -> acc.getOwnerId() == customer.getCustomerId())
                .sorted(Comparator.comparingInt(DTOAccount::getAccountId))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<DTOLoan> getCustomerLoans(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        return loans.values()
                .stream()
                .filter(loan -> loan.getDebtorId() == customer.getCustomerId())
                .sorted(Comparator.comparingInt(DTOLoan::getLoanId))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<DTOTransaction> getAccountTransactions(DTOAccount account, LocalDate since) throws DatabaseConnectionException, NoSuchRecordException {
        return transactions.get(account.getAccountId())
                .stream()
                .filter(t -> !t.getTransactionTime().isBefore(ChronoLocalDateTime.from(since)))
                .sorted(Comparator.comparingInt(DTOTransaction::getTransactionId))
                .collect(Collectors.toUnmodifiableList());
    }
}
