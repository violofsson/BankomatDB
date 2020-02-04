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

    DTOCustomer addCustomer(); // TODO Parametrar för nya fält - eller kundobjekt?

    DTOCustomer updateCustomer(DTOCustomer customer); // TODO Parametrar för uppdaterade fält

    boolean deleteCustomer(DTOCustomer customer);

    DTOAccount openAccount(DTOCustomer customer); // TODO Parametrar för nya fält

    boolean closeAccount(DTOAccount account);

    // Positiv parameter -> insättning, negativ parameter -> uttag
    DTOAccount transact(DTOAccount account, double netBalance);

    DTOAccount setAccountInterestRate(DTOAccount account, double newInterestRate);

    DTOLoan approveLoan(); // TODO Parametrar för nya fält

    DTOLoan updateLoan(DTOLoan loan); // TODO Parametrar för ändrade fält

    Collection<DTOCustomer> getCustomerData();

    Collection<DTOAccount> getAccountData(DTOCustomer customer);

    Collection<DTOLoan> getLoanData(DTOCustomer customer);

    Collection<DTOTransaction> getTransactionHistory(DTOAccount account); // Per kund? För alla?
}
