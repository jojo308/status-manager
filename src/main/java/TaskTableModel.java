import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * This class is an extension of the AbstractTableModel class.
 * it is specialized to store tasks, see {@link Task } and {@link Tasks}
 */
public class TaskTableModel extends AbstractTableModel {

    private final static String[] COLUMN_NAMES = {"company", "status"};
    List<Task> data;

    /**
     * constructs a tableModel designed for Tasks
     *
     * @param list a list of tasks
     */
    public TaskTableModel(List<Task> list) {
        data = list;
    }

    /**
     * returns the name of the specified columns
     *
     * @param col name (0) or status (1)
     * @return the name of the specified column
     */
    public String getColumnName(int col) {
        if (col >= 2 || col < 0) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        return COLUMN_NAMES[col];
    }

    /**
     * returns the amount of rows in the model
     *
     * @return the amount of rows
     */
    public int getRowCount() {
        return (data == null) ? 0 : data.size();
    }

    /**
     * returns the amount of columns in the model
     *
     * @return the amount of columns
     */
    public int getColumnCount() {
        return (COLUMN_NAMES == null) ? 0 : COLUMN_NAMES.length;
    }

    /**
     * returns the name or status on the specified cell
     *
     * @param row the row
     * @param col name (0) or status (1)
     * @return the name or status on the specified cell
     */
    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return data.get(row).getName();
        } else if (col == 1) {
            return data.get(row).getStatus();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * returns a task at the specified row
     *
     * @return the task at the specified row
     */
    public Task getTask(int row) {
        return data.get(row);
    }

    /**
     * sets the task on the specified row
     *
     * @param task the new task
     * @param row  the row whose value is to be changed
     */
    public void setValueAt(Task task, int row) {
        data.get(row).setName(task.getName());
        data.get(row).setStatus(task.getStatus());
        fireTableDataChanged();
    }

    /**
     * adds a new tasks at the bottom of the table
     *
     * @param task the task
     */
    public void addRow(Task task) {
        data.add(task);
    }

    /**
     * removes the row on the specified row
     *
     * @param row the row whose value is to be removed
     */
    public void remove(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    /**
     * checks whether the list is empty or not
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * returns whether a cell is editable or not
     *
     * The current version of this application doesn't allow
     * cells to be edited. This may change in future releases.
     *
     * @return false because cells are not allowed to be editable
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}