package main;

import java.util.ArrayList;

import javax.swing.*;

public class Main {

    public static void Main(String[] args) {

        new GUI();

        Exas a = new Exas("a");
        a.setCode("ADDI 2 1 X");
        Exas b = new Exas("b");
        b.setCode("SUBI 2 1 X");
        ArrayList<Files> f = new ArrayList<Files>();
        Niveau level = new Niveau(a, b, f);
        Game game = new Game(level);
    }

}
