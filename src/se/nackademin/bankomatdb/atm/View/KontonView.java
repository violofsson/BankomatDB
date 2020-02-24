package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.model.DTOAccount;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class KontonView extends JFrame {

    private Container container = getContentPane();
    private JLabel chooseAccountLabel = new JLabel("Välj konto");
    private JComboBox<String> accounts = new JComboBox<>();
    private JButton withdraw = new JButton("Ta ut");
    private JButton balance = new JButton("Se Saldo");
    private JButton accountHistory = new JButton("Kontohistorik");
    private ActionListenerKonton actionListener = new ActionListenerKonton(accounts, withdraw, balance, accountHistory);
    private HashMap<String, DTOAccount> accountList;

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

    public void setLayout() {
        container.setLayout(null);

    }

    public void setLocationAndSize() {
        chooseAccountLabel.setBounds(40, 20, 125, 30);
        chooseAccountLabel.setFont(new Font("Serif", Font.BOLD, 17));
        accounts.setBounds(150, 23, 125, 30);
        withdraw.setBounds(40, 80, 100, 30);
        balance.setBounds(150, 80, 100, 30);
        accountHistory.setBounds(260, 80, 100, 30);

    }

    public void addComponentsToContainer() {
        container.add(chooseAccountLabel);
        container.add(accounts);
        container.add(withdraw);
        container.add(balance);
        container.add(accountHistory);
    }

    public void addActionEvent() {
        accounts.addActionListener(actionListener);
        withdraw.addActionListener(actionListener);
        balance.addActionListener(actionListener);
        accountHistory.addActionListener(actionListener);
    }

    public void fillComboBox() {
        //Fyll hashmapen (kontoLista) med Kontonamn och Konton
        for (String kontoNamn : accountList.keySet()) {
            accounts.addItem(kontoNamn);
        }
    }
}
