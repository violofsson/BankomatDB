package se.nackademin.bankomatdb.model;

import java.time.LocalDate;

// TODO
public final class DTOLoan {
    // Hur ska låntagaren lagras? Behöver hen lagras alls eller ska klienten hantera det?

    public int getLoanId() {
        return 0;
    }

    public double getFinalPayment() {
        return 0;
    }

    public double getInterestRate() {
        return 0;
    }

    public double getOriginalLoan() {
        return 0;
    }

    // TODO Undersäk om vi bör hantera tidszoner eller ej
    public LocalDate getPaymentDeadline() {
        return null;
    }

    public LocalDate grantedOn() {
        return null;
    }
}
