package se.nackademin.bankomatdb.adminpanel.View.dialog;

import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;

public class UpdateLoanDialog extends JDialog {
    private DTOLoan originalLoan;
    private DTOLoan newLoan;
    private JTextField newDeadline = new JTextField();
    private JTextField newInterest = new JTextField();
    private JButton confirmButton = new JButton("Bekräfta ändring");
    private JButton resetButton = new JButton("Återställ");

    public UpdateLoanDialog(JFrame parent, DTOLoan loan) {
        super(parent, "Uppdatera lån", true);
        this.originalLoan = loan;
        this.newLoan = loan;
        resetFields();
        setLayout(this.getContentPane());
        setActionListeners();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        this.pack();
    }

    void resetFields() {
        this.newDeadline.setText(originalLoan.getPaymentDeadline().format(DateTimeFormatter.ISO_DATE));
        this.newInterest.setText(String.valueOf(originalLoan.getInterestRate()));
    }

    public DTOLoan run() {
        this.setVisible(true);
        return newLoan;
    }

    void setActionListeners() {
        confirmButton.addActionListener(ae -> {
            try {
                newLoan = originalLoan.updated(
                        Double.parseDouble(newInterest.getText()),
                        LocalDate.parse(newDeadline.getText(), DateTimeFormatter.ISO_DATE));
                dispose();
            } catch (NullPointerException | NumberFormatException | DateTimeParseException | IllegalFormatException e) {
                UtilityDialogs.reportInvalidInput(this, "Felaktig inmatning i ett eller flera fält.");
                dispose();
            }
        });
        resetButton.addActionListener(ae -> this.resetFields());
    }

    void setLayout(Container container) {
        container.setLayout(new GridLayout(0, 2));
        container.add(new JLabel("Ny betalplan"));
        container.add(newDeadline);
        container.add(new JLabel("Ny räntesats"));
        container.add(newInterest);
        container.add(confirmButton);
        container.add(resetButton);
    }
}
