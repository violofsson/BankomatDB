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
    }

    void newCustomer() {
        try {
            // TODO Kontrollera indata
            Triplet<String, String, String> input = new NewCustomerDialog(parentFrame).run();
            if (input == null) return;
            DTOCustomer newCustomer = controller.addCustomer(input.getValue0(), input.getValue1(), input.getValue2());
            customerSelect.addItem(newCustomer);
        } catch (NullPointerException e) {
            // TODO Kan detta hända? Null-testet ovan eliminerar redan avbruten inmatning
            e.printStackTrace();
        } catch (InvalidInsertException | DatabaseConnectionException e) {
            // TODO
            e.printStackTrace();
        }
    }

    void reloadCustomers() {
        try {
            Collection<DTOCustomer> customers = controller.getCustomers();
            customerSelect.removeAllItems();
            customers.forEach(customerSelect::addItem);
        } catch (DatabaseConnectionException e) {
            // TODO
            JOptionPane.showMessageDialog(parentFrame, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }

    void removeCustomer() {
        if (getSelectedCustomer() != null) {
            int response = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Är du säker på att du vill ta bort " + getSelectedCustomer().toString() + "?\n" +
                    "Alla hens personuppgifter, inklusive konton och lån, kommer att tas bort permanent.",
                    "Bekräfta borttagning",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                try {
                    if (controller.deleteCustomer(getSelectedCustomer())) {
                        customerSelect.removeItem(getSelectedCustomer());
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
        addCustomerButton.addActionListener(ae -> newCustomer());
        removeCustomerButton.addActionListener(ae -> removeCustomer());
        reloadButton.addActionListener(ae -> reloadCustomers());
    }
}
