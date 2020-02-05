package se.nackademin.bankomatdb.model;

import java.time.LocalDateTime;

// TODO
public final class DTOTransaction {
    // Ska konto och ev kund hanteras som lagrat id eller konstruktorparameterobjekt?
    private final int id;
    private final double netBalance;
    private final LocalDateTime transactionTime;

    public DTOTransaction(int id, double netBalance, LocalDateTime transactionTime) {
        this.id = id;
        this.netBalance = netBalance;
        this.transactionTime = transactionTime;
    }

    public int getTransactionId() {
        return id;
    }

    // Plus för kredit, minus för debet
    public double getBalanceChange() {
        return netBalance;
    }

    public LocalDateTime getTransactionTime() {
        return LocalDateTime.from(transactionTime);
    }
}
