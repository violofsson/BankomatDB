package se.nackademin.bankomatdb.adminpanel.repository;

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

    // Ogiltiga fält, kunde inte nå databasen
    DTOCustomer addCustomer(); // TODO Parametrar för nya fält - eller kundobjekt?

    // Ogiltiga fält, kunde inte nå databasen
    DTOCustomer updateCustomer(DTOCustomer customer); // TODO Parametrar för uppdaterade fält

    // Kunden finns inte, kunde inte nå databasen
    void deleteCustomer(DTOCustomer customer);

    // Ogltiga fält, kunden finns inte, kunde inte nå databasen
    DTOAccount openAccount(DTOCustomer customer); // TODO Parametrar för nya fält

    // Kontot finns inte, kunde inte nå databasen
    void closeAccount(DTOAccount account);

    // Positiv parameter -> insättning, negativ parameter -> uttag
    // För lite pengar, kontot finns inte, kunde inte nå databasen
    DTOAccount transact(DTOAccount account, double netBalance);

    // Ogiltig ränta, kontot finns inte, kunde inte nå databasen
    DTOAccount setAccountInterestRate(DTOAccount account, double newInterestRate);

    // Ogiltiga fält, kunden finns inte, kunde inte nå databasen
    DTOLoan approveLoan(); // TODO Parametrar för nya fält

    // Ogiltiga fält, lånet finns inte, kunde inte nå databasen
    DTOLoan updateLoan(DTOLoan loan); // TODO Parametrar för ändrade fält

    // Inga kunder, kunde inte nå databasen
    Collection<DTOCustomer> getCustomerData();

    // Inga konton, kunden finns inte, kunde inte nå databasen
    Collection<DTOAccount> getAccountData(DTOCustomer customer);

    // Inga lån, kunden finns inte, kunde inte nå databasen
    Collection<DTOLoan> getLoanData(DTOCustomer customer);

    // Inga transaktioner, kontot finns inte, kunde inte nå databasen
    Collection<DTOTransaction> getTransactionHistory(DTOAccount account); // Per kund? För alla?
}
