package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;

import java.util.Collection;

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

    // TODO Behandla rådatan för konton/lån och översätt från repository till vy
    // Vi lär behöva ändra returtyperna
    Collection<DTOAccount> getCustomerAccounts() {
        return repository.getCustomerAccounts(getCurrentCustomer());
    }

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account) {
        return repository.getTransactionHistory(account);
    }

    Collection<DTOLoan> getCustomerLoans() {
        return repository.getCustomerLoans(getCurrentCustomer());
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
    boolean withdraw(DTOAccount account, int amount) {
        return repository.withdraw(account, amount);
    }
}
