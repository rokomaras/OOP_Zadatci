package SOLUTIONS.src.zad_2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewPanelAUX extends JPanel {
    private final ProgrammerTableModel tableModel = new ProgrammerTableModel();
    private final JTable table = new JTable(tableModel);
    private final JLabel header = new JLabel("Filtrirano po jeziku: —");

    private Language currentLanguage = null;

    public ViewPanelAUX() {
        setBorder(BorderFactory.createTitledBorder("Pregled po jeziku"));
        setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void showLanguage(Language language, AppModel model) {
        this.currentLanguage = language;
        header.setText("Filtrirano po jeziku: " + (language == null ? "—" : language.getDisplayName()));
        if (language == null) {
            tableModel.setData(java.util.List.of());
            System.out.println("[VIEW_AUX] Nije odabran jezik. Tablica je prazna.");
        } else {
            List<Programmer> list = model.getByLanguage(language);
            tableModel.setData(list);
            System.out.println("[VIEW_AUX] Jezik " + language.getDisplayName() + " -> " + list.size() + " programera.");
        }
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }
}