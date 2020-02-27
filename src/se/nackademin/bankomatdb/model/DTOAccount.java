package se.nackademin.bankomatdb.model;

import java.util.Objects;

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
        return String.format("Konto %d, %.2f%%, %.2f kr", getAccountId(), getInterestRate(), getBalance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTOAccount that = (DTOAccount) o;
        return getAccountId() == that.getAccountId() &&
                getOwnerId() == that.getOwnerId() &&
                Double.compare(that.getBalance(), getBalance()) == 0 &&
                Double.compare(that.getInterestRate(), getInterestRate()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getOwnerId(), getBalance(), getInterestRate());
    }
}
