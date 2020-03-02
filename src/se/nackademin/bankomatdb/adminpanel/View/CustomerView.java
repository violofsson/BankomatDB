package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InvalidInsertException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.View.dialog.NewCustomerDialog;
import se.nackademin.bankomatdb.adminpanel.View.dialog.UpdateCustomerDialog;
import se.nackademin.bankomatdb.adminpanel.View.dialog.UtilityDialogs;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class CustomerView extends JPanel {
    private JFrame parentFrame;
    private Controller controller;
    private JButton addCustomerButton = new JButton("Ny kund");
    private JButton updateCustomerButton = new JButton("Uppdatera kunduppgifter");
    private JButton removeCustomerButton = new JButton("Ta bort vald kund");
    JComboBox<DTOCustomer> customerSelect = new JComboBox<>();
    private JButton reloadButton = new JButton("Ladda om kunddata");

    CustomerView(JFrame parent, Controller c) {
        super();
        this.parentFrame = parent;
        this.controller = c;
        setLayout(this);
        addActionListeners();
    }

    private void addActionListeners() {
        addCustomerButton.addActionListener(ae -> addCustomer());
        updateCustomerButton.addActionListener(ae -> updateCustomer(getSelectedCustomer()));
        removeCustomerButton.addActionListener(ae -> removeCustomer(getSelectedCustomer()));
        reloadButton.addActionListener(ae -> reloadCustomers());
    }

    private void addCustomer() {
        try {
            Triplet<String, String, String> nameIdPin = new NewCustomerDialog(parentFrame).getNameIdPin();
            if (nameIdPin == null) return;
            if (nameIdPin.getValue0().isEmpty() || nameIdPin.getValue1().isEmpty() || nameIdPin.getValue2().isEmpty()) {
                throw new InvalidInsertException("Tomma fält");
            } else if (nameIdPin.getValue1().length() != 13) {
                throw new InvalidInsertException("Felaktigt personnummer - använd 12 siffror och bindestreck!");
            } else if (nameIdPin.getValue2().length() != 4) {
                throw new InvalidInsertException("PIN-kod måste ha exakt fyra siffror!");
            }
            controller.addCustomer(nameIdPin.getValue0(), nameIdPin.getValue1(), nameIdPin.getValue2());
        } catch (InvalidInsertException e) {
            e.printStackTrace();
            UtilityDialogs.reportInvalidInput(this, "Misslyckades med att lägga till ny kund.");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        } finally {
            reloadCustomers();
        }
    }

    DTOCustomer getSelectedCustomer() {
        return customerSelect.getItemAt(customerSelect.getSelectedIndex());
    }

    void reloadCustomers() {
        try {
            customerSelect.removeAllItems();
            customerSelect.setEnabled(false);
            updateCustomerButton.setEnabled(false);
            removeCustomerButton.setEnabled(false);
            Collection<DTOCustomer> customers = controller.getCustomers();
            if (!customers.isEmpty()) {
                customers.forEach(customerSelect::addItem);
                customerSelect.setEnabled(true);
                updateCustomerButton.setEnabled(true);
                removeCustomerButton.setEnabled(true);
            }
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        }
    }

    private void removeCustomer(DTOCustomer customer) {
        if (customer == null) return;
        int response = JOptionPane.showConfirmDialog(this,
                "Är du säker på att du vill ta bort " + customer.toString() + "?\n" +
                        "Alla personuppgifter, inklusive konton och lån, kommer att tas bort permanent.",
                "Bekräfta borttagning",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                if (!controller.deleteCustomer(customer)) {
                    UtilityDialogs.reportFailedOperation(this,
                            "Misslyckades med att ta bort kund. Hen kanske redan är borttagen.\n" +
                                    "Laddar om kunddata...");
                } // Comboboxen uppdateras i finally-satsen
            } catch (NoSuchRecordException e) {
                e.printStackTrace();
                UtilityDialogs.showGenericMessage(this,
                        "Onödig operation: kunden finns inte längre i databasen.\n" +
                                "Laddar om kunddata...");
            } catch (DatabaseConnectionException e) {
                UtilityDialogs.reportConnectionError(this, e);
            } finally {
                reloadCustomers();
            }
        }
    }

    private void setLayout(Container container) {
        GridLayout layout = new GridLayout(0, 3);
        layout.setHgap(4);
        layout.setVgap(4);
        container.setLayout(layout);
        container.add(new JLabel("Välj kund:"));
        container.add(customerSelect);
        container.add(addCustomerButton);
        container.add(updateCustomerButton);
        container.add(removeCustomerButton);
        container.add(reloadButton);
    }

    private void updateCustomer(DTOCustomer customer) {
        if (customer == null) return;
        Pair<String, String> nameAndPin = new UpdateCustomerDialog(parentFrame, customer).getNameAndPin();
        if (nameAndPin == null) return;
        try {
            controller.updateCustomer(customer, nameAndPin.getValue0(), nameAndPin.getValue1());
        } catch (NoSuchRecordException e) {
            e.printStackTrace();
            UtilityDialogs.reportFailedOperation(this,
                    "Misslyckades med att uppdatera kund: kunden finns inte i databasen.\n" +
                            "Laddar om kunddata...");
        } catch (DatabaseConnectionException e) {
            UtilityDialogs.reportConnectionError(this, e);
        } finally {
            reloadCustomers();
        }
    }
}
