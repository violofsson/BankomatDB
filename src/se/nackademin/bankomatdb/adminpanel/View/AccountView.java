package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class AccountView extends JPanel {
    private Controller controller;
    private DTOCustomer currentCustomer;
    private JComboBox<DTOAccount> accountSelect = new JComboBox<>();
    private JTextField transactionField = new JTextField();
    private JButton withdrawButton = new JButton("Ta ut");
    private JButton depositButton = new JButton("Sätt in");
    private JTextField interestRateField = new JTextField();
    private JButton interestRateButton = new JButton("Sätt ny ränta");
    private JButton transactionHistoryButton = new JButton("Visa transaktionshistorik");

    AccountView(Controller c) {
        super();
        this.controller = c;
        setLayout(this);
    }

    DTOAccount getSelectedAccount() {
        return accountSelect.getItemAt(accountSelect.getSelectedIndex());
    }

    void reloadAccounts(DTOCustomer customer) {
        this.currentCustomer = customer;
        accountSelect.removeAllItems();
        for (Component c : this.getComponents()) {
            c.setEnabled(false);
        }
        if (currentCustomer == null) return;
        try {
            Collection<DTOAccount> accounts = controller.getCustomerAccounts(currentCustomer);
            if (!accounts.isEmpty()) {
                accounts.forEach(accountSelect::addItem);
                for (Component c : this.getComponents()) {
                    c.setEnabled(true);
                }
            }
        } catch (NoSuchRecordException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }

    public void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(new JLabel("Välj konto"));
        container.add(accountSelect);
        container.add(new JLabel("Ta ut/sätt in"));
        container.add(new JLabel("Ny räntesats"));
        container.add(interestRateField);
        container.add(interestRateButton);
        container.add(transactionField);
        container.add(withdrawButton);
        container.add(depositButton);
        container.add(transactionHistoryButton);
    }

    public void addActionListeners() {

    }
}
