package se.nackademin.bankomatdb.atm.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerKonton implements ActionListener {

    private JComboBox accountsComboBox;
    private JButton withdraw;
    private JButton balance;
    private JButton accountHistory;

    ActionListenerKonton(JComboBox accountsComboBox, JButton withdraw, JButton balance, JButton accountHistory) {
        this.accountsComboBox = accountsComboBox;
        this.withdraw = withdraw;
        this.balance = balance;
        this.accountHistory = accountHistory;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == accountsComboBox) {


        } else if (e.getSource() == withdraw) {
            int summa = Integer.parseInt(JOptionPane.showInputDialog(null, "Hur mycket vill du ta ut?"));
            //Lägg till metod som tar bort summa från konto.

        } else if (e.getSource() == balance) {
            JOptionPane.showMessageDialog(null, "Saldo: ");
            //Lägg till saldo.

        } else if (e.getSource() == accountHistory) {
            System.out.println("Kontohistorik: ");
            //Lägg till kontohistorik.
        }
    }
}
