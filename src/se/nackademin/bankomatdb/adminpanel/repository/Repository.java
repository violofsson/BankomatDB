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

    DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException, InvalidInsertException;

    // Ogiltiga fält
    DTOCustomer updateCustomer(DTOCustomer customer, String newName, String newPin) throws DatabaseConnectionException, NoSuchRecordException; // TODO Parametrar för uppdaterade fält

    boolean deleteCustomer(int customerId) throws DatabaseConnectionException, NoSuchRecordException;

    DTOAccount openAccount(int customerId, double initialBalance, double interestRate) throws DatabaseConnectionException, NoSuchRecordException, InvalidInsertException;

    boolean closeAccount(int accountId) throws DatabaseConnectionException, NoSuchRecordException;

    DTOAccount deposit(int accountId, double amount) throws DatabaseConnectionException, NoSuchRecordException;

    DTOAccount withdraw(int accountId, double amount) throws DatabaseConnectionException, NoSuchRecordException, InsufficientFundsException;

    // Ogiltig ränta
    DTOAccount setAccountInterestRate(int accountId, double newInterestRate) throws DatabaseConnectionException, NoSuchRecordException;

    DTOLoan approveLoan(int customerId, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchRecordException, InvalidInsertException; // TODO Parametrar för nya fält

    // Ogiltiga fält
    DTOLoan updateLoan(DTOLoan loan, double newInterestRate, LocalDate newDeadline) throws DatabaseConnectionException, NoSuchRecordException; // TODO Parametrar för ändrade fält

    Collection<DTOCustomer> getCustomerData() throws DatabaseConnectionException;

    Collection<DTOAccount> getAccountData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException;

    Collection<DTOLoan> getLoanData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException;

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchRecordException; // Per kund? För alla?
}
