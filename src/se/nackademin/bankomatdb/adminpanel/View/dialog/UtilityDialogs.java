package se.nackademin.bankomatdb.adminpanel.View.dialog;

import javax.swing.*;
import java.awt.*;

public class UtilityDialogs {
    public static void reportConnectionError(Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent,
                "Operationen misslyckades.\nKontrollera databasanslutningen.",
                "Anslutningsfel",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void reportFailedOperation(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent,
                message,
                "Misslyckad operation",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void reportInvalidInput(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent,
                message + "\nKontrollera inmatade data och försök igen.",
                "Felaktig inmatning",
                JOptionPane.WARNING_MESSAGE);
    }
}
