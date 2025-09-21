package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import javax.swing.*; // Uvozimo Swing komponente za GUI
import java.awt.*;     // Uvozimo AWT komponente za layout
import java.io.IOException; // Uvozimo IOException za rukovanje greškama

// Glavna klasa prozora aplikacije koja nasljeđuje od JFrame
public class MainFrame extends JFrame {
    private final AppModel model = new AppModel(); // Model podataka aplikacije

    // GUI paneli aplikacije
    private final FormPanel formPanel = new FormPanel();           // Panel za unos novih programera
    private final ViewPanelMain viewPanelMain = new ViewPanelMain(); // Panel za prikaz svih programera
    private final ViewPanelAUX viewPanelAUX = new ViewPanelAUX();   // Panel za filtriranje po jeziku

    // Konstruktor glavnog prozora
    public MainFrame() {
        super("Programeri - evidencija"); // Postavlja naslov prozora
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Postavlja da se aplikacija zatvori kada se zatvori prozor
        setSize(900, 600);              // Postavlja veličinu prozora
        setLocationRelativeTo(null);    // Centrira prozor na ekranu
        setLayout(new BorderLayout());  // Postavlja BorderLayout za glavni raspored

        setJMenuBar(buildMenuBar()); // Postavlja menu bar

        add(formPanel, BorderLayout.WEST); // Dodaje form panel na lijevu stranu

        // Kreira split pane za vertikalno dijeljenje prikaza
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setResizeWeight(0.6);           // Postavlja omjer veličine (60% gore, 40% dolje)
        split.setTopComponent(viewPanelMain); // Postavlja glavni prikaz gore
        split.setBottomComponent(viewPanelAUX); // Postavlja filtrirani prikaz dolje
        add(split, BorderLayout.CENTER);      // Dodaje split pane u središte

        // Postavlja listener za slanje forme
        formPanel.setFormSubmitListener(p -> {
            System.out.println("[APP] Primljen submit iz forme: " + p); // Ispisuje primljenog programera
            boolean added = model.addProgrammer(p); // Pokušava dodati programera u model
            // Provjerava je li programer dodan ili već postoji
            if (!added) {
                System.out.println("[APP] Duplikat - programer već postoji."); // Ispisuje poruku o duplikatu
                // Prikazuje poruku korisniku o duplikatu
                JOptionPane.showMessageDialog(this, "Duplikat: programer već postoji.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshViews(); // Osvježava prikaze nakon dodavanja
        });

        System.out.println("[APP] Glavni prozor inicijaliziran."); // Ispisuje poruku o inicijalizaciji
        refreshViews(); // Početno osvježavanje prikaza
    }

    // Privatna metoda za kreiranje menu bar-a
    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar(); // Kreira novi menu bar

        // Kreira File menu
        JMenu file = new JMenu("File");
        JMenuItem miSave = new JMenuItem("Spremi podatke");   // Menu item za spremanje
        JMenuItem miLoad = new JMenuItem("Učitaj podatke");   // Menu item za učitavanje
        JMenuItem miExit = new JMenuItem("Izlaz iz aplikacije"); // Menu item za izlaz

        // Dodaje action listener-e za menu item-e
        miSave.addActionListener(e -> onSave()); // Poziva metodu za spremanje
        miLoad.addActionListener(e -> onLoad()); // Poziva metodu za učitavanje
        miExit.addActionListener(e -> onExit()); // Poziva metodu za izlaz

        // Dodaje menu item-e u File menu
        file.add(miSave);         // Dodaje "Spremi podatke"
        file.add(miLoad);         // Dodaje "Učitaj podatke"
        file.addSeparator();      // Dodaje separator liniju
        file.add(miExit);         // Dodaje "Izlaz iz aplikacije"

        // Kreira View menu
        JMenu view = new JMenu("View");
        JMenuItem miFilter = new JMenuItem("Filter po jeziku..."); // Menu item za filtriranje
        miFilter.addActionListener(e -> onFilterByLanguage());     // Dodaje action listener
        view.add(miFilter);       // Dodaje menu item u View menu

        // Dodaje menu-ove u menu bar
        bar.add(file); // Dodaje File menu
        bar.add(view); // Dodaje View menu
        return bar;    // Vraća kreirani menu bar
    }

    // Privatna metoda za rukovanje spremanjem podataka
    private void onSave() {
        System.out.println("[APP] Klik na 'Spremi podatke'."); // Ispisuje poruku o kliku
        try {
            model.saveToFile(); // Poziva metodu modela za spremanje
            // Prikazuje poruku o uspješnom spremanju
            JOptionPane.showMessageDialog(this, "Podaci su spremljeni u " + AppModel.DEFAULT_PATH, "Spremanje", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println("[APP] Greška pri spremanju: " + ex.getMessage()); // Ispisuje grešku u konzolu
            // Prikazuje grešku korisniku
            JOptionPane.showMessageDialog(this, "Greška pri spremanju: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Privatna metoda za rukovanje učitavanjem podataka
    private void onLoad() {
        System.out.println("[APP] Klik na 'Učitaj podatke'."); // Ispisuje poruku o kliku
        try {
            model.loadFromFile(); // Poziva metodu modela za učitavanje
            refreshViewsAfterLoad(); // Osvježava prikaze nakon učitavanja
            // Prikazuje poruku o uspješnom učitavanju
            JOptionPane.showMessageDialog(this, "Podaci su učitani iz " + AppModel.DEFAULT_PATH, "Učitavanje", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println("[APP] Greška pri učitavanju: " + ex.getMessage()); // Ispisuje grešku u konzolu
            // Prikazuje grešku korisniku
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Privatna metoda za rukovanje izlazom iz aplikacije
    private void onExit() {
        System.out.println("[APP] Izlaz iz aplikacije."); // Ispisuje poruku o izlasku
        dispose();     // Oslobađa resurse prozora
        System.exit(0); // Izlazi iz aplikacije
    }

    // Privatna metoda za rukovanje filtriranjem po programskom jeziku
    private void onFilterByLanguage() {
        // Prikazuje input dijalog s opcijama programskih jezika
        Language selection = (Language) JOptionPane.showInputDialog(
                this,                    // Roditeljski prozor
                "Odaberite jezik:",      // Poruka u dijalogu
                "Filter po jeziku",      // Naslov dijaloga
                JOptionPane.QUESTION_MESSAGE, // Tip poruke
                null,                    // Ikona (null za zadanu)
                Language.values(),       // Opcije za odabir
                Language.JAVA           // Zadana opcija
        );
        // Provjerava je li korisnik odabrao jezik
        if (selection != null) {
            System.out.println("[APP] Odabran filter jezika: " + selection.getDisplayName()); // Ispisuje odabrani jezik
            viewPanelAUX.showLanguage(selection, model); // Prikazuje programere za odabrani jezik
        } else {
            System.out.println("[APP] Filter jezika otkazan."); // Ispisuje poruku o otkazivanju
        }
    }

    // Privatna metoda za osvježavanje svih prikaza
    private void refreshViews() {
        System.out.println("[APP] Osvježavanje prikaza (Main i AUX)."); // Ispisuje poruku o osvježavanju
        viewPanelMain.refresh(model); // Osvježava glavni prikaz s podacima iz modela
        Language current = viewPanelAUX.getCurrentLanguage(); // Dohvaća trenutno odabrani jezik za filtriranje
        // Ako je jezik odabran, osvježava filtrirani prikaz
        if (current != null) {
            viewPanelAUX.showLanguage(current, model); // Osvježava prikaz s trenutnim filterom
        }
    }

    // Privatna metoda za osvježavanje prikaza nakon učitavanja podataka
    private void refreshViewsAfterLoad() {
        System.out.println("[APP] Osvježavanje prikaza nakon učitavanja podataka."); // Ispisuje poruku
        viewPanelMain.refresh(model); // Osvježava glavni prikaz
        Language current = viewPanelAUX.getCurrentLanguage(); // Dohvaća trenutni filter
        viewPanelAUX.showLanguage(current, model); // Osvježava filtrirani prikaz (čak i ako je current null)
    }
}