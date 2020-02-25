package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

// TODO
public class ApproveLoanDialog extends JDialog {
    int debtorId;
    Triplet<Double, Double, LocalDate> input;
    JTextField loanedAmountField = new JTextField();
    JTextField interestRateField = new JTextField();
    JTextField deadlineField = new JTextField();
    JButton confirmButton = new JButton("Bevilja");
    JButton cancelButton = new JButton("Avbryt");

    ApproveLoanDialog(JFrame parent, int debtorId) {
        super(parent, "Bevilja lån", true);
        this.debtorId = debtorId;
        setLayout(this.getContentPane());
        this.setLocationRelativeTo(parent);
        this.pack();
    }

    Triplet<Double, Double, LocalDate> run() {
        this.setVisible(true);
        return input;
    }

    void setActionListeners() {
        confirmButton.addActionListener(ae -> {
            try {
                input = new Triplet<>(
                        Double.parseDouble(loanedAmountField.getText()),
                        Double.parseDouble(interestRateField.getText()),
                        LocalDate.parse(deadlineField.getText()));
                dispose();
            } catch (NumberFormatException e) {
                // TODO Meddela felaktig inmatning
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
