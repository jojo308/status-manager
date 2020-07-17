import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Utility class that reads keyboard input. It saves the content of the
 * table to the XML file.
 *
 * The class has no function for the current version of this application.
 * This may change in future releases, in which case it will be properly implemented
 */
public class EventHandler extends JPanel implements KeyListener {

    public EventHandler() {
        super();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * saves the document when Ctrl+s is pressed
     * @param e the key that is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (e.isControlDown()) {
                JOptionPane.showMessageDialog(this, "saved the document");
                //todo actually save the document
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}