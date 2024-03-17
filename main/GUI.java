package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    private JPanel mainPanel;
    private int count = 0;
    private JFrame frame;
    private JTextField textField;
    private JButton button;

    public GUI() {
        this.frame = new JFrame("My Game");
        this.frame.setSize(800, 600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.mainPanel = new JPanel();
        this.mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.mainPanel.setLayout(new GridLayout(0, 1));

        // Create a JTextField for text input
        this.textField = new JTextField();
        this.textField.setPreferredSize(new Dimension(300, 300));
        this.mainPanel.add(this.textField, BorderLayout.NORTH);

        // Create a JButton to submit the text input
        this.button = new JButton("Submit");
        this.button.setPreferredSize(new Dimension(10, 50));
        this.button.addActionListener(this);
        this.mainPanel.add(this.button, BorderLayout.CENTER);

        this.frame.add(this.mainPanel, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.count++;
        this.textField.setText("count: " + this.count);
    }

}