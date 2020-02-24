package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class StartView extends JFrame {
    private Controller controller;
    private AccountView accountPanel;
    private LoanView loanPanel;
    private JButton addCustomer = new JButton("Ny kund");
    private JButton removeCustomer = new JButton("Ta bort vald kund");
    private JComboBox<DTOCustomer> customerSelect = new JComboBox<>();
    private JButton reloadButton = new JButton("Ladda om kunddata");
    private JButton handleAccounts = new JButton("Hantera den valda kundens konton");
    private JButton handleLoans = new JButton("Hantera den valda kundens lån");

    StartView(Controller c) throws DatabaseConnectionException {
        this.controller = c;
        this.accountPanel = new AccountView(c);
        this.loanPanel = new LoanView(c);
        setLayout(this.getContentPane());
        addActionListeners();
        this.reloadCustomerSelector();
        this.setTitle("Bankombud");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    void reloadCustomerSelector() throws DatabaseConnectionException {
        Collection<DTOCustomer> customers = controller.getCustomers();
        customerSelect.removeAllItems();
        customers.forEach(customerSelect::addItem);
    }

    DTOCustomer getSelectedCustomer() {
        return customerSelect.getItemAt(customerSelect.getSelectedIndex());
    }

    public void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(customerSelect);
        container.add(addCustomer);
        container.add(removeCustomer);
        container.add(reloadButton);
        container.add(accountPanel);
        container.add(loanPanel);
    }

    public void addActionListeners() {
        customerSelect.addActionListener(ae -> {
            accountPanel.reloadAccounts(getSelectedCustomer());
            loanPanel.reloadLoans(getSelectedCustomer());
        });

        reloadButton.addActionListener(ae -> {
            try {
                reloadCustomerSelector();
            } catch (DatabaseConnectionException e) {
                e.printStackTrace();
            }
        });
    }
}
