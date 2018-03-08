import logika.Biblioteka;
import logika.Czytelnik;
import logika.Ksiazka;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class PanelCzytelnikaGUI {
    private JTextField textFieldImie;
    private JTextField textFieldNazwisko;
    private JTextField textFieldData;
    private JTextField textFieldIndeks;
    private JTextField textFieldTelefon;
    private JTable tableKsiazki;
    private JButton zapiszZmianyButton;
    private JPanel panel;
    Czytelnik czytelnik;
    Biblioteka biblioteka;
    public PanelCzytelnikaGUI(Biblioteka biblioteka, Czytelnik czytelnik) {
        this.czytelnik = czytelnik;
        this.biblioteka = biblioteka;

        // tabela ksiazek czytelnika
        String[] columnsNamesKsiazki = new String[] {"Tytuł", "Autor", "Gatunek", "Stan", "Indeks"};
        Object[][] dataKsiazki = {};
        DefaultTableModel modelKsiazki;
        modelKsiazki = new DefaultTableModel(dataKsiazki,columnsNamesKsiazki);
        tableKsiazki.setModel(modelKsiazki);
        reflashTableKsiazkiCzytelnika();
        TableRowSorter<TableModel> rowSorterKsiazki = new TableRowSorter<>(tableKsiazki.getModel());
        tableKsiazki.setRowSorter(rowSorterKsiazki);

        // pola tekstowe informacujne
        textFieldImie.setText(czytelnik.getImie());
        textFieldNazwisko.setText(czytelnik.getNazwisko());
        textFieldData.setText(czytelnik.getData_urodzenia());
        textFieldIndeks.setText(czytelnik.getIndeks_czytelnika());
        textFieldTelefon.setText(czytelnik.getNumer_telefonu());

        // menu dla ksiażek
        JPopupMenu menuKsiazki = new JPopupMenu();
        JMenuItem itemZwroc = new JMenuItem("Zwrot");
        menuKsiazki.add(itemZwroc);


        zapiszZmianyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                czytelnik.setImie(textFieldImie.getText());
                czytelnik.setNazwisko(textFieldNazwisko.getText());
                czytelnik.setData_urodzenia(textFieldData.getText());
                czytelnik.setNumer_telefonu(textFieldTelefon.getText());
                // dodaj obserwatora
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
        itemZwroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                biblioteka.zwrocKsiazke(tableKsiazki.getValueAt(tableKsiazki.getSelectedRow(),0).toString(),czytelnik.getIndeks_czytelnika());
                reflashTableKsiazkiCzytelnika();
            }
        });
    }


    private void reflashTableKsiazkiCzytelnika(){
        DefaultTableModel model =  (DefaultTableModel) tableKsiazki.getModel();

        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for(Map.Entry<String, Ksiazka> entry: czytelnik.getWypozyczone_ksiazki().entrySet()){
            model.addRow(new Object[]{entry.getValue().getNazwa(), entry.getValue().getAutor(), entry.getValue().getGatunek(),entry.getValue().isStan(), entry.getValue().getIndeks()});
        }
        tableKsiazki.setModel(model);
    }
    // getter panelu
    public JPanel getPanel() {
        return panel;
    }

}
