import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * This class is an extension of the AbstractTableModel class.
 * it is specialized to store tasks, see {@link Task }
 */
public class TaskTableModel extends AbstractTableModel {

    private final static String[] COLUMN_NAMES = {"company", "status"};
    List<Task> data;

    public TaskTableModel(List<Task> list) {
        data = list;
    }

    public String getColumnName(int col) {
        if (col >= 2 || col < 0) {
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

    // returns the name or status of a given task
    public Object getValueAt(int row, int col) {
        if (col == 0) { // returns name
            return data.get(row).getName();
        } else if (col == 1) { // returns status
            return data.get(row).getStatus();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // returns the task of the given row
    public Task getTask(int row) {
        validateRow(row);
        return data.get(row);
    }

    // modifies the task on the given row
    public void setValueAt(Task task, int row) {
        validateRow(row);
        data.get(row).setName(task.getName());
        data.get(row).setStatus(task.getStatus());
        fireTableDataChanged();
    }

    // remove the task on the given row
    public void remove(int row) {
        validateRow(row);
        data.remove(row);
        fireTableRowsDeleted(row,row);
    }

    // checks whether the list is empty or not
    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void addRow(Task task) {
        data.add(task);
    }

    // may be set on true later
    // EventHandler needs to be implemented in that case
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    // validates the given row,
    // throws an IndexOutOfBoundsException when the row is invalid
    private void validateRow(int row) {
        if (row >= data.size() || row < 0) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }
}