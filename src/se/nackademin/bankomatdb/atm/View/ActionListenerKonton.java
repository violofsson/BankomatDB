package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.atm.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            int amount = Integer.parseInt(rawInput);
            try {
                if (controller.withdraw(selectedAccount, amount)) {
                    System.out.println("Uttag lyckades.");
                } else {
                    System.out.println("Uttag misslyckades.");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Felaktig inmatning, försök igen.");
            } catch (InsufficientFundsException | DatabaseConnectionException | NoSuchRecordException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == balance) {
            if (selectedAccount == null) return;
            try {
                System.out.println("Saldo: " + selectedAccount.getBalance() +"\n" + "Lån: " + controller.getCustomerLoans());
            } catch (DatabaseConnectionException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == accountHistory) {
            try {
                if (selectedAccount == null) return;
                System.out.println("Kontohistorik: " + controller.getTransactionHistory(selectedAccount));
            } catch (DatabaseConnectionException | NoSuchRecordException ex) {
                ex.printStackTrace();
            }
        }
    }
}
