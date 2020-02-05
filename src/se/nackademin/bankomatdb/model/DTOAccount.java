package se.nackademin.bankomatdb.model;

// TODO
public final class DTOAccount {
    private final int id;
    private final double balance;
    private final double interestRate;

    public DTOAccount(int id, double balance, double interestRate) {
        this.id = id;
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
