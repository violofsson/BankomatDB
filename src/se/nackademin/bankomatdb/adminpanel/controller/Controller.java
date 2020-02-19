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

public class Controller {
    Repository repository;

    Controller() throws DatabaseConnectionException {
        this.repository = new VRepository();
    }

    DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException, InvalidInsertException {
        return repository.addCustomer(name, personalId, pin);
    }

    DTOCustomer updateCustomer(DTOCustomer customer, String newName, String newPin) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.updateCustomer(customer, newName, newPin);
    }

    boolean deleteCustomer(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.deleteCustomer(customer.getCustomerId());
    }

    DTOAccount openAccount(DTOCustomer customer, double interestRate) throws DatabaseConnectionException, NoSuchRecordException, InvalidInsertException {
        return repository.openAccount(customer.getCustomerId(), 0, interestRate);
    }

    boolean closeAccount(DTOAccount account) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.closeAccount(account.getAccountId());
    }

    DTOAccount deposit(DTOAccount account, double amount) throws DatabaseConnectionException, NoSuchRecordException {
        if (amount < 0) throw new IllegalArgumentException("Attempting to deposit a negative amount");
        return repository.deposit(account.getAccountId(), amount);
    }

    DTOAccount withdraw(DTOAccount account, double amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchRecordException {
        if (amount < 0) throw new IllegalArgumentException("Attempting to withdraw a negative amount");
        return repository.withdraw(account.getAccountId(), amount);
    }

    DTOAccount updateInterestRate(DTOAccount account, double newRate) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.setAccountInterestRate(account.getAccountId(), newRate);
    }

    DTOLoan approveLoan(DTOCustomer customer, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchRecordException, InvalidInsertException {
        return repository.approveLoan(customer.getCustomerId(), sum, interestRate, deadline);
    }

    DTOLoan updateLoan(DTOLoan loan, double newInterestRate, LocalDate newDeadline) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.updateLoan(loan, newInterestRate, newDeadline);
    }

    // TODO Mappa till vymodell
    Collection<DTOCustomer> getCustomers() throws DatabaseConnectionException {
        return repository.getCustomerData()
                .stream()
                .sorted(Comparator.comparingInt(DTOCustomer::getCustomerId))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<DTOAccount> getCustomerAccounts(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.getAccountData(customer)
                .stream()
                .sorted(Comparator.comparingInt(DTOAccount::getAccountId))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<DTOLoan> getCustomerLoans(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.getLoanData(customer)
                .stream()
                .sorted(Comparator.comparingInt(DTOLoan::getLoanId))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<DTOTransaction> getAccountTransactions(DTOAccount account, LocalDate since) throws DatabaseConnectionException, NoSuchRecordException {
        return repository.getTransactionHistory(account)
                .stream()
                .filter(t -> !t.getTransactionTime().isBefore(ChronoLocalDateTime.from(since)))
                .sorted(Comparator.comparingInt(DTOTransaction::getTransactionId))
                .collect(Collectors.toUnmodifiableList());
    }
}
