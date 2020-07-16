import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The TaskDialog is an extension of JDialog. It creates a dialog wherein
 * you can create a task. It also validates the tasks before returning one.
 */
public class TaskDialog extends JDialog implements ActionListener {

    /**
     * ok button is pressed
     */
    int OK_OPTION = 0;

    /**
     * cancel button is pressed
     */
    int CANCEL_OPTION = -1;

    /**
     * the result of which button is clicked.
     * it's either 0 or -1
     */
    public static int result;

    /**
     * label to be displayed when the name or status is empty after pressing the ok button
     */
    private JLabel emptyName, emptyStatus;

    String name, status;
    JTextField nameTxt, statusTxt;
    JButton ok, cancel;

    /**
     * Creates a dialog with the specified title and
     * with the specified parent frame
     *
     * @param parent the frame from which the dialog is displayed
     * @param title the title of the dialog
     */
    public TaskDialog(Frame parent, String title) {
        super(parent, title, true);
        this.setResizable(false);
        addComponents(parent);
    }

    /**
     * adds the components to the parent frame
     * @param parent a frame filled with components
     */
    public void addComponents(Frame parent) {
        Point loc = parent.getLocation();
        setLocation(loc.x + 80, loc.y + 80);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLbl = new JLabel("name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLbl, constraints);

        nameTxt = new JTextField(30);
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameTxt, constraints);

        emptyName = new JLabel("name must not be empty");
        emptyName.setForeground(new Color(0, 0, 0, 0));
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(emptyName, constraints);

        JLabel statusLbl = new JLabel("status:");
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(statusLbl, constraints);

        statusTxt = new JTextField(30);
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(statusTxt, constraints);

        emptyStatus = new JLabel("status must not be empty");
        emptyStatus.setForeground(new Color(0, 0, 0, 0));
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(emptyStatus, constraints);

        ok = new JButton("ok");
        ok.addActionListener(this);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 8;
        panel.add(ok, constraints);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = 8;
        panel.add(cancel, constraints);
        getContentPane().add(panel);
        pack();
    }

    /**
     * validates whether the name and status are valid or not.
     * Empty or blank strings are considered invalid
     *
     * @return true if both are valid, false otherwise
     */
    boolean validateTask() {
        if (name.isEmpty() || status.isEmpty()) {
            if (isNullOrWhitespace(name)) {
                emptyName.setForeground(Color.RED);
                emptyName.setText("name must not be empty");
                System.out.println("name is empty");
            } else {
                emptyName.setForeground(new Color(0, 0, 0, 0));
            }
            if (isNullOrWhitespace(status)) {
                emptyStatus.setForeground(Color.RED);
                emptyStatus.setText("status must not be empty");
                System.out.println("status is empty");
            } else {
                emptyStatus.setForeground(new Color(0, 0, 0, 0));
            }
            return false;
        }
        return true;
    }

    /**
     * checks if the given string is null or contains whitespace only
     * @param string the string to be checked
     * @return true if the string is null or contains whitespace only
     */
    private boolean isNullOrWhitespace(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * displays the JDialog that returns a task
     *
     * @return a task
     */
    public Task display() {
        this.setVisible(true);
        return new Task(name, status);
    }

    /**
     *  closes the dialog when the task is valid or the cancel button is pressed
     *
     * @param e either the click of the ok or the cancel button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ok) {
            result = OK_OPTION;
            name = nameTxt.getText();
            status = statusTxt.getText();
            if (validateTask()) {
                dispose();
            }
        } else {
            result = CANCEL_OPTION;
            name = null;
            status = null;
            dispose();
        }
    }
}