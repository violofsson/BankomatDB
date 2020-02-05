package se.nackademin.bankomatdb.atm.View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private Container container = getContentPane();
    private JLabel bankomatLabel = new JLabel("Bankomat");
    private JLabel pinLabel = new JLabel("Skriv in pin");
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");

    LoginView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
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
        pinLabel.setBounds(30, 75, 100, 30);
        pinLabel.setFont(new Font("Serif", Font.BOLD, 17));
        passwordField.setBounds(130, 80, 100, 30);
        resetButton.setBounds(73, 140, 70, 30);
        loginButton.setBounds(173, 140, 70, 30);

    }

    public void addComponentsToContainer() {
        container.add(bankomatLabel);
        container.add(pinLabel);
        container.add(passwordField);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
    }
}