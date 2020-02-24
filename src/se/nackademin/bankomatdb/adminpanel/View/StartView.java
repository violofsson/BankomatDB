package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame {
    private Controller controller;
    private CustomerView customerPanel;
    private AccountView accountPanel;
    private LoanView loanPanel;

    StartView(Controller c) throws DatabaseConnectionException {
        this.controller = c;
        this.customerPanel = new CustomerView(this, c);
        this.accountPanel = new AccountView(this, c);
        this.loanPanel = new LoanView(this, c);
        setLayout(this.getContentPane());
        addActionListeners();
        this.setTitle("Bankombud");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public void setLayout(Container container) {
        container.setLayout(new GridLayout(0, 1));
        container.add(customerPanel);
        container.add(accountPanel);
        container.add(loanPanel);
    }

    public void addActionListeners() {
        customerPanel.customerSelect.addActionListener(ae -> {
            accountPanel.reloadAccounts(customerPanel.getSelectedCustomer());
            loanPanel.reloadLoans(customerPanel.getSelectedCustomer());
        });
    }
}
