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

    // Uppdatera kunduppgifter

    boolean deleteCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        try {
            repository.deleteCustomer(customer.getCustomerId());
            return true;
        } catch (NoSuchCustomerException nsce) {
            return false;
        }
    }

    DTOAccount openAccount(DTOCustomer customer) {
        return null;
    }

    boolean closeAccount(DTOAccount account) {
        return false;
    }

    void deposit(DTOAccount account, double amount) throws DatabaseConnectionException, NoSuchAccountException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to deposit a negative amount");
        try {
            repository.transact(account.getAccountId(), Math.abs(amount));
        } catch (InsufficientFundsException ignored) {

        }
    }

    void withdraw(DTOAccount account, double amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchAccountException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to withdraw a negative amout");
        repository.transact(account.getAccountId(), -1 * Math.abs(amount));
    }

    void updateInterestRate(DTOAccount account, double newRate) throws DatabaseConnectionException, NoSuchAccountException {
        repository.setAccountInterestRate(account.getAccountId(), newRate);
    }

    void approveLoan(DTOLoan loan) {

    }

    void updateLoan(DTOLoan loan) throws DatabaseConnectionException, NoSuchLoanException {
        repository.updateLoan(loan);
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
