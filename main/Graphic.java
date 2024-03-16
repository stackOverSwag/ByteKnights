package main;

import javax.swing.*;

public class Graphic {
    private JFrame window;

    public Graphic() {
        window = new JFrame();
        window.setTitle("ByteKnights"); // title
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // what to do when pressing close
        window.setSize(800, 500); // window size
        window.setLocationRelativeTo(null); // centred on screen
    }

    public void show() {
        window.setVisible(true);
    }
}