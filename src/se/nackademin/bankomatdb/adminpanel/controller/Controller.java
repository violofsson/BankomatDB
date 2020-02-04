package se.nackademin.bankomatdb.adminpanel.controller;

import se.nackademin.bankomatdb.adminpanel.repository.Repository;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMAccount;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMCustomer;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMLoan;
import se.nackademin.bankomatdb.adminpanel.viewmodel.VMTransaction;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;

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

    boolean deleteCustomer(VMCustomer customer) {
        return false;
    }

    VMAccount openAccount(VMCustomer customer) {
        return null;
    }

    boolean closeAccount(VMAccount account) {
        return false;
    }

    void deposit(VMAccount account, double amount) {
        // Exception om negativ?
        repository.transact(new DTOAccount(), Math.abs(amount));
    }

    void withdraw(VMAccount account, double amount) {
        // Exception om negaativ?
        repository.transact(new DTOAccount(), -1 * Math.abs(amount));
    }

    void updateInterestRate(VMAccount account, double newRate) {
        repository.setAccountInterestRate(new DTOAccount(), newRate);
    }

    void approveLoan() {

    }

    void updateLoan(VMLoan loan) {

    }

    Collection<VMCustomer> getCustomers() {
        return repository.getCustomerData()
                .stream()
                .map(c -> new VMCustomer())
                .collect(Collectors.toList());
    }

    Collection<VMAccount> getCustomerAccounts(VMCustomer customer) {
        return repository.getAccountData(new DTOCustomer())
                .stream()
                .map(a -> new VMAccount())
                .collect(Collectors.toList());
    }

    Collection<VMLoan> getCustomerLoans(VMCustomer customer) {
        return repository.getLoanData(new DTOCustomer())
                .stream()
                .map(l -> new VMLoan())
                .collect(Collectors.toList());
    }

    Collection<VMTransaction> getAccountTransactions(VMAccount account, LocalDate since) {
        return repository.getTransactionHistory(new DTOAccount())
                .stream()
                .map(t -> new VMTransaction())
                .collect(Collectors.toList());
    }
}
