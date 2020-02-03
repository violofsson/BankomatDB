package se.nackademin.bankomatdb.atm.model;

import java.time.LocalDate;

// TODO
public class DTOLoan {
    // Hur ska låntagaren lagras? Behöver hen lagras alls när vi bara hanterar en kund i taget?
    // Ska vi hålla reda på lånets originalbelopp? Kanske i adminpanelen?

    public int getLoanId() {
        return 0;
    }

    public double getCurrentBalance() {
        return 0;
    }

    public double getInterestRate() {
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
