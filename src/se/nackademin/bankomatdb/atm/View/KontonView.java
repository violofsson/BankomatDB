package se.nackademin.bankomatdb.atm.View;

import se.nackademin.bankomatdb.atm.viewmodel.VMAccount;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class KontonView extends JFrame{

    private Container container = getContentPane();
    private JLabel väljKontoLabel = new JLabel("Välj konto");
    private JComboBox<String> konton = new JComboBox<>();
    private JButton taUt = new JButton("Ta ut");
    private JButton seSaldo = new JButton("Se Saldo");
    private JButton kontoHistorik = new JButton("Kontohistorik");
    private ActionListenerKonton actionListener = new ActionListenerKonton(konton, taUt, seSaldo, kontoHistorik);
    private HashMap<String, VMAccount> kontoLista;

    KontonView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        this.setTitle("Konton");
        this.setSize(400, 190);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);

    }

    public void setLayout() {
        container.setLayout(null);

    }

    public void setLocationAndSize() {
        väljKontoLabel.setBounds(40, 20, 125, 30);
        väljKontoLabel.setFont(new Font("Serif", Font.BOLD, 17));
        konton.setBounds(150, 23, 125, 30);
        taUt.setBounds(40, 80, 100, 30);
        seSaldo.setBounds(150, 80, 100, 30);
        kontoHistorik.setBounds(260, 80, 100, 30);

    }

    public void addComponentsToContainer() {
        container.add(väljKontoLabel);
        container.add(konton);
        container.add(taUt);
        container.add(seSaldo);
        container.add(kontoHistorik);
    }

    public void addActionEvent() {
        konton.addActionListener(actionListener);
        taUt.addActionListener(actionListener);
        seSaldo.addActionListener(actionListener);
        kontoHistorik.addActionListener(actionListener);
    }

    public void fillComboBox() {
        //Fyll hashmapen med Kontonamn och Konton
        for (String kontoNamn : kontoLista.keySet()) {
            konton.addItem(kontoNamn);
        }
    }
}
