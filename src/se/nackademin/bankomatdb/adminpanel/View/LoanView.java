package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Triplet;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class LoanView extends JFrame {
    private Controller controller;
    private DTOCustomer currentCustomer;
    private JComboBox<DTOLoan> loanSelector = new JComboBox<>();
    private JButton newLoanButton = new JButton("Bevilja nytt lån");
    private JButton updateLoanButton = new JButton("Uppdatera valt lån");

    LoanView(Controller c, DTOCustomer customer) {
        this.controller = c;
        this.currentCustomer = customer;
        setLayout(this.getContentPane());
        addActionListeners();
        this.setTitle("Lånehantering");
        this.setSize(410, 215);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(newLoanButton);
        container.add(loanSelector);
        container.add(updateLoanButton);
    }

    public void addActionListeners() {
        newLoanButton.addActionListener(ae -> {
            try {
                ApproveLoanDialog dialog = new ApproveLoanDialog(this, currentCustomer.getCustomerId());
                Triplet<Double, Double, LocalDate> input = dialog.run();
                controller.approveLoan(currentCustomer, input.getValue0(), input.getValue1(), input.getValue2());
            } catch (Exception e) {
                // TODO
            }
        });

        updateLoanButton.addActionListener(ae -> {
            try {
                DTOLoan currentLoan = getSelectedLoan();
                UpdateLoanDialog dialog = new UpdateLoanDialog(this, currentLoan);
                DTOLoan updatedLoan = dialog.run();
                if (!currentLoan.equals(updatedLoan)) {
                    controller.updateLoan(currentLoan, updatedLoan.getInterestRate(), updatedLoan.getPaymentDeadline());
                }
            } catch (Exception e) {
                // TODO
            }
        });
    }

    private DTOLoan getSelectedLoan() {
        return loanSelector.getItemAt(loanSelector.getSelectedIndex());
    }
}

