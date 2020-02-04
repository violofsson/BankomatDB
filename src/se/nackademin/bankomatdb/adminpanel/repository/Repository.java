package se.nackademin.bankomatdb.adminpanel.repository;

import se.nackademin.bankomatdb.adminpanel.model.DTOAccount;
import se.nackademin.bankomatdb.adminpanel.model.DTOCustomer;
import se.nackademin.bankomatdb.adminpanel.model.DTOLoan;
import se.nackademin.bankomatdb.adminpanel.model.DTOTransaction;

import java.util.Collection;

// TODO
public interface Repository {
    // TODO Ska upserts returnera nytt DTO-objekt eller bara meddela lyckad/misslyckad
    // operation och låte klienten läsa in objekten själv?
    DTOCustomer addCustomer();

    boolean deleteCustomer(DTOCustomer customer);

    DTOAccount openAccount(DTOCustomer customer);

    boolean closeAccount(DTOAccount account);

    boolean deposit(DTOAccount account, double amount);

    boolean withdraw(DTOAccount account, double amount);

    boolean setAccountInterestRate(DTOAccount account, double newInterestRate);

    void approveLoan();

    boolean updateLoan();

    Collection<DTOCustomer> getCustomerData();

    Collection<DTOAccount> getAccountData(DTOCustomer customer);

    Collection<DTOLoan> getLoanData(DTOCustomer customer);

    Collection<DTOTransaction> getTransactionHistory(DTOCustomer customer);
}
