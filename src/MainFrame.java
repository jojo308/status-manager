import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private final String src = "resources\\taskList.xml";

    private final static String TITLE = "Status Manager";
    private final static int FRAME_WIDTH = 300;
    private final static int FRAME_HEIGHT = 500;

    private JTable table;
    private TaskTableModel model;
    ArrayList<Task> data;

    public MainFrame() {
        setTitle(TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // keyListener
        KeyListener listener = new EventHandler();
        addKeyListener(listener);

        JLabel emptyListLabel = new JLabel
                ("<html>there are currently no tasks," +
                        "<br> click [add] to add a task</html>");

        JPanel tablePanel = new JPanel();

        // retrieve data from XML file and put in in a table
        try {
            table = refresh();
            tablePanel.add(table);
        } catch (NullPointerException e) {
            tablePanel.add(emptyListLabel);
        }

        // contentPane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // buttonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton addBtn = new JButton("add");
        addBtn.addActionListener(addPressed(this));

        JButton editBtn = new JButton("edit");
        editBtn.addActionListener(editPressed(this));

        JButton deleteBtn = new JButton("delete");
        deleteBtn.addActionListener(deletePressed(this));

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        contentPane.add(buttonPanel);
        contentPane.add(tablePanel);

        getContentPane().add(contentPane, BorderLayout.WEST);
        setVisible(true);
    }

    private List<Task> readDocument() {
        try {
            return JAXBUtils.read(src).getTasks();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ActionListener addPressed(JFrame parent) {
        return e -> {
            CustomDialog dialog = new CustomDialog(parent);
            Task task = dialog.display();

            if (CustomDialog.result == 0) {
                try {
                    JAXBUtils.read(src);
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
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "please select a task");
            } else {
                Task task = model.getTask(row);
                CustomDialog dialog = new EditDialog(parent, task);
                Task updated = dialog.display();

                if (CustomDialog.result == 0) {
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
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "please select a task");
            } else {
                Task task = model.getTask(row);
                int result = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete this task?\n" +
                        "it can't be undone later", "delete task", JOptionPane.YES_NO_OPTION);

                if (result == 0) {
                    try {
                        JAXBUtils.read(src);
                        JAXBUtils.delete(task.getId());
                        update(task, updateType.delete);
                    } catch (JAXBException jaxbException) {
                        jaxbException.printStackTrace();
                    }
                } else {
                    System.out.println("action cancelled");
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
                model.setValueAt(task.getName(), row, 0);
                model.setValueAt(task.getStatus(), row, 1);
                break;
            case delete:
                model.remove(row);
                break;
        }
        System.out.println("updated " + task.toString() + " (" + type + ")");
        model.fireTableDataChanged(); //todo edit task properly
    }

    public JTable refresh() {
        data = new ArrayList<>(readDocument());
        model = new TaskTableModel(data);
        return new JTable(model);
    }

    public boolean isFocusable() {
        return true;
    }
}

