package se.nackademin.bankomatdb.atm.repository;

import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.util.List;

// Tanken är att bankomaten hämtar ett kundobjekt vid inloggning och återanvänder det
// TODO Ange eventuella exceptions
public interface ATMRepository {
    List<DTOAccount> getCustomerAccounts(DTOCustomer customer);

    List<DTOLoan> getCustomerLoans(DTOCustomer customer);

    List<DTOTransaction> getTransactionHistory(DTOAccount account);

    // Kan behöva ses över senare. Vad används för identifikation? Är strängar det rätta valet?
    DTOCustomer login(String identification, String pin);

    // Returnerar true oom uttaget lyckas
    // Kontanterna lär lagras som flyttal i databasen, men uttag är alltid i hela kronor
    // TODO Returnera kontoobjekt istället?
    boolean withdraw(DTOAccount account, int amount);
}
