import java.awt.*;

/**
 * Creates a dialog that returns a new task.
 */
public class AddDialog extends TaskDialog{

    public AddDialog(Frame parent) {
        super(parent, "add task");
        nameTxt.setText(name);
        statusTxt.setText(status);
    }
}