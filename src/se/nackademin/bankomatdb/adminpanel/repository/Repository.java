package se.nackademin.bankomatdb.adminpanel.repository;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.time.LocalDate;
import java.util.Collection;

// TODO
public interface Repository {
    // Obs: upserts returnerar skapat/uppdaterat objekt; för att vara konsekvent
    // rekommenderas att eventuell lagring ersätter det gamla objektet med det
    // nya på något lämpligt sätt
    // TODO Kasta relevanta exceptions när en operation misslyckas

    // Ogiltiga fält
    DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException;

    // Ogiltiga fält
    DTOCustomer updateCustomer(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException; // TODO Parametrar för uppdaterade fält

    boolean deleteCustomer(int customerId) throws DatabaseConnectionException;

    // Ogltiga fält
    DTOAccount openAccount(int customerId, double interestRate) throws DatabaseConnectionException, NoSuchCustomerException;

    boolean closeAccount(int accountId) throws DatabaseConnectionException;

    DTOAccount deposit(int accountId, double amount) throws DatabaseConnectionException, NoSuchAccountException;

    DTOAccount withdraw(int accountId, double amount) throws DatabaseConnectionException, NoSuchAccountException, InsufficientFundsException;

    // Ogiltig ränta
    DTOAccount setAccountInterestRate(int accountId, double newInterestRate) throws DatabaseConnectionException, NoSuchAccountException;

    // Ogiltiga fält
    DTOLoan approveLoan(int customerId, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchCustomerException; // TODO Parametrar för nya fält

    // Ogiltiga fält
    DTOLoan updateLoan(DTOLoan loan) throws DatabaseConnectionException, NoSuchLoanException; // TODO Parametrar för ändrade fält

    Collection<DTOCustomer> getCustomerData() throws DatabaseConnectionException;

    Collection<DTOAccount> getAccountData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException;

    Collection<DTOLoan> getLoanData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException;

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchAccountException; // Per kund? För alla?
}
