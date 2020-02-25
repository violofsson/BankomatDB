package se.nackademin.bankomatdb.adminpanel.View;

import org.javatuples.Triplet;
import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.InvalidInsertException;
import se.nackademin.bankomatdb.NoSuchRecordException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class CustomerView extends JPanel {
    private JFrame parentFrame;
    private Controller controller;
    private JButton addCustomerButton = new JButton("Ny kund");
    private JButton removeCustomerButton = new JButton("Ta bort vald kund");
    JComboBox<DTOCustomer> customerSelect = new JComboBox<>();
    private JButton reloadButton = new JButton("Ladda om kunddata");

    CustomerView(JFrame parent, Controller c) {
        super();
        this.parentFrame = parent;
        this.controller = c;
        setLayout(this);
        addActionListeners();
        reloadCustomers();
    }

    void newCustomer() {
        try {
            NewCustomerDialog dialog = new NewCustomerDialog(parentFrame);
            Triplet<String, String, String> input = dialog.run();
            controller.addCustomer(input.getValue0(), input.getValue1(), input.getValue2());
            reloadCustomers();
        } catch (InvalidInsertException | DatabaseConnectionException e) {
            // TODO
        } catch (NullPointerException e) {
            // TODO
        }
    }

    void reloadCustomers() {
        try {
            Collection<DTOCustomer> customers = controller.getCustomers();
            customerSelect.removeAllItems();
            customers.forEach(customerSelect::addItem);
        } catch (DatabaseConnectionException e) {
            // TODO
        }
    }

    void removeCustomer() {
        if (getSelectedCustomer() != null) {
            int response = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Är du säker på att du vill ta bort " + getSelectedCustomer().toString() + "?",
                    "Bekräfta borttagning",
                    JOptionPane.OK_CANCEL_OPTION);
            if (response == JOptionPane.OK_OPTION) {
                try {
                    if (controller.deleteCustomer(getSelectedCustomer())) {
                        reloadCustomers();
                    } else {
                        // TODO
                    }
                } catch (NoSuchRecordException e) {
                    // TODO Meddela misslyckande?
                    e.printStackTrace();
                    reloadCustomers();
                } catch (DatabaseConnectionException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
        }
    }

    DTOCustomer getSelectedCustomer() {
        return customerSelect.getItemAt(customerSelect.getSelectedIndex());
    }

    void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(customerSelect);
        container.add(addCustomerButton);
        container.add(removeCustomerButton);
        container.add(reloadButton);
    }

    void addActionListeners() {
        removeCustomerButton.addActionListener(ae -> removeCustomer());
        reloadButton.addActionListener(ae -> reloadCustomers());
    }
}
