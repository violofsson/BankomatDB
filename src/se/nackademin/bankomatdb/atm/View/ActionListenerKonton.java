package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.atm.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class ActionListenerKonton implements ActionListener {
    private JComboBox<DTOAccount> accountsComboBox;
    private JButton withdraw;
    private JButton balance;
    private JButton accountHistory;
    private Controller controller;

    ActionListenerKonton(Controller c, JComboBox<DTOAccount> accountsComboBox, JButton withdraw, JButton balance, JButton accountHistory) {
        this.controller = c;
        this.accountsComboBox = accountsComboBox;
        this.withdraw = withdraw;
        this.balance = balance;
        this.accountHistory = accountHistory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DTOAccount selectedAccount = (DTOAccount) accountsComboBox.getSelectedItem();

        if (e.getSource() == withdraw) {
            if (selectedAccount == null) return;
            String rawInput = JOptionPane.showInputDialog(null, "Hur mycket vill du ta ut?");
            if (rawInput == null) return;
            try {
            int amount = Integer.parseInt(rawInput);
                if (controller.withdraw(selectedAccount, amount)) {
                    System.out.println("Uttag lyckades.\n");
                } else {
                    System.out.println("Uttag misslyckades.\n");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Felaktig inmatning, försök igen.");
            } catch (InsufficientFundsException | DatabaseConnectionException | NoSuchRecordException ex) {
                ex.printStackTrace();
            } finally {
                fillComboBox();
                System.out.println();
            }

        } else if (e.getSource() == balance) {
            if (selectedAccount == null) return;
            try {
                System.out.println("Saldo: " + selectedAccount.getBalance() +"\n" + "Lån:");
                Collection<DTOLoan> loans = controller.getCustomerLoans();
                if (loans.isEmpty()) {
                    System.out.println("Inga lån.");
                } else {
                    loans.forEach(System.out::println);
                }
            } catch (DatabaseConnectionException ex) {
                ex.printStackTrace();
            } finally {
                System.out.println();
            }

        } else if (e.getSource() == accountHistory) {
            try {
                if (selectedAccount == null) return;
                System.out.println("Kontohistorik:");
                Collection<DTOTransaction> history = controller.getTransactionHistory(selectedAccount);
                if (history.isEmpty()) {
                    System.out.println("Inga transaktioner.");
                } else {
                    history.forEach(System.out::println);
                }
            } catch (DatabaseConnectionException | NoSuchRecordException ex) {
                ex.printStackTrace();
            } finally {
                System.out.println();
            }
        }
    }

    public void fillComboBox() {
        try {
            accountsComboBox.setEnabled(false);
            accountsComboBox.removeAllItems();
            controller.getCustomerAccounts().forEach(account -> accountsComboBox.addItem(account));
            accountsComboBox.setEnabled(true);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }
}
