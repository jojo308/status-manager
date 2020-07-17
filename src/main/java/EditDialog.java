import java.awt.*;

/**
 * creates a dialog with auto filled input fields from the
 * original task. When submitted, the task will be updated.
 * If the dialog is cancelled, it returns the original task.
 */
public class EditDialog extends TaskDialog {

    /**
     * the id of the task that is needed to identify it.
     */
    private final int id;

    public EditDialog(Frame parent, Task task) {
        super(parent, "edit task");
        name = task.getName();
        status = task.getStatus();

        id = task.getId();
        nameTxt.setText(name);
        statusTxt.setText(status);
    }

    public Task display() {
        this.setVisible(true);
        return new Task(id, name, status);
    }
}