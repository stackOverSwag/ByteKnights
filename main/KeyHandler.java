import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class KeyHandler implements KeyListener {

    public boolean escape;

    @Override
    public void keyTyped(KeyEvent e) {} // NOT USED EVER

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns number of key pressed
        
        if (code == KeyEvent.VK_ESCAPE) {
            escape = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {} // NOT USED EVER

    public boolean Pause(){
        boolean pause = this.escape;
        this.escape = false;
        return pause;
    }
}
