package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.NoSuchCustomerException;
import se.nackademin.bankomatdb.adminpanel.repository.Repository;
import se.nackademin.bankomatdb.atm.controller.Controller;
import se.nackademin.bankomatdb.atm.repository.ATMRepository;
import se.nackademin.bankomatdb.model.DTOAccount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KontonView extends JFrame {

    private Container container = getContentPane();
    private JLabel chooseAccountLabel = new JLabel("Välj konto");
    private JComboBox<DTOAccount> accounts = new JComboBox<>();
    private JButton withdraw = new JButton("Ta ut");
    private JButton balance = new JButton("Se Saldo");
    private JButton accountHistory = new JButton("Kontohistorik");
    private ActionListenerKonton actionListener = new ActionListenerKonton(accounts, withdraw, balance, accountHistory);
    private List<DTOAccount> accountList = new ArrayList<>();
    private Controller controller = new Controller();
    private ATMRepository repository;

    KontonView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        fillComboBox();
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
        try {
            accountList = repository.getCustomerAccounts(controller.getCurrentCustomerId());
        } catch (DatabaseConnectionException | NoSuchCustomerException e) {
            e.printStackTrace();
        }

        for (DTOAccount account : accountList) {
            accounts.addItem(account);
        }
    }
}
