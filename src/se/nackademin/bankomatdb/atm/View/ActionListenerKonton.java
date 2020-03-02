package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.NoSuchAccountException;
import se.nackademin.bankomatdb.NoSuchCustomerException;
import se.nackademin.bankomatdb.atm.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ActionListenerKonton implements ActionListener {

    private JComboBox<DTOAccount> accountsComboBox;
    private JButton withdraw;
    private JButton balance;
    private JButton accountHistory;
    private Controller controller = new Controller();

    ActionListenerKonton(JComboBox<DTOAccount> accountsComboBox, JButton withdraw, JButton balance, JButton accountHistory) {
        this.accountsComboBox = accountsComboBox;
        this.withdraw = withdraw;
        this.balance = balance;
        this.accountHistory = accountHistory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DTOAccount selectedAccount = (DTOAccount) accountsComboBox.getSelectedItem();

        if (e.getSource() == accountsComboBox) {
            accountsComboBox = (JComboBox) e.getSource();

        } else if (e.getSource() == withdraw) {
            int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Hur mycket vill du ta ut?"));
            try {
                if (controller.withdraw(selectedAccount, amount)) {
                    System.out.println("Uttag lyckades.");
                } else {
                    System.out.println("Uttag misslyckades.");
                }
            } catch (InsufficientFundsException | DatabaseConnectionException | NoSuchAccountException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == balance) {
            JOptionPane.showMessageDialog(null, "Saldo: " + selectedAccount.getBalance());

        } else if (e.getSource() == accountHistory) {
            try {
                System.out.println("Kontohistorik: " + controller.getTransactionHistory(selectedAccount));
            } catch (DatabaseConnectionException | NoSuchAccountException ex) {
                ex.printStackTrace();
            }
        }
    }
}
