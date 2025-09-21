package SOLUTIONS.src.zad_2;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProgrammerTableModel extends AbstractTableModel {
    private final String[] columns = {"Ime", "Email", "Jezik", "Zaposlenje"};
    private List<Programmer> data = new ArrayList<>();

    public void setData(List<Programmer> programmers) {
        this.data = new ArrayList<>(programmers);
        fireTableDataChanged();
    }

    public Programmer getAt(int row) {
        return (row >= 0 && row < data.size()) ? data.get(row) : null;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Programmer p = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getName();
            case 1: return p.getEmail();
            case 2: return p.getLanguage().getDisplayName();
            case 3: return p.getEmploymentType().getDisplayName();
            default: return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}