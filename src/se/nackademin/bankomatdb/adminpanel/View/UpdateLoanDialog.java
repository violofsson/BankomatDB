package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class UpdateLoanDialog extends JDialog {
    private DTOLoan originalLoan;
    private DTOLoan newLoan;
    private JTextField newDeadline = new JTextField();
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
        this.newDeadline.setText(originalLoan.getPaymentDeadline().format(DateTimeFormatter.BASIC_ISO_DATE));
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
        resetButton.addActionListener(ae -> this.resetFields());
    }

    void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(new JLabel("Ny betalplan"));
        container.add(newDeadline);
        container.add(new JLabel("Ny räntesats"));
        container.add(newInterest);
        container.add(confirmButton);
        container.add(resetButton);
    }
}
