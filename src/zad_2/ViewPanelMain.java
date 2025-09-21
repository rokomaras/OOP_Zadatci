package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import javax.swing.*; // Uvozimo Swing komponente za GUI
import java.awt.*;     // Uvozimo AWT komponente za layout
import java.util.List; // Uvozimo List interface

// Panel klasa za prikaz glavne tablice sa svim programerima
public class ViewPanelMain extends JPanel {
    private final ProgrammerTableModel tableModel = new ProgrammerTableModel(); // Model podataka za tablicu
    private final JTable table = new JTable(tableModel); // JTable komponenta za prikaz podataka

    // Konstruktor koji postavlja komponente panela
    public ViewPanelMain() {
        setBorder(BorderFactory.createTitledBorder("Svi programeri")); // Postavlja okvir s naslovom
        setLayout(new BorderLayout()); // Postavlja BorderLayout za raspored komponenti
        table.setFillsViewportHeight(true); // Postavlja tablicu da ispuni visinu viewport-a
        add(new JScrollPane(table), BorderLayout.CENTER); // Dodaje tablicu u scroll pane u središte
    }

    // Metoda za osvježavanje prikaza s podacima iz modela
    public void refresh(AppModel model) {
        List<Programmer> list = model.getAllProgrammers(); // Dohvaća sve programere iz modela
        tableModel.setData(list); // Postavlja podatke u model tablice
        // Ispisuje poruku o osvježavanju s brojem programera
        System.out.println("[VIEW_MAIN] Osvježeno s " + list.size() + " programera.");
    }
}