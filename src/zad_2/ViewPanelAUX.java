package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import javax.swing.*; // Uvozimo Swing komponente za GUI
import java.awt.*;     // Uvozimo AWT komponente za layout
import java.util.List; // Uvozimo List interface

// Pomoćni panel klasa za prikaz programera filtriranih po programskom jeziku
public class ViewPanelAUX extends JPanel {
    private final ProgrammerTableModel tableModel = new ProgrammerTableModel(); // Model podataka za tablicu
    private final JTable table = new JTable(tableModel); // JTable komponenta za prikaz podataka
    private final JLabel header = new JLabel("Filtrirano po jeziku: —"); // Label za prikaz trenutnog filtera

    private Language currentLanguage = null; // Trenutno odabrani jezik za filtriranje

    // Konstruktor koji postavlja komponente panela
    public ViewPanelAUX() {
        setBorder(BorderFactory.createTitledBorder("Pregled po jeziku")); // Postavlja okvir s naslovom
        setLayout(new BorderLayout()); // Postavlja BorderLayout za raspored komponenti
        header.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8)); // Postavlja padding za header
        add(header, BorderLayout.NORTH); // Dodaje header na vrh panela
        add(new JScrollPane(table), BorderLayout.CENTER); // Dodaje tablicu u scroll pane u središte
    }

    // Metoda za prikaz programera koji koriste određeni programski jezik
    public void showLanguage(Language language, AppModel model) {
        this.currentLanguage = language; // Postavlja trenutni jezik
        // Ažurira tekst header-a s nazivom odabranog jezika
        header.setText("Filtrirano po jeziku: " + (language == null ? "—" : language.getDisplayName()));
        // Provjerava je li jezik odabran
        if (language == null) {
            tableModel.setData(java.util.List.of()); // Postavlja praznu listu ako jezik nije odabran
            System.out.println("[VIEW_AUX] Nije odabran jezik. Tablica je prazna."); // Ispisuje poruku
        } else {
            List<Programmer> list = model.getByLanguage(language); // Dohvaća programere za određeni jezik
            tableModel.setData(list); // Postavlja podatke u model tablice
            // Ispisuje poruku s jezikom i brojem programera
            System.out.println("[VIEW_AUX] Jezik " + language.getDisplayName() + " -> " + list.size() + " programera.");
        }
    }

    // Getter metoda koja vraća trenutno odabrani jezik
    public Language getCurrentLanguage() {
        return currentLanguage; // Vraća trenutni jezik
    }
}