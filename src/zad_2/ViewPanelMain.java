package SOLUTIONS.src.zad_2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewPanelMain extends JPanel {
    private final ProgrammerTableModel tableModel = new ProgrammerTableModel();
    private final JTable table = new JTable(tableModel);

    public ViewPanelMain() {
        setBorder(BorderFactory.createTitledBorder("Svi programeri"));
        setLayout(new BorderLayout());
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refresh(AppModel model) {
        List<Programmer> list = model.getAllProgrammers();
        tableModel.setData(list);
        System.out.println("[VIEW_MAIN] Osvježeno s " + list.size() + " programera.");
    }
}