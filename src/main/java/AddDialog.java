import java.awt.*;

/**
 * Creates a dialog with input fields to create a task. It
 * returns a new task after submitting.
 */
public class AddDialog extends TaskDialog{

    public AddDialog(Frame parent) {
        super(parent, "add task");
        nameTxt.setText(name);
        statusTxt.setText(status);
    }
}