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

    public boolean push(String s) {
        return this.stack.push(s);
    }

    public void SEEK(int i) {}

    public boolean estVide() {
        return (this.stack.size() == 0);
    }

    public String peek() {
        return this.stack.peek();
    }
}