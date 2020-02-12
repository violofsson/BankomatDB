package se.nackademin.bankomatdb.model;

// TODO
public final class DTOAccount {
    private final int id;
    private final int ownerId;
    private final double balance;
    private final double interestRate;

    public DTOAccount(int id, int ownerId, double balance, double interestRate) {
        this.id = id;
        this.ownerId = ownerId;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public int getAccountId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public double getBalance() {
        return balance;
    }

    // Procentsats eller multiplikationsfaktor?
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return String.format("Kontonummer %d, räntesats %f%%, saldo %.2f", id, interestRate, balance);
    }
}
