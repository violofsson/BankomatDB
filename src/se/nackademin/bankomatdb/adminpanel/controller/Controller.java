package se.nackademin.bankomatdb.adminpanel.controller;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.NoSuchAccountException;
import se.nackademin.bankomatdb.NoSuchCustomerException;
import se.nackademin.bankomatdb.adminpanel.repository.Repository;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMAccount;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMCustomer;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMLoan;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMTransaction;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

// TODO
public class Controller {
    Repository repository;

    VMCustomer addCustomer() {
        return null;
    }

    // Uppdatera kunduppgifter

    boolean deleteCustomer(VMCustomer customer) throws DatabaseConnectionException {
        try {
            repository.deleteCustomer(customer.getId());
            return true;
        } catch (NoSuchCustomerException nsce) {
            return false;
        }
    }

    VMAccount openAccount(VMCustomer customer) {
        return null;
    }

    boolean closeAccount(VMAccount account) {
        return false;
    }

    void deposit(VMAccount account, double amount) throws DatabaseConnectionException, NoSuchAccountException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to deposit a negative amount");
        try {
            repository.transact(account.getId(), Math.abs(amount));
        } catch (InsufficientFundsException ignored) {

        }
    }

    void withdraw(VMAccount account, double amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchAccountException {
        if (amount < 0)
            throw new IllegalArgumentException("Attempting to withdraw a negative amout");
        repository.transact(account.getId(), -1 * Math.abs(amount));
    }

    void updateInterestRate(VMAccount account, double newRate) throws DatabaseConnectionException, NoSuchAccountException {
        repository.setAccountInterestRate(account.getId(), newRate);
    }

    void approveLoan() {

    }

    void updateLoan(VMLoan loan) throws DatabaseConnectionException {
        repository.updateLoan(new DTOLoan());
    }

    Collection<VMCustomer> getCustomers() throws DatabaseConnectionException {
        return repository.getCustomerData()
                .stream()
                .map(c -> new VMCustomer())
                .collect(Collectors.toList());
    }

    Collection<VMAccount> getCustomerAccounts(VMCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getAccountData(new DTOCustomer())
                .stream()
                .map(a -> new VMAccount())
                .collect(Collectors.toList());
    }

    Collection<VMLoan> getCustomerLoans(VMCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getLoanData(new DTOCustomer())
                .stream()
                .map(l -> new VMLoan())
                .collect(Collectors.toList());
    }

    Collection<VMTransaction> getAccountTransactions(VMAccount account, LocalDate since) throws DatabaseConnectionException, NoSuchAccountException {
        return repository.getTransactionHistory(new DTOAccount())
                .stream()
                .map(t -> new VMTransaction())
                .collect(Collectors.toList());
    }
}
