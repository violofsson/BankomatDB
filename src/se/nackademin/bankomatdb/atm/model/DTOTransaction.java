package se.nackademin.bankomatdb.atm.model;

// TODO
public class DTOTransaction {
    // Ska konto och ev kund hanteras som lagrat id eller konstruktorparameterobjekt?
    public int getTransactionId() {
        return 0;
    }

    // Plus för kredit, minus för debet
    public double getBalanceChange() {
        return 0;
    }
}
