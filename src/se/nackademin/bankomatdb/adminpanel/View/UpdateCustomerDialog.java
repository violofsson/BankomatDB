package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Pair;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;

public class UpdateCustomerDialog extends JDialog {
    private DTOCustomer originalCustomer;
    private Pair<String, String> newNameAndPin;
    private JTextField nameField = new JTextField();
    private JTextField pinField = new JTextField();
    private JButton confirmButton = new JButton("Bekräfta");
    private JButton resetButton = new JButton("Avbryt");

    UpdateCustomerDialog(JFrame parent, DTOCustomer customer) {
        super(parent, "Uppdatera kunduppgifter", true);
        this.originalCustomer = customer;
        newNameAndPin = null;
        resetFields();
        setLayout(this.getContentPane());
        addActionListeners();
        setLocationRelativeTo(parent);
        this.pack();
    }

    void resetFields() {
        this.nameField.setText(originalCustomer.getName());
        this.pinField.setText(String.valueOf(originalCustomer.getPin()));
    }

    Pair<String, String> run() {
        this.setVisible(true);
        return newNameAndPin;
    }

    void addActionListeners() {
        confirmButton.addActionListener(ae -> {
            try {
                newNameAndPin = Pair.with(nameField.getText(), pinField.getText());
                dispose();
            } catch (NullPointerException e) {
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
        container.add(new JLabel("Nytt namn"));
        container.add(nameField);
        container.add(new JLabel("Ny PIN"));
        container.add(pinField);
        container.add(confirmButton);
        container.add(resetButton);
    }
}
