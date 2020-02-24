package se.nackademin.bankomatdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DTOLoan {
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
        return originalLoan * Math.pow(1 + (interestRate / 100), ChronoUnit.MONTHS.between(granted, deadline));
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
        return new DTOLoan(
                this.getLoanId(),
                this.getDebtorId(),
                this.getOriginalLoan(),
                newInterestRate,
                this.grantedOn(),
                newDeadline);
    }

    @Override
    public String toString() {
        return String.format("Lån %d, lånade %.2f %s, %.2f ränta, %.2f betalas %s",
                id, originalLoan, granted.format(DateTimeFormatter.ISO_DATE),
                interestRate, getFinalPayment(), deadline.format(DateTimeFormatter.ISO_DATE));
    }
}
