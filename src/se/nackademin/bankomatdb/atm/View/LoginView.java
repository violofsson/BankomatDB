package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.atm.AlreadyLoggedInException;
import se.nackademin.bankomatdb.atm.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private Controller controller; // TODO Tilldela vid lämpligt tillfälle
    private Container container = getContentPane();
    private JLabel bankomatLabel = new JLabel("Bankomat");
    private JLabel userLabel = new JLabel("Skriv in personnummer");
    private JTextField userField = new JTextField();
    private JLabel pinLabel = new JLabel("Skriv in pin");
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");
    private KontonView kontonView = new KontonView();

    LoginView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvents();
        this.setTitle("Login");
        this.setSize(300, 310);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void login() {
        // Skapa en checkpin metod.
        // Skapa en current Customer metod.
        try {
            controller.login(userField.getText(), String.valueOf(passwordField.getPassword()));
        } catch (AlreadyLoggedInException | DatabaseConnectionException e) {
            // TODO Hantera på riktigt
            e.printStackTrace();
        }
        kontonView.setVisible(true);
    }

    public void resetInputFields() {
        userField.removeAll();
        passwordField.removeAll();
    }

    public void setLayout() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        // TODO Placera personnummerfält
        bankomatLabel.setBounds(85, 10, 150, 50);
        bankomatLabel.setFont(new Font("Serif", Font.BOLD, 25));
        pinLabel.setBounds(30, 75, 100, 30);
        pinLabel.setFont(new Font("Serif", Font.BOLD, 17));
        passwordField.setBounds(130, 80, 100, 30);
        resetButton.setBounds(73, 140, 70, 30);
        loginButton.setBounds(173, 140, 70, 30);
    }

    public void addComponentsToContainer() {
        container.add(bankomatLabel);
        container.add(userLabel);
        container.add(userField);
        container.add(pinLabel);
        container.add(passwordField);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvents() {
        loginButton.addActionListener(e -> login());
        resetButton.addActionListener(e -> resetInputFields());
    }
}
