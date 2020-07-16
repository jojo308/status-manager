import java.awt.*;

public class AddDialog extends TaskDialog{

    public AddDialog(Frame parent) {
        super(parent, "add task");
        nameTxt.setText(name);
        statusTxt.setText(status);
    }

    @Override
    public Task display() {
        return super.display();
    }
}