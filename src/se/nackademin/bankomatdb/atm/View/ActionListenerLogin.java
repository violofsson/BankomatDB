package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.Database.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerLogin extends JFrame implements ActionListener {

    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private KontonView kontonView = new KontonView();

    ActionListenerLogin(JPasswordField passwordField, JButton loginButton, JButton resetButton) {
        this.passwordField = passwordField;
        this.loginButton = loginButton;
        this.resetButton = resetButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            kontonView.setVisible(true);
            //Lägg till checkpin metod.

        }else if (e.getSource() == resetButton) {
            passwordField.setText("");

        }
    }
}
