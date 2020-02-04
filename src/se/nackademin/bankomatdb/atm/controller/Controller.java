package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.atm.viewmodel.VMAccount;
import se.nackademin.bankomatdb.atm.viewmodel.VMLoan;
import se.nackademin.bankomatdb.atm.viewmodel.VMTransaction;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class Controller {
    ATMRepository repository;
    DTOCustomer currentCustomer;

    public Controller() {
        // Intialisera repository
    }

    // TODO Kasta lämplig exception om kunden inte är initialiserad
    DTOCustomer getCurrentCustomer() {
        return currentCustomer;
    }

    Collection<VMAccount> getCustomerAccounts() {
        return repository.getCustomerAccounts(getCurrentCustomer())
                .stream()
                .map(a -> new VMAccount()) // TODO Ersätt med riktig konstruktoe
                .collect(Collectors.toList());
    }

    Collection<VMTransaction> getTransactionHistory(DTOAccount account) {
        return repository.getTransactionHistory(account)
                .stream()
                .map(t -> new VMTransaction()) // TODO Ersätt med riktig konstruktor
                .collect(Collectors.toList());
    }

    Collection<VMLoan> getCustomerLoans() {
        return repository.getCustomerLoans(getCurrentCustomer())
                .stream()
                .map(l -> new VMLoan()) // TODO Riktig konstruktor
                .collect(Collectors.toList());
    }

    public boolean login(String id, String pin) {
        // TODO Kasta exception om redan inloggad
        try {
            currentCustomer = repository.login(id, pin);
            return true;
        } catch (Exception e) {
            currentCustomer = null;
            return false;
        }
    }

    public void logout() {
        currentCustomer = null;
    }

    // Returnerar true oom uttaget lyckades, precis som i Repository
    // TODO Översätt från vyns VMAccount till DTOAccount
    boolean withdraw(DTOAccount account, int amount) {
        return repository.withdraw(account, amount);
    }
}
