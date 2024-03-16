package main;

import java.util.LinkedList;
import java.util.Queue;

public class FileFIFO extends Files {
    
    private Queue<String> queue;

    public FileFIFO(String nom, int coordX, int coordY){
        super(nom, coordX, coordY);

        this.queue = new LinkedList<>();
    }

    public String VOID() {
        return this.queue.pop();
    }

    public boolean push(String s) {
        return this.queue.push(s);
    }

    public void SEEK(int i) {}

    public boolean estVide() {
        return (this.queue.size() == 0);
    }

    public String peek() {
        return this.queue.peek();
    }
}