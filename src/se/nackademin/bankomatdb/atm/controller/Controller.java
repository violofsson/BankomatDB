package se.nackademin.bankomatdb.atm.controller;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.atm.viewmodel.VMAccount;
import se.nackademin.bankomatdb.atm.viewmodel.VMLoan;
import se.nackademin.bankomatdb.atm.viewmodel.VMTransaction;
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
    // Inte inloggad
    DTOCustomer getCurrentCustomer() {
        return currentCustomer;
    }

    Collection<VMAccount> getCustomerAccounts() throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getCustomerAccounts(getCurrentCustomer().getCustomerId())
                .stream()
                .map(a -> new VMAccount()) // TODO Ersätt med riktig konstruktoe
                .collect(Collectors.toList());
    }

    Collection<VMTransaction> getTransactionHistory(VMAccount account) throws DatabaseConnectionException, NoSuchAccountException {
        return repository.getTransactionHistory(account.getAccountId())
                .stream()
                .map(t -> new VMTransaction()) // TODO Ersätt med riktig konstruktor
                .collect(Collectors.toList());
    }

    Collection<VMLoan> getCustomerLoans() throws DatabaseConnectionException, NoSuchCustomerException {
        return repository.getCustomerLoans(getCurrentCustomer().getCustomerId())
                .stream()
                .map(l -> new VMLoan()) // TODO Riktig konstruktor
                .collect(Collectors.toList());
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
    boolean withdraw(VMAccount account, int amount) throws InsufficientFundsException, DatabaseConnectionException, NoSuchAccountException {
        return repository.withdraw(account.getAccountId(), amount);
    }
}
