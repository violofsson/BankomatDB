package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.atm.AlreadyLoggedInException;
import se.nackademin.bankomatdb.atm.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerLogin extends JFrame implements ActionListener {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private KontonView kontonView;
    private LoginView loginView;
    private Controller controller;

    ActionListenerLogin(Controller c, JTextField idField, JPasswordField passwordField, JButton loginButton, JButton resetButton, LoginView loginView) {
        this.controller = c;
        this.kontonView = new KontonView(c);
        this.idField = idField;
        this.passwordField = passwordField;
        this.loginButton = loginButton;
        this.resetButton = resetButton;
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            try {
                if (controller.login(idField.getText(), String.valueOf(passwordField.getPassword()))) {
                    loginView.setVisible(false);
                    kontonView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Fel inloggning");
                }
            } catch (DatabaseConnectionException | AlreadyLoggedInException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == resetButton) {
            passwordField.setText("");
        }
    }
}
