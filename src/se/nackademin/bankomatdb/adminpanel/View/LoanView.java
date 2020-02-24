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

public class LoanView extends JPanel {
    private Controller controller;
    private DTOCustomer currentCustomer;
    private JComboBox<DTOLoan> loanSelector = new JComboBox<>();
    private JButton newLoanButton = new JButton("Bevilja nytt lån");
    private JButton updateLoanButton = new JButton("Uppdatera valt lån");

    LoanView(Controller c) {
        this.controller = c;
        setLayout(this);
        addActionListeners();
        this.setVisible(true);
    }

    void reloadLoans(DTOCustomer customer) {
        this.currentCustomer = customer;
        loanSelector.removeAllItems();
        loanSelector.setEnabled(false);
        newLoanButton.setEnabled(false);
        updateLoanButton.setEnabled(false);
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

    public void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(newLoanButton);
        container.add(loanSelector);
        container.add(updateLoanButton);
    }

    public void addActionListeners() {
        newLoanButton.addActionListener(ae -> this.approveLoan());
        updateLoanButton.addActionListener(ae -> this.updateLoan());
    }

    void approveLoan() {
        try {
            ApproveLoanDialog dialog = new ApproveLoanDialog((Frame) SwingUtilities.getWindowAncestor(this), currentCustomer.getCustomerId());
            Triplet<Double, Double, LocalDate> input = dialog.run();
            controller.approveLoan(currentCustomer, input.getValue0(), input.getValue1(), input.getValue2());
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
            UpdateLoanDialog dialog = new UpdateLoanDialog((Frame) SwingUtilities.getWindowAncestor(this), currentLoan);
            DTOLoan updatedLoan = dialog.run();
            if (!currentLoan.equals(updatedLoan)) {
                controller.updateLoan(currentLoan, updatedLoan.getInterestRate(), updatedLoan.getPaymentDeadline());
            }
        } catch (NoSuchRecordException | DatabaseConnectionException e) {
            // TODO
        }
    }
}

