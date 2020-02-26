package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;

public class NewCustomerDialog extends JDialog {
    Triplet<String, String, String> input;
    JTextField nameField = new JTextField();
    JTextField personalIdField = new JTextField();
    JTextField pinField = new JTextField();
    JButton confirmButton = new JButton("Lägg till");
    JButton cancelButton = new JButton("Avbryt");

    NewCustomerDialog(JFrame parent) {
        super(parent, "Ny kund", true);
        setLayout(this.getContentPane());
        setActionListeners();
        this.setLocationRelativeTo(parent);
        this.pack();
    }

    Triplet<String, String, String> run() {
        this.setVisible(true);
        return input;
    }

    void setActionListeners() {
        confirmButton.addActionListener(ae -> {
            try {
                input = Triplet.with(nameField.getText().trim(),
                        personalIdField.getText().trim(),
                        pinField.getText().trim());
                dispose();
            } catch (Exception e) {
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
