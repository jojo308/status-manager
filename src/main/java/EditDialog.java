import java.awt.*;

/**
 * creates a dialog that returns an edited version of the task.
 * If the task is unedited, it returns theoriginal task.
 */
public class EditDialog extends TaskDialog {

    /**
     * the id of the task is needed to identify it.
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