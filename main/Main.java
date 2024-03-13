package main;

import javax.swing.*;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow();
                main.show();
            }
        });
    }

}