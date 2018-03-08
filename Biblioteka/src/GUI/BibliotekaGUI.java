package GUI;

import logika.Biblioteka;
import logika.Czytelnik;
import logika.Ksiazka;
import logika.Obserwator;
import wyjatki.DodawanieException;
import wyjatki.StanException;
import wyjatki.UsuwanieException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class BibliotekaGUI implements Obserwator, Serializable {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField textFieldKsiazkaSzukaj;
    private JPanel panel2;
    private JTable tableKsiazki;
    private JTextField textFieldTytul;
    private JTextField textFieldAutor;
    private JTextField textFieldGatunek;
    private JButton dodajKsiazkeButton;
    private JButton usuńKsiazkeButton;
    private JTextField textFieldImie;
    private JTextField textFieldData;
    private JButton dodajCzytelnikaButton;
    private JTextField textFieldTelefon;
    private JTextField textFieldNazwisko;
    private JTable tableCzytelnicy;
    private JTextField textFieldSzukajCzytelnik;


    private Biblioteka biblioteka;

    public BibliotekaGUI(Biblioteka biblioteka) {


        this.biblioteka = biblioteka;

        biblioteka.subskrybuj(this);

        // tabela Ksiazki
        String[] columnsNamesKsiazki = new String[] {"Tytuł", "Autor", "Gatunek", "Stan", "Indeks"};
        Object[][] dataKsiazki = {};
        DefaultTableModel modelKsiazki;
        modelKsiazki = new DefaultTableModel(dataKsiazki,columnsNamesKsiazki);
        tableKsiazki.setModel(modelKsiazki);
        reflashTableKsiazki();
        TableRowSorter<TableModel> rowSorterKsiazki = new TableRowSorter<>(tableKsiazki.getModel());
        tableKsiazki.setRowSorter(rowSorterKsiazki);

        // tabela czytelnicy
        String[] columnsNamesCzytelnicy = new String[]{"Imię","Nazwisko","Indeks Czytelnika","Zaległości"};
        Object[][] dataCzyetlnicy = {};
        DefaultTableModel modelCzytelnicy;
        modelCzytelnicy = new DefaultTableModel(dataCzyetlnicy,columnsNamesCzytelnicy);
        tableCzytelnicy.setModel(modelCzytelnicy);
        reflashTableCzytelnicy();
        TableRowSorter<TableModel> rowSorterCzytelnicy = new TableRowSorter<>(tableCzytelnicy.getModel());
        tableCzytelnicy.setRowSorter(rowSorterCzytelnicy);


        // menu dla tabeli ksiazki
        JPopupMenu menuKsiazki = new JPopupMenu();
        JMenuItem itemWypozycz = new JMenuItem("Wypożycz");
        JMenuItem itemUsunKsiazke = new JMenuItem("Usuń");
        menuKsiazki.add(itemWypozycz);
        menuKsiazki.add(itemUsunKsiazke);

        // menu dla tabeli czytelnicy
        JPopupMenu menuCzytelnik = new JPopupMenu();
        JMenuItem itemUsunCzytelnika = new JMenuItem("Usuń");
        JMenuItem itemPanel = new JMenuItem("Panel Użytkownika");
        menuCzytelnik.add(itemPanel);
        menuCzytelnik.add(itemUsunCzytelnika);

        //listeners

        dodajKsiazkeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    biblioteka.dodajKsiazkeDoKatalogu(textFieldTytul.getText(),textFieldGatunek.getText(),textFieldAutor.getText());
                    reflashDodajKsiazki(biblioteka.getKatalog().getKsiazki().get(textFieldTytul.getText()));
                    textFieldTytul.setText("");
                    textFieldAutor.setText("");
                    textFieldGatunek.setText("");
                } catch (DodawanieException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });

        itemUsunKsiazke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    biblioteka.skasujKsiazke(tableKsiazki.getValueAt(tableKsiazki.getSelectedRow(),0).toString());
                    reflashtableKsiazkiUsuwanie();
                } catch (UsuwanieException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });

        dodajCzytelnikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String imie = textFieldImie.getText();
                    String nazwisko = textFieldNazwisko.getText();
                    String data = textFieldData.getText();
                    String telefon = textFieldTelefon.getText();
                    biblioteka.dodaj_czytelnika(imie,nazwisko,data,telefon);
                    //czytelnikObserwacja(biblioteka);
                    reflashtableCzytelnik(biblioteka.getCzytelnicy().get(biblioteka.getCzytelnicy().size() - 1));
                    textFieldImie.setText("");
                    textFieldNazwisko.setText("");
                    textFieldData.setText("");
                    textFieldTelefon.setText("");
                }catch (StringIndexOutOfBoundsException e2){
                } catch (DodawanieException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });

        itemUsunCzytelnika.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                biblioteka.zwrotWszystkichKsiazek(tableCzytelnicy.getValueAt(tableCzytelnicy.getSelectedRow(),2).toString());
                biblioteka.usun_czytelnika(tableCzytelnicy.getValueAt(tableCzytelnicy.getSelectedRow(),2).toString());
                reflashTableCzytelnicy();
                reflashTableKsiazki();
            }
        });

        textFieldKsiazkaSzukaj.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textFieldKsiazkaSzukaj.getText();

                if (text.trim().length() == 0) {
                    rowSorterKsiazki.setRowFilter(null);
                 } else {
                    rowSorterKsiazki.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                 }
             }

            @Override
             public void removeUpdate(DocumentEvent e) {
                String text = textFieldKsiazkaSzukaj.getText();

                if (text.trim().length() == 0) {
                    rowSorterKsiazki.setRowFilter(null);
                } else {
                    rowSorterKsiazki.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

        textFieldSzukajCzytelnik.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textFieldSzukajCzytelnik.getText();

                if (text.trim().length() == 0) {
                    rowSorterCzytelnicy.setRowFilter(null);
                } else {
                    rowSorterCzytelnicy.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textFieldSzukajCzytelnik.getText();

                if (text.trim().length() == 0) {
                    rowSorterCzytelnicy.setRowFilter(null);
                } else {
                    rowSorterCzytelnicy.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });


        tableKsiazki.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tableKsiazki.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tableKsiazki.getRowCount()) {
                    tableKsiazki.setRowSelectionInterval(r, r);
                } else {
                    tableKsiazki.clearSelection();
                }
                int rowindex = tableKsiazki.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = menuKsiazki;
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        tableCzytelnicy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tableCzytelnicy.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tableCzytelnicy.getRowCount()) {
                    tableCzytelnicy.setRowSelectionInterval(r, r);
                } else {
                    tableCzytelnicy.clearSelection();
                }
                int rowindex = tableCzytelnicy.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = menuCzytelnik;
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        // odpalam nowe okno wypozyczenia
        itemWypozycz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    biblioteka.getKatalog().getKsiazki().get(tableKsiazki.getValueAt(tableKsiazki.getSelectedRow(),0).toString()).isStanCheck();
                    JFrame frame = new JFrame("Wypożyczenie");
                    WypożyczeniaGUI okno = new WypożyczeniaGUI(biblioteka,biblioteka.szukajKsiazki(tableKsiazki.getValueAt(tableKsiazki.getSelectedRow(),0).toString()));
                    frame.setContentPane(okno.getPanel());
                    frame.pack();
                    frame.setVisible(true);
                    frame.setSize(500,200);   //wymiary
                } catch (StanException e1) {
                    JOptionPane.showMessageDialog(null,e1.getMessage());
            }

            }
        });
        // odpalam nowe okno uzytkownika
        itemPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Panel Czytelnika");
                PanelCzytelnikaGUI okno = new PanelCzytelnikaGUI(biblioteka,biblioteka.szukaj_czytelnika(tableCzytelnicy.getValueAt(tableCzytelnicy.getSelectedRow(),2).toString()));
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        reflashTableCzytelnicy();
                        }
                });
                frame.setContentPane(okno.getPanel());
                frame.pack();
                frame.setVisible(true);
                frame.setSize(700,400);   //wymiary
            }
        });

    };

      


    private void reflashTableKsiazki(){
        DefaultTableModel model =  (DefaultTableModel) tableKsiazki.getModel();

        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) { model.removeRow(i);
        }
        for(Map.Entry<String, Ksiazka> entry: biblioteka.getKatalog().getKsiazki().entrySet()){
            model.addRow(new Object[]{entry.getValue().getNazwa(), entry.getValue().getAutor(), entry.getValue().getGatunek(),entry.getValue().isStanString(), entry.getValue().getIndeks()});
        }
        tableKsiazki.setModel(model);
    }

    private void reflashTableCzytelnicy(){
        DefaultTableModel model = (DefaultTableModel) tableCzytelnicy.getModel();
        int rowCount = model.getRowCount();
        for(int i = rowCount - 1; i >= 0; i--){
            model.removeRow(i);
        }
        for(int i=0;i<biblioteka.getCzytelnicy().size();i++){
            model.addRow(new Object[]{biblioteka.getCzytelnicy().get(i).getImie(),biblioteka.getCzytelnicy().get(i).getNazwisko(),biblioteka.getCzytelnicy().get(i).getIndeks_czytelnika(),biblioteka.getCzytelnicy().get(i).getPosiadanieKsiazekString()});
        }
    }
    //dodawanie ksiazki
    private void reflashDodajKsiazki(Ksiazka ksiazka){

        DefaultTableModel model = (DefaultTableModel) tableKsiazki.getModel();
        model.addRow(new Object[]{ksiazka.getNazwa(), ksiazka.getAutor(),ksiazka.getGatunek(),ksiazka.isStanString(),ksiazka.getIndeks()});
    }
    // dodawanie nowego
    private void reflashtableCzytelnik(Czytelnik czytelnik){
        //dodawanie czytelnika
        DefaultTableModel model = (DefaultTableModel) tableCzytelnicy.getModel();
        model.addRow(new Object[]{czytelnik.getImie(), czytelnik.getNazwisko(),czytelnik.getIndeks_czytelnika(),czytelnik.getPosiadanieKsiazekString()});
        tableCzytelnicy.setModel(model);
    }
    // usuwanie ksiazki
    private void reflashtableKsiazkiUsuwanie(){

        DefaultTableModel model = (DefaultTableModel) tableKsiazki.getModel();
        model.removeRow(tableKsiazki.getSelectedRow());
        tableKsiazki.setModel(model);
    }
    // prompt text
    private void reflashTextField(){
        // textFieldTytul.putClientProperty();
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("BibliotekaGUI");
        Biblioteka biblioteka = new Biblioteka();
        biblioteka.odczytKataloguZPliku("biblioteczka.bin");
        biblioteka.odczytCzytelnikowZPliku("czytelnicy.bin");

        frame.setContentPane(new BibliotekaGUI(biblioteka).panel1);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    biblioteka.zapiszDoPlikuKatalog("biblioteczka.bin");
                    biblioteka.zapiszDoPlikuCzytelnicy("czytelnicy.bin");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200,700);   //wymiary
    }

    // logika.Obserwator
    @Override
    public void informuj() {
        reflashTableKsiazki();
        reflashTableCzytelnicy();
    }
}