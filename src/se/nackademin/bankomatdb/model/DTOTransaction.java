package se.nackademin.bankomatdb.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
                getTransactionId(), getAccountId(), getTransactionTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                (getBalanceChange() >= 0 ? "+" : ""), getBalanceChange());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTOTransaction that = (DTOTransaction) o;
        return getTransactionId() == that.getTransactionId() &&
                getAccountId() == that.getAccountId() &&
                Double.compare(that.getBalanceChange(), getBalanceChange()) == 0 &&
                getTransactionTime().equals(that.getTransactionTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getAccountId(), getBalanceChange(), getTransactionTime());
    }
}
