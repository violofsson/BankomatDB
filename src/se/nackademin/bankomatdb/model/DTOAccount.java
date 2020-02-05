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

    public double getBalance() {
        return balance;
    }

    // Procentsats eller multiplikationsfaktor?
    public double getInterestRate() {
        return interestRate;
    }
}
