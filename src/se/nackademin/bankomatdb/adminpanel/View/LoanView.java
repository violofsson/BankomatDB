package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Triplet;
import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InvalidInsertException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
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
        } catch (NoSuchRecordException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }

    void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(newLoanButton);
        container.add(loanSelector);
        container.add(updateLoanButton);
    }

    void addActionListeners() {
        newLoanButton.addActionListener(ae -> this.approveLoan());
        updateLoanButton.addActionListener(ae -> this.updateLoan());
    }

    void approveLoan() {
        try {
            ApproveLoanDialog dialog = new ApproveLoanDialog(parentFrame, currentCustomer.getCustomerId());
            Triplet<Double, Double, LocalDate> input = dialog.run();
            DTOLoan newLoan = controller.approveLoan(currentCustomer, input.getValue0(), input.getValue1(), input.getValue2());
            loanSelector.addItem(newLoan);
        } catch (NullPointerException | NumberFormatException | IllegalFormatException e) {
            // TODO
        } catch (InvalidInsertException | NoSuchRecordException | DatabaseConnectionException e) {
            // TODO
        }
    }

    private DTOLoan getSelectedLoan() {
        return loanSelector.getItemAt(loanSelector.getSelectedIndex());
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
        } catch (NoSuchRecordException | DatabaseConnectionException e) {
            // TODO
        }
    }
}

