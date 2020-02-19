package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InsufficientFundsException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.atm.controller.Controller;
import se.nackademin.bankomatdb.model.DTOAccount;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class KontonView extends JFrame {
    private Controller controller; // TODO Tilldela vid lämpligt tillfälle
    private Container container = getContentPane();
    private JLabel väljKontoLabel = new JLabel("Välj konto");
    private JComboBox<DTOAccount> konton = new JComboBox<>();
    private JButton taUt = new JButton("Ta ut");
    private JButton seSaldo = new JButton("Se Saldo");
    private JButton kontoHistorik = new JButton("Kontohistorik");

    KontonView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        this.setTitle("Konton");
        this.setSize(400, 190);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
    }

    void setLayout() {
        container.setLayout(null);
    }

    void setLocationAndSize() {
        väljKontoLabel.setBounds(40, 20, 125, 30);
        väljKontoLabel.setFont(new Font("Serif", Font.BOLD, 17));
        konton.setBounds(150, 23, 125, 30);
        taUt.setBounds(40, 80, 100, 30);
        seSaldo.setBounds(150, 80, 100, 30);
        kontoHistorik.setBounds(260, 80, 100, 30);
    }

    void addComponentsToContainer() {
        container.add(väljKontoLabel);
        container.add(konton);
        container.add(taUt);
        container.add(seSaldo);
        container.add(kontoHistorik);
    }

    void addActionEvent() {
        konton.addActionListener(e -> selectKonto());
        taUt.addActionListener(e -> withdraw((DTOAccount) konton.getSelectedItem()));
        seSaldo.addActionListener(e -> viewSaldo((DTOAccount) konton.getSelectedItem()));
        kontoHistorik.addActionListener(e -> printKontohistorik((DTOAccount) konton.getSelectedItem()));
    }

    void fillComboBox(Collection<DTOAccount> accounts) {
        konton.removeAllItems();
        accounts.forEach(acc -> konton.addItem(acc));
    }

    void printKontohistorik(DTOAccount account) {
        try {
            System.out.println("Kontohistorik för kontonummer " + account.getAccountId() + ":");
            controller.getTransactionHistory(account).forEach(t -> System.out.println(t.toString()));
        } catch (DatabaseConnectionException | NoSuchRecordException e) {
            e.printStackTrace();
        }
    }

    void selectKonto() {
        // Skapa currentKonto metod
    }

    void viewSaldo(DTOAccount account) {
        JOptionPane.showMessageDialog(null, "Saldo: " + account.getBalance());
    }

    void withdraw(DTOAccount account) {
        int summa = Integer.parseInt(JOptionPane.showInputDialog(null, "Hur mycket vill du ta ut?"));
        try {
            if (controller.withdraw(account, summa)) {
                // uttag lyckades
            } else {
                // uttag misslyckades utan felsignal
            }
        } catch (NoSuchRecordException | DatabaseConnectionException | InsufficientFundsException e) {
            e.printStackTrace();
        }
    }
}
