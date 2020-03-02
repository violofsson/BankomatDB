package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.atm.controller.Controller;

public class Main {
    public static void main(String[] args) {
        try {
            Controller c = new Controller();
            new LoginView(c);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }
}
