package se.nackademin.bankomatdb.adminpanel.View.dialog;

import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;

public class NewCustomerDialog extends JDialog {
    Triplet<String, String, String> nameIdPin = null;
    JTextField nameField = new JTextField();
    JTextField personalIdField = new JTextField();
    JTextField pinField = new JTextField();
    JButton confirmButton = new JButton("Lägg till");
    JButton cancelButton = new JButton("Avbryt");

    public NewCustomerDialog(JFrame parent) {
        super(parent, "Ny kund", true);
        setLayout(this.getContentPane());
        addActionListeners();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    void addActionListeners() {
        confirmButton.addActionListener(ae -> {
            nameIdPin = Triplet.with(nameField.getText().trim(),
                    personalIdField.getText().trim(),
                    pinField.getText().trim());
            dispose();
        });
        cancelButton.addActionListener(ae -> dispose());
    }

    public Triplet<String, String, String> getNameIdPin() {
        this.setVisible(true);
        return nameIdPin;
    }

    void setLayout(Container container) {
        container.setLayout(new GridLayout(0, 2));
        container.add(new JLabel("Namn"));
        container.add(nameField);
        container.add(new JLabel("Personnummer"));
        container.add(personalIdField);
        container.add(new JLabel("PIN"));
        container.add(pinField);
        container.add(confirmButton);
        container.add(cancelButton);
    }
}
