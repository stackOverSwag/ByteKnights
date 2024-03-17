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
        return this.queue.poll();
    }

    public void push(String s) {
        this.queue.offer(s); // avant boolean type
    }

    public void SEEK(int i) {}

    public boolean estVide() {
        return this.queue.isEmpty();
    }

    public String peek() {
        return this.queue.peek();
    }
}