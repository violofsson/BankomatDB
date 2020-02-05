package se.nackademin.bankomatdb.model;

import java.time.LocalDate;

// TODO
public final class DTOLoan {
    // Hur ska låntagaren lagras? Behöver hen lagras alls eller ska klienten hantera det?
    private final int id;
    private final int debtorId;
    private final double originalLoan;
    private final double interestRate;
    private final LocalDate granted;
    private final LocalDate deadline;

    public DTOLoan(int id, int debtorId, double originalLoan, double interestRate, LocalDate granted, LocalDate deadline) {
        this.id = id;
        this.debtorId = debtorId;
        this.originalLoan = originalLoan;
        this.interestRate = interestRate;
        this.granted = granted;
        this.deadline = deadline;
    }

    public int getLoanId() {
        return id;
    }

    public int getDebtorId() {
        return debtorId;
    }

    public double getFinalPayment() {
        return 0;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getOriginalLoan() {
        return originalLoan;
    }

    // TODO Undersäk om vi bör hantera tidszoner eller ej
    public LocalDate getPaymentDeadline() {
        return LocalDate.from(deadline);
    }

    public LocalDate grantedOn() {
        return LocalDate.from(granted);
    }

    public DTOLoan updated(double newInterestRate, LocalDate newDeadline) {
        return new DTOLoan(this.id, this.debtorId, this.originalLoan, newInterestRate, this.granted, newDeadline);
    }
}
