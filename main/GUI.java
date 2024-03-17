/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import javax.swing.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GUI extends JFrame {

    private int levelheight;
    private int levelwidth;

    private GridLayout mainGrid;

    private JFrame JFrame_Window;
    // {

    private JPanel JPanel_Left;
    // {

    private JPanel JPanel_Exa1;
    // {
    private JLabel JLabel_Exa1;
    private JTextField JTextField_Exa1;
    private String Exa1Code;
    // }

    private JPanel JPanel_Exa2;
    // {
    private JLabel JLabel_Exa2;
    private JTextField JTextField_Exa2;
    private String Exa2Code;
    // }

    private JPanel JPanel_Buttons;
    // {
    private JButton JButton_Step;
    private JButton JButton_Run;
    // }

    // }
    // -----------------------------------------------------------------------
    private JPanel JPanel_Right;
    // {

    private JPanel JPanel_Screen;
    // {
    private ArrayList<JLabel> JPanel_ScreenMatrix;
    // }

    private JPanel JPanel_Errors;
    // {
    private JLabel JLabel_Errors;
    // }

    // }

    // }

    /**
     * Creates components for GUI
     */
    public GUI(int height, int width) {
        this.levelheight = height;
        this.levelwidth = width;
        initComponents();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI(5, 5);
            }
        });
    }

    private void initComponents() {

        this.JFrame_Window = new JFrame("ByteKnights");
        this.JFrame_Window.setSize(800, 500);
        this.JFrame_Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainGrid = new GridLayout(1, 2);
        this.JFrame_Window.setLayout(this.mainGrid);

        initComponents_left();
        this.JFrame_Window.add(this.JPanel_Left);

        initComponents_right();
        this.JFrame_Window.add(this.JPanel_Right);

        this.JFrame_Window.setVisible(true);
        pack();
    }

    public void initComponents_right() {

        this.JPanel_Right = new JPanel(new GridLayout(2, 1));

        this.JPanel_Screen = new JPanel(new GridLayout(this.levelheight, this.levelwidth));
        this.JPanel_ScreenMatrix = new ArrayList<>();

        ImageIcon icon = new ImageIcon("Images/Tile.png");
        System.out.println("Image path: " + icon.getDescription());
        for (int i = 0; i < this.levelheight; i++) {
            for (int j = 0; j < this.levelwidth; j++) {
                System.out.println("added " + (this.levelheight * i + j));
                JLabel label = new JLabel(icon);
                this.JPanel_ScreenMatrix.add(label);
                this.JPanel_Screen.add(label);
            }
        }

        this.JPanel_Errors = new JPanel();

        this.JPanel_Right.add(JPanel_Screen);
        this.JPanel_Right.add(JPanel_Errors);
    }

    public void initComponents_left() {
        this.JPanel_Left = new JPanel(new GridLayout(3, 1));

        this.JPanel_Exa1 = new JPanel(new GridLayout(2, 1));
        this.JLabel_Exa1 = new JLabel("Exa 1 code");
        this.JTextField_Exa1 = new JTextField();

        this.JPanel_Exa2 = new JPanel(new GridLayout(2, 1));
        this.JLabel_Exa2 = new JLabel("Exa 2 code");
        this.JTextField_Exa2 = new JTextField();

        this.JPanel_Buttons = new JPanel(new GridLayout(1, 2));
        this.JButton_Step = new JButton();
        this.JButton_Run = new JButton();

        this.JPanel_Exa1.add(this.JLabel_Exa1);
        this.JPanel_Exa1.add(this.JTextField_Exa1);
        this.JPanel_Left.add(this.JPanel_Exa1);

        this.JPanel_Exa2.add(this.JLabel_Exa2);
        this.JPanel_Exa2.add(this.JTextField_Exa2);
        this.JPanel_Left.add(this.JPanel_Exa2);

        this.JPanel_Buttons.add(this.JButton_Step);
        this.JPanel_Buttons.add(this.JButton_Run);
        this.JPanel_Left.add(this.JPanel_Buttons);

        this.JTextField_Exa2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JTextField_Exa2ActionPerformed(evt);
            }
        });

        this.JTextField_Exa1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JTextField_Exa2ActionPerformed(evt);
            }
        });

        this.JButton_Step.setText("Step");
        this.JButton_Step.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JButton_StepActionPerformed(evt);
            }
        });

        this.JButton_Run.setText("Run");
        this.JButton_Run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JButton_RunActionPerformed(evt);
            }
        });

    }

    private void JButton_StepActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void JButton_RunActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void JTextField_Exa1ActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void JTextField_Exa2ActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

}
