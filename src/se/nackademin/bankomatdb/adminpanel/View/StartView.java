package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame {
    private CustomerView customerPanel;
    private AccountView accountPanel;
    private LoanView loanPanel;

    StartView(Controller c) {
        this.customerPanel = new CustomerView(this, c);
        this.accountPanel = new AccountView(this, c);
        this.loanPanel = new LoanView(this, c);
        setLayout(this.getContentPane());
        addActionListeners();
        customerPanel.reloadCustomers();
        this.setTitle("Bankombud");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    void setLayout(Container container) {
        BorderLayout layout = new BorderLayout();
        layout.setVgap(8);
        container.setLayout(layout);
        container.add(customerPanel, BorderLayout.PAGE_START);
        container.add(accountPanel, BorderLayout.CENTER);
        container.add(loanPanel, BorderLayout.PAGE_END);
    }

    DTOCustomer getCurrentCustomer() {
        return customerPanel.getSelectedCustomer();
    }

    void addActionListeners() {
        customerPanel.customerSelect.addActionListener(ae -> {
            accountPanel.reloadAccounts(getCurrentCustomer());
            loanPanel.reloadLoans(getCurrentCustomer());
        });
    }
}
