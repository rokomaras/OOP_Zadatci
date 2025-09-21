package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import javax.swing.table.AbstractTableModel; // Uvozimo AbstractTableModel za kreiranje table modela
import java.util.ArrayList;                 // Uvozimo ArrayList za dinamične liste
import java.util.List;                      // Uvozimo List interface

// Model klasa za JTable koja prikazuje podatke o programerima
public class ProgrammerTableModel extends AbstractTableModel {
    // Niz s nazivima stupaca tablice
    private final String[] columns = {"Ime", "Email", "Jezik", "Zaposlenje"};
    // Lista programera koja se prikazuje u tablici
    private List<Programmer> data = new ArrayList<>();

    // Metoda za postavljanje novih podataka u tablicu
    public void setData(List<Programmer> programmers) {
        this.data = new ArrayList<>(programmers); // Kreira kopiju liste programera
        fireTableDataChanged(); // Obavještava JTable da su se podaci promijenili
    }

    // Metoda koja vraća programera na određenom redu
    public Programmer getAt(int row) {
        // Provjerava je li red valjan i vraća programera ili null
        return (row >= 0 && row < data.size()) ? data.get(row) : null;
    }

    // Implementira AbstractTableModel metodu za broj redova
    @Override
    public int getRowCount() {
        return data.size(); // Vraća broj programera u listi
    }

    // Implementira AbstractTableModel metodu za broj stupaca
    @Override
    public int getColumnCount() {
        return columns.length; // Vraća broj stupaca u tablici
    }

    // Implementira AbstractTableModel metodu za naziv stupca
    @Override
    public String getColumnName(int column) {
        return columns[column]; // Vraća naziv stupca na određenoj poziciji
    }

    // Implementira AbstractTableModel metodu za dohvaćanje vrijednosti ćelije
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Programmer p = data.get(rowIndex); // Dohvaća programera na određenom redu
        // Switch statement za vraćanje odgovarajuće vrijednosti ovisno o stupcu
        switch (columnIndex) {
            case 0: return p.getName();                           // Vraća ime programera
            case 1: return p.getEmail();                          // Vraća email programera
            case 2: return p.getLanguage().getDisplayName();     // Vraća naziv programskog jezika
            case 3: return p.getEmploymentType().getDisplayName(); // Vraća tip zaposlenja
            default: return "";                                   // Vraća prazan string za nepoznati stupac
        }
    }

    // Implementira AbstractTableModel metodu za provjeru je li ćelija editabilna
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Sve ćelije su samo za čitanje (nisu editabilne)
    }
}