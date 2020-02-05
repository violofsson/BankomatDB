package se.nackademin.bankomatdb.adminpanel.controller;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.adminpanel.repository.Repository;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

// TODO
public class Controller {
    Repository repository;

    DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException {
        return repository.addCustomer(name, personalId, pin);
    }

    DTOCustomer updateCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        return repository.updateCustomer(customer);
    }

    boolean deleteCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        return repository.deleteCustomer(customer.getCustomerId());
    }

    DTOAccount openAccount(DTOCustomer customer, double interestRate) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.openAccount(customer.getCustomerId(), interestRate);
    }

    boolean closeAccount(DTOAccount account) throws DatabaseConnectionException {
        return repository.closeAccount(account.getAccountId());
    }

    void deposit(DTOAccount account, double amount) throws DatabaseConnectionException, NoSuchAccountException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to deposit a negative amount");
        repository.deposit(account.getAccountId(), amount);
    }

    void withdraw(DTOAccount account, double amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchAccountException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to withdraw a negative amount");
        repository.withdraw(account.getAccountId(), -amount);
    }

    DTOAccount updateInterestRate(DTOAccount account, double newRate) throws DatabaseConnectionException, NoSuchAccountException {
        return repository.setAccountInterestRate(account.getAccountId(), newRate);
    }

    DTOLoan approveLoan(DTOCustomer customer, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.approveLoan(customer.getCustomerId(), sum, interestRate, deadline);
    }

    DTOLoan updateLoan(DTOLoan loan, double newInterestRate, LocalDate newDeadline) throws DatabaseConnectionException, NoSuchLoanException {
        return repository.updateLoan(loan.updated(newInterestRate, newDeadline));
    }

    Collection<DTOCustomer> getCustomers() throws DatabaseConnectionException {
        return repository.getCustomerData();
    }

    Collection<DTOAccount> getCustomerAccounts(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getAccountData(customer);
    }

    Collection<DTOLoan> getCustomerLoans(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getLoanData(customer);
    }

    Collection<DTOTransaction> getAccountTransactions(DTOAccount account, LocalDate since) throws DatabaseConnectionException, NoSuchAccountException {
        return repository.getTransactionHistory(account)
                .stream()
                .filter(t -> !t.getTransactionTime().isBefore(ChronoLocalDateTime.from(since)))
                .collect(Collectors.toList());
    }
}
