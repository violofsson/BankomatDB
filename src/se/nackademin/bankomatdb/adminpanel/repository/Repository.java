package se.nackademin.bankomatdb.adminpanel.repository;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.util.Collection;

// TODO
public interface Repository {
    // Obs: upserts returnerar skapat/uppdaterat objekt, vilket kanske eller kanske inte
    // är detsamma som eventuell inparameter
    // TODO Bestäm en regel för samma/olika objekt och följ konsekvent
    // TODO Kasta relevanta exceptions när en operation misslyckas

    // Ogiltiga fält
    DTOCustomer addCustomer() throws DatabaseConnectionException; // TODO Parametrar för nya fält - eller kundobjekt?

    // Ogiltiga fält
    DTOCustomer updateCustomer(DTOCustomer customer) throws DatabaseConnectionException; // TODO Parametrar för uppdaterade fält

    void deleteCustomer(int customerId) throws DatabaseConnectionException, NoSuchCustomerException;

    // Ogltiga fält
    DTOAccount openAccount(int customerId) throws DatabaseConnectionException, NoSuchCustomerException; // TODO Parametrar för nya fält

    void closeAccount(int accountId) throws DatabaseConnectionException, NoSuchCustomerException;

    // Positiv parameter -> insättning, negativ parameter -> uttag
    DTOAccount transact(int accountId, double netBalance) throws DatabaseConnectionException, NoSuchAccountException, InsufficientFundsException;

    // Ogiltig ränta
    DTOAccount setAccountInterestRate(int accountId, double newInterestRate) throws DatabaseConnectionException, NoSuchAccountException;

    // Ogiltiga fält
    DTOLoan approveLoan(int customerId) throws DatabaseConnectionException, NoSuchCustomerException; // TODO Parametrar för nya fält

    // Ogiltiga fält
    DTOLoan updateLoan(DTOLoan loan) throws DatabaseConnectionException, NoSuchLoanException; // TODO Parametrar för ändrade fält

    Collection<DTOCustomer> getCustomerData() throws DatabaseConnectionException;

    Collection<DTOAccount> getAccountData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException;

    Collection<DTOLoan> getLoanData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException;

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchAccountException; // Per kund? För alla?
}
