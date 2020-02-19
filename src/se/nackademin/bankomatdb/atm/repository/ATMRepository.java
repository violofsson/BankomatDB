package se.nackademin.bankomatdb.atm.repository;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.util.List;

// Tanken är att bankomaten hämtar ett kundobjekt vid inloggning och återanvänder det
// TODO Kontrollera exceptions
public interface ATMRepository {
    List<DTOAccount> getCustomerAccounts(int customerId) throws DatabaseConnectionException;

    List<DTOLoan> getCustomerLoans(int customerId) throws DatabaseConnectionException;

    List<DTOTransaction> getTransactionHistory(int accountId) throws DatabaseConnectionException, NoSuchRecordException;

    // Kan behöva ses över senare. Vad används för identifikation? Är strängar det rätta valet?
    DTOCustomer login(String identification, String pin) throws DatabaseConnectionException, InvalidCredentialsException;

    // Returnerar true oom uttaget lyckas
    // Kontanterna lär lagras som flyttal i databasen, men uttag är alltid i hela kronor
    // TODO Returnera kontoobjekt istället?
    boolean withdraw(int accountId, int amount) throws DatabaseConnectionException, InsufficientFundsException, NoSuchRecordException;
}
