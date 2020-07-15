import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // keyListener
        KeyListener listener = new EventHandler();
        addKeyListener(listener);

        // label to display when the table is empty
        emptyListLabel = new JLabel
                ("<html>there are currently no tasks," +
                        "<br> click [add] to add a task</html>");

        // table panel
        JPanel tablePanel = new JPanel();
        data = new ArrayList<>(readDocument().getTasks());
        model = new TaskTableModel(data);
        table = new JTable(model);

        tablePanel.add(table);
        tablePanel.add(emptyListLabel);
        refresh();

        // buttonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

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
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(buttonPanel);
        contentPane.add(tablePanel);

        getContentPane().add(contentPane, BorderLayout.WEST);
        setVisible(true);
    }

    private Tasks readDocument() {
        try {
            return JAXBUtils.read(src);
        } catch (JAXBException | NullPointerException ignored) {
        }
        return new Tasks();
    }

    private ActionListener addPressed(JFrame parent) {
        return e -> {
            TaskDialog dialog = new TaskDialog(parent);
            Task task = dialog.display();

            if (TaskDialog.result == 0) {
                try {
                    Tasks tasks = JAXBUtils.read(src);
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

    private ActionListener editPressed(JFrame parent) {
        return e -> {
            int row = table.getSelectedRow();
            if (isEmpty) {
                JOptionPane.showMessageDialog(this, "there are no tasks to edit, add \na task to be able to edit tasks");
            } else if (row == -1) {
                JOptionPane.showMessageDialog(this, "please select a task");
            } else {
                Task task = model.getTask(row);
                TaskDialog dialog = new EditDialog(parent, task);
                Task updated = dialog.display();
                if (TaskDialog.result == 0) {
                    try {
                        JAXBUtils.read(src);
                        JAXBUtils.edit(updated);
                        update(updated, updateType.edit);
                    } catch (JAXBException jaxbException) {
                        jaxbException.printStackTrace();
                    }
                }
            }
        };
    }

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
                        JAXBUtils.read(src);
                        JAXBUtils.delete(task.getId());
                        update(task, updateType.delete);
                    } catch (JAXBException jaxbException) {
                        jaxbException.printStackTrace();
                    }
                }
            }
        };
    }

    enum updateType {
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

