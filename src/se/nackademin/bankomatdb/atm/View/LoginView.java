package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.atm.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private Container container = getContentPane();
    private JLabel bankomatLabel = new JLabel("Bankomat");
    private JLabel idLabel = new JLabel("Skriv in id");
    private JTextField idField = new JTextField();
    private JLabel pinLabel = new JLabel("Skriv in pin");
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");
    private ActionListenerLogin actionListener;

    LoginView(Controller c) {
        this.actionListener = new ActionListenerLogin(c, idField, passwordField, loginButton, resetButton, this);
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        this.setTitle("Login");
        this.setSize(300, 310);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setLayout() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        bankomatLabel.setBounds(85, 10, 150, 50);
        bankomatLabel.setFont(new Font("Serif", Font.BOLD, 25));
        idLabel.setBounds(30, 65, 100, 30);
        idLabel.setFont(new Font("Serif", Font.BOLD, 17));
        idField.setBounds(130, 65, 100, 30);
        pinLabel.setBounds(30, 110, 100, 30);
        pinLabel.setFont(new Font("Serif", Font.BOLD, 17));
        passwordField.setBounds(130, 110, 100, 30);
        resetButton.setBounds(73, 160, 70, 30);
        loginButton.setBounds(173, 160, 70, 30);
    }

    public void addComponentsToContainer() {
        container.add(bankomatLabel);
        container.add(idLabel);
        container.add(idField);
        container.add(pinLabel);
        container.add(passwordField);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(actionListener);
        resetButton.addActionListener(actionListener);
    }
}
