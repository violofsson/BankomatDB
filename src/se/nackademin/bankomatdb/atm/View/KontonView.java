package se.nackademin.bankomatdb.atm.View;

import javax.swing.*;
import java.awt.*;

public class KontonView extends JFrame{

    private Container container = getContentPane();
    private JLabel väljKontoLabel = new JLabel("Välj konto");
    private JComboBox konton = new JComboBox();
    private JButton taUt = new JButton("Ta ut");
    private JButton seSaldo = new JButton("Se Saldo");
    private JButton kontoHistorik = new JButton("Kontohistorik");

    KontonView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        this.setTitle("Konton");
        this.setSize(400, 190);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

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
    }
}
