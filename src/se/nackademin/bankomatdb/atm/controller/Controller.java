package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.atm.AlreadyLoggedInException;
import se.nackademin.bankomatdb.atm.NotLoggedInException;
import se.nackademin.bankomatdb.atm.repository.JRepository;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.util.Collection;

public class Controller {
    private ATMRepository repository;
    private DTOCustomer currentCustomer;

    public Controller() throws DatabaseConnectionException {
        repository = new JRepository();
    }

    public DTOCustomer getCurrentCustomer() throws NotLoggedInException {
        if (currentCustomer != null) {
            return currentCustomer;
        } else {
            throw new NotLoggedInException("No customer is defined");
        }
    }

    public Collection<DTOAccount> getCustomerAccounts() throws DatabaseConnectionException, NotLoggedInException {
        return repository.getCustomerAccounts(getCurrentCustomer().getCustomerId());
    }

    public Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchAccountException {
        return repository.getTransactionHistory(account.getAccountId());
    }

    public Collection<DTOLoan> getCustomerLoans() throws DatabaseConnectionException, NotLoggedInException {
        return repository.getCustomerLoans(getCurrentCustomer().getCustomerId());
    }

    public boolean login(String id, String pin) throws DatabaseConnectionException, AlreadyLoggedInException {
        if (currentCustomer != null) {
            throw new AlreadyLoggedInException("Unable to log in: already logged in");
        }
        try {
            currentCustomer = repository.login(id, pin);
            return true;
        } catch (InvalidCredentialsException e) {
            currentCustomer = null;
            return false;
        }
    }

    public void logout()  throws NotLoggedInException {
        if (currentCustomer != null) {
            currentCustomer = null;
        } else {
            throw new NotLoggedInException("Unable to log out: nobody's logged in");
        }
    }

    // Returnerar true oom uttaget lyckades, precis som i Repository
    public boolean withdraw(DTOAccount account, int amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchAccountException {
        if (amount > account.getBalance()) {
            throw new InsufficientFundsException();
        }
        return repository.withdraw(account.getAccountId(), amount);
    }
}
