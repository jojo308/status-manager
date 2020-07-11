import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDialog extends JDialog implements ActionListener {

    int OK_OPTION = 0;
    int CANCEL_OPTION = -1;

    public static int result;

    String name, status;
    JTextField nameTxt, statusTxt;
    private final JLabel emptyName, emptyStatus;
    final JButton ok, cancel;

    public CustomDialog(Frame parent) {
        super(parent, "task", true);
        this.setResizable(false);

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

        JLabel spacer = new JLabel(" "); // adds some extra space between the textFields and the buttons
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(spacer, constraints);

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

    private boolean isNullOrWhitespace(String string) {
        return string == null || string.trim().length() == 0;
    }

    public Task display() {
        this.setVisible(true);
        return new Task(name, status);
    }

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