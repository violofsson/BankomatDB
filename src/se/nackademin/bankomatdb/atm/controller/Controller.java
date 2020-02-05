package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.util.Collection;

public class Controller {
    ATMRepository repository;
    DTOCustomer currentCustomer;

    public Controller() {
        // Intialisera repository
    }

    // TODO Kasta lämplig exception om kunden inte är initialiserad
    // Inte inloggad
    DTOCustomer getCurrentCustomer() {
        return currentCustomer;
    }

    Collection<DTOAccount> getCustomerAccounts() throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getCustomerAccounts(getCurrentCustomer().getCustomerId());
    }

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchAccountException {
        return repository.getTransactionHistory(account.getAccountId());
    }

    Collection<DTOLoan> getCustomerLoans() throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getCustomerLoans(getCurrentCustomer().getCustomerId());
    }

    // Redan inloggad
    public boolean login(String id, String pin) throws DatabaseConnectionException, AlreadyLoggedInException {
        if (currentCustomer != null) {
            throw new AlreadyLoggedInException();
        }
        try {
            currentCustomer = repository.login(id, pin);
            return true;
        } catch (InvalidCredentialsException e) {
            currentCustomer = null;
            return false;
        }
    }

    // Inte inloggad
    public void logout() {
        currentCustomer = null;
    }

    // Returnerar true oom uttaget lyckades, precis som i Repository
    boolean withdraw(DTOAccount account, int amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchAccountException {
        return repository.withdraw(account.getAccountId(), amount);
    }
}
