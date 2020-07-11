import java.awt.*;

public class EditDialog extends CustomDialog {

    private final int id;

    public EditDialog(Frame parent, Task task) {
        super(parent);
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