package se.nackademin.bankomatdb.adminpanel.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerStart implements ActionListener {

    private JTextField läggTillKundTextField;
    private JButton läggTillKundButton;
    private JComboBox raderaKundComboBox;
    private JButton raderaKundButton;
    private JTextField skapaKontoTextField;
    private JButton skapaKontoButton;
    private JComboBox avslutaKontoComboBox;
    private JButton avslutaKontoButton;
    private JComboBox hanteraKontoComboBox;
    private JButton hanteraKontoButton;
    private JComboBox hanteraLånComboBox;
    private JButton hanteraLånButton;
    private KontoHanteringView kontoHanteringView = new KontoHanteringView();
    private LåneHanteringView låneHanteringView = new LåneHanteringView();

    ActionListenerStart(JTextField läggTillKundTextField, JButton läggTillKundButton, JComboBox raderaKundComboBox,
                        JButton raderaKundButton, JTextField skapaKontoTextField, JButton skapaKontoButton,
                        JComboBox avslutaKontoComboBox, JButton avslutaKontoButton, JComboBox hanteraKontoComboBox,
                        JButton hanteraKontoButton, JComboBox hanteraLånComboBox, JButton hanteraLånButton) {

        this.läggTillKundTextField = läggTillKundTextField;
        this.läggTillKundButton = läggTillKundButton;
        this.raderaKundComboBox = raderaKundComboBox;
        this.raderaKundButton = raderaKundButton;
        this.skapaKontoTextField = skapaKontoTextField;
        this.skapaKontoButton = skapaKontoButton;
        this.avslutaKontoComboBox = avslutaKontoComboBox;
        this.avslutaKontoButton = avslutaKontoButton;
        this.hanteraKontoComboBox = hanteraKontoComboBox;
        this.hanteraKontoButton = hanteraKontoButton;
        this.hanteraLånComboBox = hanteraLånComboBox;
        this.hanteraLånButton = hanteraLånButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == läggTillKundButton) {

        }else if (e.getSource() == raderaKundComboBox) {

        }else if (e.getSource() == raderaKundComboBox) {

        }else if (e.getSource() == skapaKontoButton) {

        }else if (e.getSource() == avslutaKontoComboBox) {

        }else if (e.getSource() == avslutaKontoButton) {

        }else if (e.getSource() == hanteraKontoComboBox) {

        }else if (e.getSource() == hanteraKontoButton) {
            kontoHanteringView.setVisible(true);

        }else if (e.getSource() == hanteraLånComboBox) {

        }else if (e.getSource() == hanteraLånButton) {
            låneHanteringView.setVisible(true);
        }

    }
}
