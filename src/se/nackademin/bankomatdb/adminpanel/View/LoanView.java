package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Triplet;
import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InvalidInsertException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.View.dialog.ApproveLoanDialog;
import se.nackademin.bankomatdb.adminpanel.View.dialog.UpdateLoanDialog;
import se.nackademin.bankomatdb.adminpanel.View.dialog.UtilityDialogs;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.IllegalFormatException;

public class LoanView extends JPanel {
    private JFrame parentFrame;
    private Controller controller;
    private DTOCustomer currentCustomer;
    private JComboBox<DTOLoan> loanSelector = new JComboBox<>();
    private JButton newLoanButton = new JButton("Bevilja nytt lån");
    private JButton updateLoanButton = new JButton("Uppdatera valt lån");

    LoanView(JFrame parentFrame, Controller controller) {
        this.parentFrame = parentFrame;
        this.controller = controller;
        setLayout(this);
        addActionListeners();
    }

    void addActionListeners() {
        newLoanButton.addActionListener(ae -> this.approveLoan());
        updateLoanButton.addActionListener(ae -> this.updateLoan());
    }

    void approveLoan() {
        try {
            ApproveLoanDialog dialog = new ApproveLoanDialog(parentFrame);
            Triplet<Double, Double, LocalDate> input = dialog.run();
            if (input == null) return;
            controller.approveLoan(currentCustomer, input.getValue0(), input.getValue1(), input.getValue2());
            reloadLoans(currentCustomer);
        } catch (NullPointerException | NumberFormatException | DateTimeParseException | IllegalFormatException | InvalidInsertException e) {
            UtilityDialogs.reportInvalidInput(this, "Felaktigt format på ett eller flera fält.");
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(this, "Misslyckades med att ge lån: låntagaren finns inte.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    private DTOLoan getSelectedLoan() {
        return loanSelector.getItemAt(loanSelector.getSelectedIndex());
    }

    void reloadLoans(DTOCustomer customer) {
        this.currentCustomer = customer;
        loanSelector.removeAllItems();
        loanSelector.setEnabled(false);
        newLoanButton.setEnabled(false);
        updateLoanButton.setEnabled(false);
        if (currentCustomer == null) return;
        try {
            Collection<DTOLoan> loans = controller.getCustomerLoans(currentCustomer);
            newLoanButton.setEnabled(true);
            if (!loans.isEmpty()) {
                loans.forEach(loanSelector::addItem);
                loanSelector.setEnabled(true);
                updateLoanButton.setEnabled(true);
            }
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    void setLayout(Container container) {
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(4);
        container.setLayout(layout);
        container.add(new JLabel("Välj lån:"));
        container.add(loanSelector);
        container.add(newLoanButton);
        container.add(updateLoanButton);
    }

    void updateLoan() {
        try {
            DTOLoan currentLoan = getSelectedLoan();
            UpdateLoanDialog dialog = new UpdateLoanDialog(parentFrame, currentLoan);
            DTOLoan updatedLoan = dialog.run();
            if (!currentLoan.equals(updatedLoan)) {
                updatedLoan = controller.updateLoan(currentLoan, updatedLoan.getInterestRate(), updatedLoan.getPaymentDeadline());
                int selectedIndex = loanSelector.getSelectedIndex();
                loanSelector.removeItemAt(selectedIndex);
                loanSelector.insertItemAt(updatedLoan, selectedIndex);
            }
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(this, "Misslyckades med att uppdatera lån: lånet finns inte.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }
}

