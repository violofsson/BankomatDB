package se.nackademin.bankomatdb.adminpanel.View;

import se.nackademin.bankomatdb.DatabaseConnectionException;
import se.nackademin.bankomatdb.adminpanel.controller.Controller;
import se.nackademin.bankomatdb.model.DTOCustomer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class CustomerView extends JPanel {
    private JFrame parentFrame;
    private Controller controller;
    private JButton addCustomer = new JButton("Ny kund");
    private JButton removeCustomer = new JButton("Ta bort vald kund");
    JComboBox<DTOCustomer> customerSelect = new JComboBox<>();
    private JButton reloadButton = new JButton("Ladda om kunddata");

    CustomerView(JFrame parent, Controller c) throws DatabaseConnectionException {
        super();
        this.parentFrame = parent;
        this.controller = c;
        setLayout(this);
        addActionListeners();
        reloadCustomerSelector();
    }

    void reloadCustomerSelector() throws DatabaseConnectionException {
        Collection<DTOCustomer> customers = controller.getCustomers();
        customerSelect.removeAllItems();
        customers.forEach(customerSelect::addItem);
    }

    DTOCustomer getSelectedCustomer() {
        return customerSelect.getItemAt(customerSelect.getSelectedIndex());
    }

    public void setLayout(Container container) {
        container.setLayout(new FlowLayout());
        container.add(customerSelect);
        container.add(addCustomer);
        container.add(removeCustomer);
        container.add(reloadButton);
    }

    public void addActionListeners() {
        reloadButton.addActionListener(ae -> {
            try {
                reloadCustomerSelector();
            } catch (DatabaseConnectionException e) {
                e.printStackTrace();
            }
        });
    }
}
