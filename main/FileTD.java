package main;

import java.util.ArrayList;

public class FileTD extends Files {
    
    private ArrayList<String> tab;
    private int lastAdded;
    private int index;

    public FileTD(String nom, int coordX, int coordY){
        super(nom, coordX, coordY);
        this.index = 0;
        this. lastAdded = -1;

        this.tab = new ArrayList<>();
    }

    public String VOID() {
        if(-1 != this.lastAdded) {
            return this.tab.remove(this.lastAdded);
        }
        return "";
    }

    public boolean push(String s) {
        this.tab.add(s);
        this.index++;
        this.lastAdded = this.index;
    }

    public void SEEK(int i) {
        this.index += i;
    }


    // actually returns true if the pointer is at the end of the file
    public boolean estVide() {
        return (this.tab.size() == this.index);
    }

    public String peek() {
        return this.tab.get(index);
    }
}