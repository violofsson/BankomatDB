package se.nackademin.bankomatdb.adminpanel.View;

import javax.swing.*;
import java.awt.*;

public class KontoHanteringView extends JFrame{

    private Container container = getContentPane();
    private JLabel sättInPengarLabel = new JLabel("Sätt in pengar");
    private JTextField sättInPengarTextField = new JTextField("Summa");
    private JButton sättInPengarJbutton = new JButton("Sätt in");
    private JLabel taUtPengarLabel = new JLabel("Ta ut pengar");
    private JTextField taUtPengarTextField = new JTextField("Summa");
    private JButton taUtPengarButton = new JButton("Ta ut");
    private JLabel ändraRänteSatsLabel = new JLabel("Ändra räntesats");
    private JTextField ändraRänteSatsTextField = new JTextField("Räntesats");
    private JButton ändraRänteSatsButton = new JButton("Ändra Ränta");
    private JLabel kontoHistorikLabel = new JLabel("Visa kontohistorik");
    private JButton kontoHistorikButton = new JButton("Kontohistorik");

    KontoHanteringView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        this.setTitle("Kontohantering");
        this.setSize(410, 215);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);

    }

    public void setLayout() {
        container.setLayout(null);

    }

    public void setLocationAndSize() {
        sättInPengarLabel.setBounds(20, 10, 110, 30);
        sättInPengarLabel.setFont(new Font("sherif", Font.BOLD, 15));
        sättInPengarTextField.setBounds(145, 10, 100, 30);
        sättInPengarJbutton.setBounds(270, 10, 100, 30);
        taUtPengarLabel.setBounds(20, 50, 100, 30);
        taUtPengarLabel.setFont(new Font("sherif", Font.BOLD, 15));
        taUtPengarTextField.setBounds(145, 50, 100, 30);
        taUtPengarButton.setBounds(270, 50, 100, 30);
        ändraRänteSatsLabel.setBounds(20, 90, 120, 30);
        ändraRänteSatsLabel.setFont(new Font("sherif", Font.BOLD, 15));
        ändraRänteSatsTextField.setBounds(145, 90, 100, 30);
        ändraRänteSatsButton.setBounds(270, 90, 100, 30);
        kontoHistorikLabel.setBounds(20, 130, 140, 30);
        kontoHistorikLabel.setFont(new Font("sherif", Font.BOLD, 15));
        kontoHistorikButton.setBounds(190, 130, 120, 30);
    }

    public void addComponentsToContainer() {
        container.add(sättInPengarLabel);
        container.add(sättInPengarTextField);
        container.add(sättInPengarJbutton);
        container.add(taUtPengarLabel);
        container.add(taUtPengarTextField);
        container.add(taUtPengarButton);
        container.add(ändraRänteSatsLabel);
        container.add(ändraRänteSatsTextField);
        container.add(ändraRänteSatsButton);
        container.add(kontoHistorikLabel);
        container.add(kontoHistorikButton);

    }

    public void addActionEvent() {

    }
}
