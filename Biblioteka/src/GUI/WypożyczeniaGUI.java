package GUI;

import logika.Biblioteka;
import logika.Ksiazka;
import logika.Obserwator;
import wyjatki.DodawanieException;
import wyjatki.StanException;
import wyjatki.SzukanieException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WypożyczeniaGUI  {
    private JPanel panel;
    private JTextField textFieldTytul;
    private JTextField textFieldAutor;
    private JTextField textFieldIndeks;
    private JTextField textFieldImie;
    private JTextField textFieldNazwisko;
    private JTextField textFieldNumer;
    private JButton wypożyczButton;
    Ksiazka ksiazka;
    Biblioteka biblioteka;


    public WypożyczeniaGUI(Biblioteka biblioteka, Ksiazka ksiazka) {

        this.ksiazka = ksiazka;
        this.biblioteka = biblioteka;
        textFieldTytul.setText(ksiazka.getNazwa());
        textFieldAutor.setText(ksiazka.getAutor());
        textFieldIndeks.setText(ksiazka.getIndeks());


        wypożyczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    biblioteka.wypozyczKsiazke(textFieldTytul.getText(),textFieldNumer.getText());
                    JOptionPane.showMessageDialog(null, "Książka wypożyczona");
                } catch (DodawanieException | SzukanieException e1) {
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }

            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
