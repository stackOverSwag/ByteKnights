package main;

import java.util.ArrayList;

public class Niveau {
    private Exas exa1;
    private Exas exa2;
    private ArrayList<Files> files;
    private int height;
    private int width;
    private ArrayList<Integer> positions;

    public Niveau(Exas exa1, Exas exa2, ArrayList<? extends Files> files) {

        this.files = new ArrayList<>(files);
        this.exa1 = exa1;
        this.exa2 = exa2;
        this.height = 5;
        this.width = 5;

        checkPositions();
        saveExasStartingPositions();
    }

    public Niveau(Exas exa1, Exas exa2, ArrayList<Files> files, int height, int width) {

        super();
        this.height = height;
        this.width = width;

        checkPositions();
        saveExasStartingPositions();
    }

    public void checkPositions() {
        if (this.height < this.exa1.getCoordY()) {
            throw new IllegalArgumentException("exa1y > height");
        }
        if (this.height < this.exa2.getCoordY()) {
            throw new IllegalArgumentException("exa2y > height");
        }
        if (this.width < this.exa1.getCoordX()) {
            throw new IllegalArgumentException("exa1X > width");
        }
        if (this.width < this.exa2.getCoordX()) {
            throw new IllegalArgumentException("exa2X > width");
        }
        for (Files f : this.files) {
            if (this.height < f.getCoordY()) {
                throw new IllegalArgumentException("File " + f.getNom() + " height > this.height");
            }
            if (this.width < f.getCoordX()) {
                throw new IllegalArgumentException("File " + f.getNom() + " width > this.width");
            }
        }

    }

    public Exas getExa1() {
        return this.exa1;
    }

    public Exas getExa2() {
        return this.exa2;
    }

    public Files getFile(String nom) {
        for (Files f : this.files) {
            if (f.getNom().equals(nom)) {
                return f;
            }
        }
        throw new IllegalArgumentException("File " + nom + " not found");
    }

    public ArrayList<Integer> findEmptySpot() {

        // not using a matrix of files made this into O(files.size() * n^2) time
        // complexity... I am an idiot.
        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                if(!((this.exa1.getCoordX() == i && this.exa1.getCoordY() == j)
                    || (this.exa2.getCoordX() == i && this.exa2.getCoordY() == j))) {                
                    for (Files f : this.files) {
                        if (!(f.getCoordX() == i && f.getCoordY() == j)) {
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            temp.add(i);
                            temp.add(j);
                            return temp;
                        }
                    }
                }
            }
        }
        return new ArrayList<Integer>();
    }

    public void addFile(Files file) {
        try {
            checkIfFree(file.getCoordX(), file.getCoordY());

        } catch (IllegalArgumentException e) {
            ArrayList<Integer> coords = findEmptySpot();
            if (coords.size() == 0) {
                throw new IllegalArgumentException("File " + file + " does not fit");
            }
            file.setCoordX(coords.get(0));
            file.setCoordY(coords.get(1));
        }

        this.files.add(file);
    }

    public void removeFile(String nom) {
        for (Files f : this.files) {
            if (f.getNom().equals(nom)) {
                if (!this.files.remove(f)) {
                    throw new IllegalArgumentException("File " + nom + " was not able to be removed");
                }

            }
        }
        throw new IllegalArgumentException("File " + nom + " does not exist in the level");
    }

    public void checkIfFree(int x, int y) {
        if (this.exa1.getCoordX() == x && this.exa1.getCoordY() == y) {
            throw new IllegalArgumentException("Exa " + this.exa1.getNom() + " already there");
        }

        if (this.exa2.getCoordX() == x && this.exa2.getCoordY() == y) {
            throw new IllegalArgumentException("Exa " + this.exa2.getNom() + " already there");
        }

        for (Files f : this.files) {
            if (f.getCoordX() == x && f.getCoordY() == y) {
                throw new IllegalArgumentException("Files " + f.getNom() + " already there");
            }
        }
    }

    public void changePosition(String nom, int x, int y) {
        checkIfFree(x, y);
        if (nom.equals(this.exa1.getNom())) {

            this.exa1.setCoordX(x);
            this.exa1.setCoordY(y);
            checkPositions();
            return;
        } else if (nom.equals(this.exa2.getNom())) {
            this.exa2.setCoordX(x);
            this.exa2.setCoordY(y);
            checkPositions();
            return;
        } else {
            for (Files f : this.files) {
                if (f.getNom().equals(nom)) {
                    f.setCoordX(x);
                    f.setCoordY(y);
                    checkPositions();
                    return;
                }
            }

        }
        throw new IllegalArgumentException(nom + " is not in the level");

    }

    public boolean hasFreeSpace() {
        return (2 + this.files.size()) < (this.height * this.width);
    }

    public void saveExasStartingPositions() {
        this.positions = new ArrayList<Integer>();
        this.positions.add(this.exa1.getCoordX());
        this.positions.add(this.exa1.getCoordY());
        this.positions.add(this.exa2.getCoordX());
        this.positions.add(this.exa2.getCoordY());
    }

    public boolean hasWon() {
        return this.positions.get(0) == this.positions.get(2) && this.positions.get(1) == this.positions.get(3);
    }

}
