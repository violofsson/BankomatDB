package se.nackademin.bankomatdb.adminpanel.View;

import javax.swing.*;
import java.awt.*;

public class LåneHanteringView extends JFrame{

    private Container container = getContentPane();
    private JLabel ändraBetalplanLabel = new JLabel("Ändra betalplan");
    private JTextField ändraBetalplanTextField = new JTextField("Datum");
    private JButton ändraBetalplanButton = new JButton("Ändra");
    private JLabel ändraRäntesatsLabel = new JLabel("Ändra Räntesats");
    private JTextField ändraRäntesatsDatum = new JTextField("Datum");
    private JTextField ändraRäntesatsRänta = new JTextField("Ränta");
    private JButton ändraRäntesatsButton = new JButton("Ändra");
    private JLabel beviljaLånLabel = new JLabel("Bevilja Lån");
    private JButton beviljaLånButton = new JButton("Bevilja");
    private JLabel visaBetalPlanLabel = new JLabel("Visa Betalplan");
    private JButton visaBetalPlanButton = new JButton("Visa");

    LåneHanteringView() {
        setLayout();
        setLocationAndSize();
        addComponentsToContainer();
        this.setTitle("Lånehantering");
        this.setSize(410, 215);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

    }

    public void setLayout() {
        container.setLayout(null);

    }

    public void setLocationAndSize() {
        ändraRäntesatsLabel.setBounds(20, 50, 130, 30);
        ändraRäntesatsLabel.setFont(new Font("sherif", Font.BOLD, 15));
        ändraRäntesatsDatum.setBounds(150, 10, 50, 30);
        ändraRäntesatsRänta.setBounds(210, 10, 50, 30);
        ändraRäntesatsButton.setBounds(270, 10, 100, 30);
        ändraBetalplanLabel.setBounds(20, 10, 120, 30);
        ändraBetalplanLabel.setFont(new Font("sherif", Font.BOLD, 15));
        ändraBetalplanTextField.setBounds(150, 50, 110, 30);
        ändraBetalplanButton.setBounds(270, 50, 100, 30);
        beviljaLånLabel.setBounds(20, 90, 100, 30);
        beviljaLånLabel.setFont(new Font("sherif", Font.BOLD, 15));
        beviljaLånButton.setBounds(150, 90, 100, 30);
        visaBetalPlanLabel.setBounds(20, 130, 120, 30);
        visaBetalPlanLabel.setFont(new Font("sherif", Font.BOLD, 15));
        visaBetalPlanButton.setBounds(150, 130, 100, 30);


    }

    public void addComponentsToContainer() {
        container.add(ändraRäntesatsLabel);
        container.add(ändraRäntesatsRänta);
        container.add(ändraRäntesatsDatum);
        container.add(ändraRäntesatsButton);
        container.add(ändraBetalplanLabel);
        container.add(ändraBetalplanTextField);
        container.add(ändraBetalplanButton);
        container.add(beviljaLånLabel);
        container.add(beviljaLånButton);
        container.add(visaBetalPlanLabel);
        container.add(visaBetalPlanButton);

    }

    public void addActionEvent() {

    }
}

