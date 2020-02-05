package se.nackademin.bankomatdb.model;

import java.time.LocalDateTime;

// TODO
public final class DTOTransaction {
    // Ska konto och ev kund hanteras som lagrat id eller konstruktorparameterobjekt?
    public int getTransactionId() {
        return 0;
    }

    // Plus för kredit, minus för debet
    public double getBalanceChange() {
        return 0;
    }

    public LocalDateTime getTransactionTime() {
        return null;
    }
}
