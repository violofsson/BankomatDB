package se.nackademin.bankomatdb.adminpanel.View.dialog;

import javax.swing.*;
import java.awt.*;

public class UtilityDialogs {
    private UtilityDialogs() {
    }

    public static void reportConnectionError(Component parentComponent, Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(parentComponent,
                "Operationen misslyckades. Kontrollera databasanslutningen.",
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

    public static void showGenericMessage(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent,
                message,
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
