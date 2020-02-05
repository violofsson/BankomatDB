package se.nackademin.bankomatdb.adminpanel.View;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame{

    private Container container = getContentPane();
    private JLabel läggTillKundLabel = new JLabel("Lägg till kund");
    private JTextField läggTillKundTextField = new JTextField("Skriv in kund");
    private JButton läggTillKundButton = new JButton("Lägg till");
    private JLabel raderaKundLabel = new JLabel("Radera kund");
    private JComboBox raderaKundComboBox = new JComboBox();
    private JButton raderaKundButton = new JButton("Radera");
    private JLabel skapaKontoLabel = new JLabel("Skapa konto");
    private JTextField skapaKontoTextField = new JTextField("Skriv in konto");
    private JButton skapaKontoButton = new JButton("Skapa konto");
    private JLabel avslutaKontoLabel = new JLabel("Avsluta konto");
    private JComboBox avslutaKontoComboBox = new JComboBox();
    private JButton avslutaKontoButton = new JButton("Avsluta konto");
    private JLabel hanteraKontoLabel = new JLabel("Hantera konto");
    private JComboBox hanteraKontoComboBox = new JComboBox();
    private JButton hanteraKontoButton = new JButton("Gå vidare");
    private JLabel hanteraLånLabel = new JLabel("Hantera lån");
    private JComboBox hanteraLånComboBox = new JComboBox();
    private JButton hanteraLånButton = new JButton("Gå vidare");
    private ActionListenerStart actionListener = new ActionListenerStart(läggTillKundTextField, läggTillKundButton,
            raderaKundComboBox, raderaKundButton, skapaKontoTextField, skapaKontoButton, avslutaKontoComboBox,
            avslutaKontoButton, hanteraKontoComboBox, hanteraKontoButton, hanteraLånComboBox, hanteraLånButton);

    StartView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        this.setTitle("Bankombud");
        this.setSize(410, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

    }

    public void setLayout() {
        container.setLayout(null);

    }

    public void setLocationAndSize() {
        läggTillKundLabel.setBounds(20, 10, 100, 30);
        läggTillKundLabel.setFont(new Font("sherif", Font.BOLD, 15));
        läggTillKundTextField.setBounds(145, 10, 100, 30);
        läggTillKundButton.setBounds(270, 10, 100, 30);
        raderaKundLabel.setBounds(20, 50, 100, 30);
        raderaKundLabel.setFont(new Font("sherif", Font.BOLD, 15));
        raderaKundComboBox.setBounds(145, 50, 100, 30);
        raderaKundButton.setBounds(270, 50, 100, 30);
        skapaKontoLabel.setBounds(20, 90, 100, 30);
        skapaKontoLabel.setFont(new Font("sherif", Font.BOLD, 15));
        skapaKontoTextField.setBounds(145, 90, 100, 30);
        skapaKontoButton.setBounds(270, 90, 100, 30);
        avslutaKontoLabel.setBounds(20, 130, 110, 30);
        avslutaKontoLabel.setFont(new Font("sherif", Font.BOLD, 15));
        avslutaKontoComboBox.setBounds(145, 130, 100, 30);
        avslutaKontoButton.setBounds(270, 130, 100, 30);
        hanteraKontoLabel.setBounds(20, 170, 110, 30);
        hanteraKontoLabel.setFont(new Font("sherif", Font.BOLD, 15));
        hanteraKontoComboBox.setBounds(145, 170, 100, 30);
        hanteraKontoButton.setBounds(270, 170, 100, 30);
        hanteraLånLabel.setBounds(20, 210, 100, 30);
        hanteraLånLabel.setFont(new Font("sherif", Font.BOLD, 15));
        hanteraLånComboBox.setBounds(145, 210, 100, 30);
        hanteraLånButton.setBounds(270, 210, 100, 30);

    }

    public void addComponentsToContainer() {
        container.add(läggTillKundLabel);
        container.add(läggTillKundTextField);
        container.add(läggTillKundButton);
        container.add(raderaKundLabel);
        container.add(raderaKundComboBox);
        container.add(raderaKundButton);
        container.add(skapaKontoLabel);
        container.add(skapaKontoTextField);
        container.add(skapaKontoButton);
        container.add(avslutaKontoLabel);
        container.add(avslutaKontoComboBox);
        container.add(avslutaKontoButton);
        container.add(hanteraKontoLabel);
        container.add(hanteraKontoComboBox);
        container.add(hanteraKontoButton);
        container.add(hanteraLånLabel);
        container.add(hanteraLånComboBox);
        container.add(hanteraLånButton);

    }

    public void addActionEvent() {
        läggTillKundTextField.addActionListener(actionListener);
        läggTillKundButton.addActionListener(actionListener);
        raderaKundComboBox.addActionListener(actionListener);
        raderaKundButton.addActionListener(actionListener);
        skapaKontoTextField.addActionListener(actionListener);
        skapaKontoButton.addActionListener(actionListener);
        avslutaKontoComboBox.addActionListener(actionListener);
        avslutaKontoButton.addActionListener(actionListener);
        hanteraKontoComboBox.addActionListener(actionListener);
        hanteraKontoButton.addActionListener(actionListener);
        hanteraLånComboBox.addActionListener(actionListener);
        hanteraLånButton.addActionListener(actionListener);

    }
}
