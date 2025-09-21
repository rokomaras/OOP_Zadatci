package SOLUTIONS.src.zad_2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private final AppModel model = new AppModel();

    private final FormPanel formPanel = new FormPanel();
    private final ViewPanelMain viewPanelMain = new ViewPanelMain();
    private final ViewPanelAUX viewPanelAUX = new ViewPanelAUX();

    public MainFrame() {
        super("Programeri - evidencija");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(buildMenuBar());

        add(formPanel, BorderLayout.WEST);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setResizeWeight(0.6);
        split.setTopComponent(viewPanelMain);
        split.setBottomComponent(viewPanelAUX);
        add(split, BorderLayout.CENTER);

        formPanel.setFormSubmitListener(p -> {
            System.out.println("[APP] Primljen submit iz forme: " + p);
            boolean added = model.addProgrammer(p);
            if (!added) {
                System.out.println("[APP] Duplikat - programer već postoji.");
                JOptionPane.showMessageDialog(this, "Duplikat: programer već postoji.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshViews();
        });

        System.out.println("[APP] Glavni prozor inicijaliziran.");
        refreshViews();
    }

    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem miSave = new JMenuItem("Spremi podatke");
        JMenuItem miLoad = new JMenuItem("Učitaj podatke");
        JMenuItem miExit = new JMenuItem("Izlaz iz aplikacije");

        miSave.addActionListener(e -> onSave());
        miLoad.addActionListener(e -> onLoad());
        miExit.addActionListener(e -> onExit());

        file.add(miSave);
        file.add(miLoad);
        file.addSeparator();
        file.add(miExit);

        JMenu view = new JMenu("View");
        JMenuItem miFilter = new JMenuItem("Filter po jeziku...");
        miFilter.addActionListener(e -> onFilterByLanguage());
        view.add(miFilter);

        bar.add(file);
        bar.add(view);
        return bar;
    }

    private void onSave() {
        System.out.println("[APP] Klik na 'Spremi podatke'.");
        try {
            model.saveToFile();
            JOptionPane.showMessageDialog(this, "Podaci su spremljeni u " + AppModel.DEFAULT_PATH, "Spremanje", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println("[APP] Greška pri spremanju: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Greška pri spremanju: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLoad() {
        System.out.println("[APP] Klik na 'Učitaj podatke'.");
        try {
            model.loadFromFile();
            refreshViewsAfterLoad();
            JOptionPane.showMessageDialog(this, "Podaci su učitani iz " + AppModel.DEFAULT_PATH, "Učitavanje", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println("[APP] Greška pri učitavanju: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onExit() {
        System.out.println("[APP] Izlaz iz aplikacije.");
        dispose();
        System.exit(0);
    }

    private void onFilterByLanguage() {
        Language selection = (Language) JOptionPane.showInputDialog(
                this,
                "Odaberite jezik:",
                "Filter po jeziku",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Language.values(),
                Language.JAVA
        );
        if (selection != null) {
            System.out.println("[APP] Odabran filter jezika: " + selection.getDisplayName());
            viewPanelAUX.showLanguage(selection, model);
        } else {
            System.out.println("[APP] Filter jezika otkazan.");
        }
    }

    private void refreshViews() {
        System.out.println("[APP] Osvježavanje prikaza (Main i AUX).");
        viewPanelMain.refresh(model);
        Language current = viewPanelAUX.getCurrentLanguage();
        if (current != null) {
            viewPanelAUX.showLanguage(current, model);
        }
    }

    private void refreshViewsAfterLoad() {
        System.out.println("[APP] Osvježavanje prikaza nakon učitavanja podataka.");
        viewPanelMain.refresh(model);
        Language current = viewPanelAUX.getCurrentLanguage();
        viewPanelAUX.showLanguage(current, model);
    }
}