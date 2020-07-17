import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private final String src = "resources\\taskList.xml";
    private boolean isEmpty;

    private final JTable table;
    private final TaskTableModel model;
    ArrayList<Task> data;
    JLabel emptyListLabel;

    public MainFrame() {
        setTitle("Status Manager");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // label to display when the table is empty
        emptyListLabel = new JLabel
                ("<html>there are currently no tasks," +
                        "<br> click [add] to add a task</html>");

        // table panel
        JPanel tablePanel = new JPanel();
        data = new ArrayList<>(readDocument().getTasks());
        model = new TaskTableModel(data);
        table = new JTable(model);

        JScrollPane tableScroller = new JScrollPane(table);

        tablePanel.add(tableScroller);
        tablePanel.add(emptyListLabel);
        refresh();

        // buttonPanel
        JPanel buttonPanel = new JPanel();

        // buttons
        JButton addBtn = new JButton("add");
        addBtn.addActionListener(addPressed(this));

        JButton editBtn = new JButton("edit");
        editBtn.addActionListener(editPressed(this));

        JButton deleteBtn = new JButton("delete");
        deleteBtn.addActionListener(deletePressed(this));

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        // contentPane
        Container contentPane = getContentPane();

        contentPane.add(buttonPanel, BorderLayout.PAGE_START);
        contentPane.add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private Tasks readDocument() {
        try {
            return JAXBUtils.read(src);
        } catch (JAXBException ex) {
            System.out.println("an error has occurred and the document cannot be read");
            ex.printStackTrace();
        }
        return new Tasks();
    }

    /**
     * adds an actionListener which opens {@link AddDialog} that returns a new task.
     * The returned task wil be written to the file and the table is updated.
     *
     * @param parent the frame in which the dialog is displayed
     * @return an actionListener that opens a AddDialog
     */
    private ActionListener addPressed(JFrame parent) {
        return e -> {
            AddDialog dialog = new AddDialog(parent);
            Task task = dialog.display();

            if (AddDialog.result == 0) {
                try {
                    Tasks tasks = readDocument();
                    task.setId(tasks.getNextId());
                    tasks.add(task);
                    JAXBUtils.write(tasks);
                    update(task, updateType.add);
                } catch (JAXBException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * adds an actionListener which opens a {@link EditDialog} that returns the edited task.
     * The returned task wil be written to the file and the table is updated.
     *
     * @param parent the frame in which the dialog is displayed
     * @return an actionListener that opens an EditDialog
     */
    private ActionListener editPressed(JFrame parent) {
        return e -> {
            int row = table.getSelectedRow();
            if (isEmpty) {
                JOptionPane.showMessageDialog(this, "there are no tasks to edit, add \na task to be able to edit tasks");
            } else if (row == -1) {
                JOptionPane.showMessageDialog(this, "please select a task");
            } else {
                Task task = model.getTask(row);
                EditDialog dialog = new EditDialog(parent, task);
                Task updated = dialog.display();
                if (EditDialog.result == 0) {
                    try {
                        JAXBUtils.edit(updated);
                        update(updated, updateType.edit);
                    } catch (JAXBException jaxbException) {
                        jaxbException.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * adds an actionListener which opens a JOptionPane and deletes
     * the task if the user confirms it.
     *
     * @param parent the frame in which the dialog is displayed
     * @return an actionListener with a JOptionPane
     */
    private ActionListener deletePressed(JFrame parent) {
        return e -> {
            int row = table.getSelectedRow();
            if (isEmpty) {
                JOptionPane.showMessageDialog(this, "there are no tasks to delete, add \na task to be able to delete tasks");
            } else if (row == -1) {
                JOptionPane.showMessageDialog(this, "please select a task");
            } else {
                Task task = model.getTask(row);
                int result = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete this task?\n" +
                        "this can't be undone later", "delete task", JOptionPane.YES_NO_OPTION);

                if (result == 0) {
                    try {
                        JAXBUtils.delete(task.getId());
                        update(task, updateType.delete);
                    } catch (JAXBException jaxbException) {
                        jaxbException.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * the type of the update. Only needed for the update method
     */
    private enum updateType {
        add,
        edit,
        delete
    }

    /**
     * updates the JTable and returns the task that has been modified
     *
     * @param task the task that needs to be updated
     * @param type the type of update (add, edit or delete)
     */
    private void update(Task task, updateType type) {
        int row = table.getSelectedRow();
        switch (type) {
            case add:
                model.addRow(task);
                break;
            case edit:
                model.setValueAt(task, row);
                break;
            case delete:
                model.remove(row);
                break;
        }
        model.fireTableDataChanged();
        refresh();
    }

    /**
     * refreshes the table after an update. If the table
     * is empty, it will display a special message.
     */
    public void refresh() {
        if (model.isEmpty()) {
            table.setVisible(false);
            emptyListLabel.setVisible(true);
            isEmpty = true;
        } else {
            table.setVisible(true);
            emptyListLabel.setVisible(false);
            isEmpty = false;
        }
    }
}

