package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

public class AccountView extends JPanel {
    private JFrame parentFrame;
    private Controller controller;
    private DTOCustomer currentCustomer;
    private JComboBox<DTOAccount> accountSelect = new JComboBox<>();
    private JButton withdrawButton = new JButton("Ta ut");
    private JButton depositButton = new JButton("Sätt in");
    private JTextField interestRateField = new JTextField();
    private JButton interestRateButton = new JButton("Sätt ny ränta");
    private JButton transactionHistoryButton = new JButton("Visa transaktionshistorik");
    private JButton closeAccountButton = new JButton("Stäng konto");

    AccountView(JFrame parent, Controller c) {
        super();
        this.parentFrame = parent;
        this.controller = c;
        addActionListeners();
        setLayout(this);
    }

    DTOAccount getSelectedAccount() {
        return accountSelect.getItemAt(accountSelect.getSelectedIndex());
    }

    void printTransactions() {
        try {
            controller.getAccountTransactions(getSelectedAccount(), LocalDate.now().minus(1, ChronoUnit.MONTHS)).forEach(t -> System.out.println(t.toString()));
        } catch (DatabaseConnectionException | NoSuchRecordException e) {
            e.printStackTrace();
        }
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
        container.add(new JLabel("Ny räntesats"));
        container.add(interestRateField);
        container.add(interestRateButton);
        container.add(withdrawButton);
        container.add(depositButton);
        container.add(transactionHistoryButton);
        container.add(closeAccountButton);
    }

    void closeAccount() {
        int response = JOptionPane.showConfirmDialog(parentFrame,
                "Är du säker på att du vill ta bort kontot?",
                "Bekräfta borttagning",
                JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            try {
                controller.closeAccount(getSelectedAccount());
            } catch (NoSuchRecordException e) {
                e.printStackTrace();
            } catch (DatabaseConnectionException e) {
                e.printStackTrace();
            }
        }
    }

    void deposit() {
        try {
            double input = Double.parseDouble(JOptionPane.showInputDialog("Belopp att ta ut:"));
            controller.deposit(getSelectedAccount(), input);
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }

    void withdraw() {
        try {
            double input = Double.parseDouble(JOptionPane.showInputDialog("Belopp att ta ut:"));
            controller.withdraw(getSelectedAccount(), input);
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
    }

    public void addActionListeners() {
        depositButton.addActionListener(ae -> deposit());
        withdrawButton.addActionListener(ae -> withdraw());
        transactionHistoryButton.addActionListener(ae -> printTransactions());
        closeAccountButton.addActionListener(ae -> closeAccount());
    }
}
