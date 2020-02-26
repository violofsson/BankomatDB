package se.nackademin.bankomatdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

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
        return String.format("Lån %d, %.2f vid %s, ränta %.2f%%, %.2f betalas %s",
                getLoanId(), getOriginalLoan(), grantedOn().format(DateTimeFormatter.ISO_DATE),
                getInterestRate(), getFinalPayment(), getPaymentDeadline().format(DateTimeFormatter.ISO_DATE));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTOLoan dtoLoan = (DTOLoan) o;
        return getLoanId() == dtoLoan.getLoanId() &&
                getDebtorId() == dtoLoan.getDebtorId() &&
                Double.compare(dtoLoan.getOriginalLoan(), getOriginalLoan()) == 0 &&
                Double.compare(dtoLoan.getInterestRate(), getInterestRate()) == 0 &&
                grantedOn().equals(dtoLoan.grantedOn()) &&
                getPaymentDeadline().equals(dtoLoan.getPaymentDeadline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId(), getDebtorId(), getOriginalLoan(), getInterestRate(), grantedOn(), getPaymentDeadline());
    }
}
