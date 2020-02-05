package se.nackademin.bankomatdb.atm.repository;

import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.util.List;

// Tanken är att bankomaten hämtar ett kundobjekt vid inloggning och återanvänder det
// TODO Ange eventuella exceptions
public interface ATMRepository {
    // Inga konton, kunden finns inte, kunde inte nå databasen
    List<DTOAccount> getCustomerAccounts(DTOCustomer customer);

    // Inga lån, kunden finns inte, kunde inte nå databasen
    List<DTOLoan> getCustomerLoans(DTOCustomer customer);

    // Inga transaktioner, kontot finns inte, kunde inte nå databasen
    List<DTOTransaction> getTransactionHistory(DTOAccount account);

    // Kan behöva ses över senare. Vad används för identifikation? Är strängar det rätta valet?
    // Felaktiga uppgifter, kunde inte nå databasen
    DTOCustomer login(String identification, String pin);

    // Returnerar true oom uttaget lyckas
    // Kontanterna lär lagras som flyttal i databasen, men uttag är alltid i hela kronor
    // Kontot finns inte, för lite pengar, kunde inte nå databasen
    // TODO Returnera kontoobjekt istället?
    boolean withdraw(DTOAccount account, int amount);
}
