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

    DTOCustomer addCustomer() {
        return null;
    }

    DTOCustomer updateCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        return repository.updateCustomer(customer);
    }

    boolean deleteCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        try {
            repository.deleteCustomer(customer.getCustomerId());
            return true;
        } catch (NoSuchCustomerException nsce) {
            return false;
        }
    }

    DTOAccount openAccount(DTOCustomer customer, double interestRate) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.openAccount(customer.getCustomerId(), interestRate);
    }

    void closeAccount(DTOAccount account) throws DatabaseConnectionException, NoSuchCustomerException {
        repository.closeAccount(account.getAccountId());
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

    void updateInterestRate(DTOAccount account, double newRate) throws DatabaseConnectionException, NoSuchAccountException {
        repository.setAccountInterestRate(account.getAccountId(), newRate);
    }

    void approveLoan(double sum, double interestRate, LocalDate deadline) {
        approveLoan(sum, interestRate, deadline);
    }

    void updateLoan(DTOLoan loan, double newInterestRate, LocalDate newDeadline) throws DatabaseConnectionException, NoSuchLoanException {
        repository.updateLoan(new DTOLoan(loan.getLoanId()));
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
