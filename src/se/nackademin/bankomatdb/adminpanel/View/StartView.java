package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class StartView extends JFrame {
    private Controller controller;
    private JButton addCustomer = new JButton("Ny kund");
    private JButton removeCustomer = new JButton("Ta bort vald kund");
    private JComboBox<DTOCustomer> customerSelect = new JComboBox<>();
    private JButton handleAccounts = new JButton("Hantera den valda kundens konton");
    private JButton handleLoans = new JButton("Hantera den valda kundens lån");

    StartView(Controller c) {
        this.controller = c;
        setLayout(this.getContentPane());
        this.setTitle("Bankombud");
        this.setSize(410, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    void reloadCustomerSelector(Collection<DTOCustomer> customers) {
        customerSelect.removeAllItems();
        customers.forEach(customerSelect::addItem);
    }

    public void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(customerSelect);
        container.add(addCustomer);
        container.add(removeCustomer);
        container.add(handleAccounts);
        container.add(handleLoans);
    }

    public void addActionListeners() {

    }
}
