package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.atm.model.DTOAccount;
import se.nackademin.bankomatdb.atm.model.DTOCustomer;
import se.nackademin.bankomatdb.atm.model.DTOLoan;
import se.nackademin.bankomatdb.atm.model.DTOTransaction;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;

import java.util.Collection;

public class Controller {
    // Eftersom vi bara aldrig uppdaterar dem kan vi hämta konton och lån
    // vid inloggning och lagra dem istället för att hämta på nytt varje gång.
    // Transaktioner måste dock hämtas om, eftersom vi kan uppdatera dem
    // genom att ta ut pengar.
    ATMRepository repository;
    DTOCustomer currentCustomer;
    Collection<DTOAccount> customerAccounts;
    Collection<DTOLoan> customerLoans;

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
        return customerAccounts;
    }

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account) {
        return repository.getTransactionHistory(account);
    }

    Collection<DTOLoan> getCustomerLoans() {
        return customerLoans;
    }

    public boolean login(String id, String pin) {
        // TODO Kasta exception om redan inloggad
        try {
            currentCustomer = repository.login(id, pin);
            customerAccounts = repository.getCustomerAccounts(currentCustomer);
            customerLoans = repository.getCustomerLoans(currentCustomer);
            return true;
        } catch (Exception e) {
            currentCustomer = null;
            customerAccounts = null;
            customerLoans = null;
            return false;
        }
    }

    public void logout() {
        currentCustomer = null;
        customerAccounts = null;
        customerLoans = null;
    }

    // Returnerar true oom uttaget lyckades, precis som i Repository
    boolean withdraw(DTOAccount account, int amount) {
        return repository.withdraw(account, amount);
    }
}
