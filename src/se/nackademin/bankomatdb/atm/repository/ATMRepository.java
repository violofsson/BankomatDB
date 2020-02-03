package se.nackademin.bankomatdb.atm.repository;

import se.nackademin.bankomatdb.atm.model.DTOAccount;
import se.nackademin.bankomatdb.atm.model.DTOCustomer;
import se.nackademin.bankomatdb.atm.model.DTOLoan;

import java.util.List;

// Tanken är att bankomaten hämtar ett kundobjekt vid inloggning och återanvänder det
public interface ATMRepository {
    List<DTOAccount> getCustomerAccounts(DTOCustomer customer);

    List<DTOLoan> getCustomerLoans(DTOCustomer customer);

    // Kan behöva ses över senare. Vad används för identifikation? Är strängar det rätta valet?
    DTOCustomer login(String identification, String pin);

    // Returnerar true oom uttaget lyckas
    // Kontanterna lär lagras som flyttal i databasen, men uttag är alltid i hela kronor
    boolean withdraw(DTOCustomer customer, int amount);
}
