package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.InvalidInsertException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.View.dialog.UtilityDialogs;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;

public class AccountView extends JPanel {
    private JFrame parentFrame;
    private Controller controller;
    private DTOCustomer currentCustomer;
    private JComboBox<DTOAccount> accountSelect = new JComboBox<>();
    private JButton withdrawButton = new JButton("Ta ut");
    private JButton depositButton = new JButton("Sätt in");
    private JButton interestRateButton = new JButton("Sätt ny ränta");
    private JButton transactionHistoryButton = new JButton("Visa transaktionshistorik");
    private JButton openAccountButton = new JButton("Öppna nytt konto");
    private JButton closeAccountButton = new JButton("Stäng konto");

    AccountView(JFrame parent, Controller c) {
        super();
        this.parentFrame = parent;
        this.controller = c;
        addActionListeners();
        setLayout(this);
    }

    void addActionListeners() {
        interestRateButton.addActionListener(ae -> setInterestRate(getSelectedAccount()));
        depositButton.addActionListener(ae -> deposit(getSelectedAccount()));
        withdrawButton.addActionListener(ae -> withdraw(getSelectedAccount()));
        transactionHistoryButton.addActionListener(ae -> printTransactions(getSelectedAccount()));
        openAccountButton.addActionListener(ae -> openAccount(currentCustomer));
        closeAccountButton.addActionListener(ae -> closeAccount(getSelectedAccount()));
    }

    void closeAccount(DTOAccount account) {
        int response = JOptionPane.showConfirmDialog(parentFrame,
                "Är du säker på att du vill ta bort kontot? Det kan inte återställas.",
                "Bekräfta borttagning",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                controller.closeAccount(account);
                reloadAccounts(currentCustomer);
            } catch (NoSuchRecordException e) {
                UtilityDialogs.reportFailedOperation(this,
                        "Onödig operation: kontot är redan borttaget från databasen.\n" +
                                "Laddar om kontoinformation...");
                reloadAccounts(currentCustomer);
            } catch (DatabaseConnectionException e) {
                UtilityDialogs.reportConnectionError(this, e);
            }
        }
    }

    void deposit(DTOAccount account) {
        try {
            String rawInput = JOptionPane.showInputDialog("Belopp att ta ut (saldo " +
                    account.getBalance() + " kr):");
            if (rawInput == null) return;
            controller.deposit(account, Double.parseDouble(rawInput));
            reloadAccounts(currentCustomer);
        } catch (NumberFormatException e) {
            UtilityDialogs.reportInvalidInput(this, "Ogiltigt formaterad insättning.");
        } catch (NoSuchRecordException e) {
            UtilityDialogs.reportFailedOperation(
                    this,
                    "Det sökta kontot hittades inte. Inga pengar har satts in.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    DTOAccount getSelectedAccount() {
        return accountSelect.getItemAt(accountSelect.getSelectedIndex());
    }

    void openAccount(DTOCustomer customer) {
        String rawInput = JOptionPane.showInputDialog("Ange räntesats för det nya kontot i procent:");
        if (rawInput == null) return;
        try {
            double interestRate = Double.parseDouble(rawInput.replace("%", "").strip());
            controller.openAccount(customer, interestRate);
            reloadAccounts(customer);
        } catch (NumberFormatException e) {
            UtilityDialogs.reportInvalidInput(this, "Ogiltigt formaterad räntesats.");
        } catch (InvalidInsertException e) {
            e.printStackTrace();
            UtilityDialogs.reportInvalidInput(this, "Misslyckades med att öppna konto.");
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(this, "Misslyckades med att öppna konto: ägaren hittades inte.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    void printTransactions(DTOAccount account) {
        try {
            controller.getAccountTransactions(account,
                    LocalDate.now().minus(1, ChronoUnit.MONTHS))
                    .forEach(t -> System.out.println(t.toString()));
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(this, "Kontot hittades inte.");
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    void reloadAccounts(DTOCustomer customer) {
        this.currentCustomer = customer;
        accountSelect.removeAllItems();
        Arrays.stream(this.getComponents()).forEach(c -> c.setEnabled(false));
        if (currentCustomer == null) return;
        openAccountButton.setEnabled(true);
        try {
            Collection<DTOAccount> accounts = controller.getCustomerAccounts(currentCustomer);
            if (!accounts.isEmpty()) {
                accounts.forEach(accountSelect::addItem);
                Arrays.stream(this.getComponents()).forEach(c -> c.setEnabled(true));
            }
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    void setLayout(Container container) {
        GridLayout layout = new GridLayout(0, 3);
        layout.setHgap(4);
        layout.setVgap(4);
        container.setLayout(layout);
        container.add(new JLabel("Välj konto:"));
        container.add(accountSelect);
        container.add(openAccountButton);
        container.add(interestRateButton);
        container.add(withdrawButton);
        container.add(depositButton);
        container.add(transactionHistoryButton);
        container.add(closeAccountButton);
    }

    void setInterestRate(DTOAccount account) {
        try {
            String rawInput = JOptionPane.showInputDialog(
                    "Ny räntesats i procent (nuvarande " + account.getInterestRate() + "%)");
            if (rawInput == null) return;
            // Överflödigt procenttecken är ett lättgjort misstag och lätt att korrigera
            double interestRate = Double.parseDouble(rawInput.replace("%", "").strip());
            controller.updateInterestRate(account, interestRate);
            reloadAccounts(currentCustomer);
        } catch (NumberFormatException e) {
            UtilityDialogs.reportInvalidInput(this, "Ogiltigt fomaterad räntesats.");
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(this, "Misslyckades med att ändra räntesats: kontot hittades inte.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    void withdraw(DTOAccount account) {
        try {
            String rawInput = JOptionPane.showInputDialog("Belopp att ta ut (saldo " +
                    account.getBalance() + " kr) :");
            if (rawInput == null) return;
            controller.withdraw(account, Double.parseDouble(rawInput.replace(",", ".").strip()));
            reloadAccounts(currentCustomer);
        } catch (NumberFormatException e) {
            UtilityDialogs.reportInvalidInput(this, "Ogiltigt formaterat uttag. Inga pengar har tagits ut.");
        } catch (InsufficientFundsException e) {
            UtilityDialogs.reportInvalidInput(this, "Otillräckligt saldo. Inga pengar har tagits ut.");
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(
                    this,
                    "Det sökta kontot hittades inte. Inga pengar har tagits ut.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }
}
