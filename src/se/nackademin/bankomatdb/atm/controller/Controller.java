package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.atm.model.DTOAccount;
import se.nackademin.bankomatdb.atm.model.DTOCustomer;
import se.nackademin.bankomatdb.atm.model.DTOLoan;
import se.nackademin.bankomatdb.atm.model.DTOTransaction;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;

import java.util.List;

public class Controller {
    ATMRepository repository;
    DTOCustomer currentCustomer;

    // TODO Kasta lämplig exception om kunden inte är initialiserad
    DTOCustomer getCurrentCustomer() {
        return currentCustomer;
    }

    // TODO Behandla rådatan för konton/lån och översätt från repository till vy
    // Vi lär behöva ändra returtyperna
    List<DTOAccount> getCustomerAccounts() {
        return repository.getCustomerAccounts(getCurrentCustomer());
    }

    List<DTOTransaction> getTransactionHistory(DTOAccount account) {
        return repository.getTransactionHistory(account);
    }

    List<DTOLoan> getCustomerLoans() {
        return repository.getCustomerLoans(getCurrentCustomer());
    }

    public boolean login(String id, String pin) {
        currentCustomer = repository.login(id, pin);
        return (currentCustomer != null);
    }

    public void logout() {
        currentCustomer = null;
    }

    // Returnerar true oom uttaget lyckades, precis som i Repository
    boolean withdraw(DTOAccount account, int amount) {
        return repository.withdraw(account, amount);
    }
}
