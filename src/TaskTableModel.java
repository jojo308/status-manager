import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

    private final static String[] COLUMN_NAMES = {"company", "status"};
    List<Task> data;

    public TaskTableModel(List<Task> list) {
        data = list;
    }

    public String getColumnName(int col) {
        if (col >= getColumnCount()) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        return COLUMN_NAMES[col];
    }

    public int getRowCount() {
        return (data == null) ? 0 : data.size();
    }

    public int getColumnCount() {
        return (COLUMN_NAMES == null) ? 0 : COLUMN_NAMES.length;
    }

    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return data.get(row).getName();
        } else if (col == 1) {
            return data.get(row).getStatus();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setValueAt(Object value, int row, int col) {
        if (value instanceof String) {
            if (col == 0) {
                data.get(row).setName(value.toString());
            } else if (col == 1) {
                data.get(row).setStatus(value.toString());
            }
        } else {
            throw new IllegalArgumentException("value must be a string");
        }
        fireTableCellUpdated(row, col);
    }

    public void remove(int row) {
        data.remove(row);
        fireTableRowsDeleted(row,row);
    }

    public void addRow(Task task) {
        data.add(task);
    }

    public Task getTask(int row) {
        return data.get(row);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }


}