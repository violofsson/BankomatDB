package se.nackademin.bankomatdb.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DTOTransaction {
    private final int id;
    private final int accountId;
    private final double netBalance;
    private final LocalDateTime transactionTime;

    public DTOTransaction(int id, int accountId, double netBalance, LocalDateTime transactionTime) {
        this.id = id;
        this.accountId = accountId;
        this.netBalance = netBalance;
        this.transactionTime = transactionTime;
    }

    public int getTransactionId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    // Plus för kredit, minus för debet
    public double getBalanceChange() {
        return netBalance;
    }

    public LocalDateTime getTransactionTime() {
        return LocalDateTime.from(transactionTime);
    }

    @Override
    public String toString() {
        return String.format("Transakton %d från konto %d vid %s, %s%.2f kr",
                id, accountId, transactionTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                (netBalance >= 0 ? "+" : ""), netBalance);
    }
}
