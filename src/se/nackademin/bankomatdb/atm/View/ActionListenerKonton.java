package se.nackademin.bankomatdb.atm.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerKonton implements ActionListener {

    private JComboBox kontonComboBox;
    private JButton taUt;
    private JButton seSaldo;
    private JButton kontoHistorik;

    ActionListenerKonton(JComboBox kontonComboBox, JButton taUt, JButton seSaldo, JButton kontoHistorik) {
        this.kontonComboBox = kontonComboBox;
        this.taUt = taUt;
        this.seSaldo = seSaldo;
        this.kontoHistorik = kontoHistorik;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == kontonComboBox) {
            //skapa currentkonto metod.


        }else if (e.getSource() == taUt) {
            int summa = Integer.parseInt(JOptionPane.showInputDialog(null, "Hur mycket vill du ta ut?"));

        }else if (e.getSource() == seSaldo) {
            JOptionPane.showMessageDialog(null, "Saldo: ");

        }else if (e.getSource() == kontoHistorik) {
            System.out.println("Kontohistorik: ");
        }
    }
}
