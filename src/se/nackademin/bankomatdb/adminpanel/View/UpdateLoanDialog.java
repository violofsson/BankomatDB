package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

class UpdateLoanDialog extends JDialog {
    private DTOLoan originalLoan;
    private DTOLoan newLoan;
    private JLabel deadlineLabel = new JLabel("Ny betalplan");
    private JTextField newDeadline = new JTextField();
    private JLabel interestRateLabel = new JLabel("Ny räntesats");
    private JTextField newInterest = new JTextField();
    private JButton confirmButton = new JButton("Bekräfta ändring");
    private JButton resetButton = new JButton("Återställ");

    UpdateLoanDialog(Frame parent, DTOLoan loan) {
        super(parent, "Uppdatera lån", true);
        this.originalLoan = loan;
        this.newLoan = loan;
        resetFields();
        setLayout(this.getContentPane());
        setActionListeners();
    }

    void resetFields() {
        this.newDeadline.setText(originalLoan.getPaymentDeadline().toString());
        this.newInterest.setText(String.valueOf(originalLoan.getInterestRate()));
    }

    DTOLoan run() {
        this.setVisible(true);
        return newLoan;
    }

    void setActionListeners() {
        confirmButton.addActionListener(ae -> {
            try {
                newLoan = originalLoan.updated(
                        Double.parseDouble(newInterest.getText()),
                        LocalDate.parse(newDeadline.getText()));
                dispose();
            } catch (Exception e) {
                // TODO Meddela felaktig inmatning
            }
        });
        resetButton.addActionListener(this::actionPerformed);
    }

    void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(deadlineLabel);
        container.add(newDeadline);
        container.add(interestRateLabel);
        container.add(newInterest);
        container.add(confirmButton);
        container.add(resetButton);
    }

    private void actionPerformed(ActionEvent ae) {
        resetFields();
    }
}
