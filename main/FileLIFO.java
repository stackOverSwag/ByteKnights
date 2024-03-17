package main;

import java.util.LinkedList;
import java.util.Stack;

public class FileLIFO extends Files {
    
    private Stack<String> stack;

    public FileLIFO(String nom, int coordX, int coordY){
        super(nom, coordX, coordY);

        this.stack = new Stack<>();
    }

    public String VOID() {
        return this.stack.pop();
    }

    public void push(String s) {
        this.stack.push(s); //avant type bool
    }

    public void SEEK(int i) {}

    public boolean estVide() {
        return this.stack.isEmpty();
    }

    public String peek() {
        return this.stack.peek();
    }
}