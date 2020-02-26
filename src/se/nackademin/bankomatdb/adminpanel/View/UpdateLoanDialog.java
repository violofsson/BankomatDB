package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.model.DTOLoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatException;

class UpdateLoanDialog extends JDialog {
    private DTOLoan originalLoan;
    private DTOLoan newLoan;
    private JTextField newDeadline = new JTextField();
    private JTextField newInterest = new JTextField();
    private JButton confirmButton = new JButton("Bekräfta ändring");
    private JButton resetButton = new JButton("Återställ");

    UpdateLoanDialog(JFrame parent, DTOLoan loan) {
        super(parent, "Uppdatera lån", true);
        this.originalLoan = loan;
        this.newLoan = loan;
        resetFields();
        setLayout(this.getContentPane());
        setActionListeners();
        setLocationRelativeTo(parent);
        this.pack();
    }

    void resetFields() {
        this.newDeadline.setText(originalLoan.getPaymentDeadline().format(DateTimeFormatter.ISO_DATE));
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
                        LocalDate.parse(newDeadline.getText(), DateTimeFormatter.ISO_DATE));
                dispose();
            } catch (NullPointerException | NumberFormatException | IllegalFormatException e) {
                JOptionPane.showMessageDialog(null, "Felaktigt format, försök igen.",
                        "", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                // TODO Meddela felaktig inmatning
                e.printStackTrace();
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
