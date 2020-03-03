package se.nackademin.bankomatdb.adminpanel.View.dialog;

import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;

public class ApproveLoanDialog extends JDialog {
    Triplet<Double, Double, LocalDate> input;
    JTextField loanedAmountField = new JTextField();
    JTextField interestRateField = new JTextField();
    JTextField deadlineField = new JTextField();
    JButton confirmButton = new JButton("Bevilja");
    JButton cancelButton = new JButton("Avbryt");

    public ApproveLoanDialog(JFrame parent) {
        super(parent, "Bevilja lån", true);
        setLayout(this.getContentPane());
        this.setActionListeners();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    public Triplet<Double, Double, LocalDate> run() {
        this.setVisible(true);
        return input;
    }

    void setActionListeners() {
        confirmButton.addActionListener(ae -> {
            try {
                input = Triplet.with(
                        Double.parseDouble(loanedAmountField.getText().replace(",", ".").strip()),
                        Double.parseDouble(interestRateField.getText().replace(",", ".").strip()),
                        LocalDate.parse(deadlineField.getText().strip(), DateTimeFormatter.ISO_DATE));
                dispose();
            } catch (NullPointerException | NumberFormatException | DateTimeParseException | IllegalFormatException e) {
                UtilityDialogs.reportInvalidInput(this, "Felaktig inmatning i ett eller flera fält.");
                dispose();
            }
        });

        cancelButton.addActionListener(ae -> {
            input = null;
            dispose();
        });
    }

    void setLayout(Container container) {
        container.setLayout(new GridLayout(0, 2));
        container.add(new JLabel("Lånebelopp"));
        container.add(loanedAmountField);
        container.add(new JLabel("Ränta"));
        container.add(interestRateField);
        container.add(new JLabel("Sista betalningsdag"));
        container.add(deadlineField);
        container.add(confirmButton);
        container.add(cancelButton);
    }
}
