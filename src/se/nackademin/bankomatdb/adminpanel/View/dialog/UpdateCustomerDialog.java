package se.nackademin.bankomatdb.adminpanel.View.dialog;

import org.javatuples.Pair;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;

public class UpdateCustomerDialog extends JDialog {
    private DTOCustomer originalCustomer;
    private Pair<String, String> newNameAndPin = null;
    private JTextField nameField = new JTextField();
    private JTextField pinField = new JTextField();
    private JButton confirmButton = new JButton("Uppdatera");
    private JButton cancelButton = new JButton("Avbryt");

    public UpdateCustomerDialog(JFrame parent, DTOCustomer customer) {
        super(parent, "Uppdatera kunduppgifter", true);
        this.originalCustomer = customer;
        resetFields();
        setLayout(this.getContentPane());
        addActionListeners();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    void addActionListeners() {
        confirmButton.addActionListener(ae -> {
            newNameAndPin = Pair.with(nameField.getText(), pinField.getText());
            dispose();
        });
        cancelButton.addActionListener(ae -> resetFields());
    }

    public Pair<String, String> getNameAndPin() {
        this.setVisible(true);
        return newNameAndPin;
    }

    void resetFields() {
        this.nameField.setText(originalCustomer.getName());
        this.pinField.setText(String.valueOf(originalCustomer.getPin()));
    }

    void setLayout(Container container) {
        container.setLayout(new GridLayout(0, 2));
        container.add(new JLabel("Nytt namn"));
        container.add(nameField);
        container.add(new JLabel("Ny PIN"));
        container.add(pinField);
        container.add(confirmButton);
        container.add(cancelButton);
    }
}
