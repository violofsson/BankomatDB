package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;

public class Main {
    Controller controller;

    Main() throws DatabaseConnectionException {
        this.controller = new Controller();
    }

    public static void main(String[] args) {
        try {
            Main root = new Main();
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
