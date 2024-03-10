public class Niveau {
    private Exas exa1;
    private Exas exa2;
    private Files file;
    private int height;
    private int width;
    private ArrayList<Integer> positions;

    public Niveau(Exas exa1, Exas exa2, Files file) {

        this.file = file;
        this.exa1 = exa1;
        this.exa2 = exa2;
        this.height = 5;
        this.width = 5;

        checkPositions();

        saveStartingPositions();
    }

    // too many arguments...
    public Niveau(Exas exa1, Exas exa2, Files file,
            int exa1X, int exa1Y, int exa2X,
            int exa2Y, int fileX, int fileY,
            int height, int width) {
        this.file = file;
        this.exa1 = exa1;
        this.exa2 = exa2;

        checkPositions();

        this.exa1.setCoordX(exa1X);
        this.exa2.setCoordX(exa2X);
        this.exa1.setCoordY(exa1Y);
        this.exa2.setCoordY(exa2Y);
        this.file.setCoordX(fileX);
        this.file.setCoordY(fileY);
        this.height = height;
        this.width = width;

        saveStartingPositions();
    }

    public void checkPositions() {
        if (this.height < this.exa1.getCoordY()) {
            throw new IllegalArgumentException("exa1y > height");
        }
        if (this.height < this.exa2.getCoordY()) {
            throw new IllegalArgumentException("exa2y > height");
        }
        if (this.height < this.file.getCoordY()) {
            throw new IllegalArgumentException("filey > height");
        }
        if (this.width < this.exa1.getCoordX()) {
            throw new IllegalArgumentException("exa1X > width");
        }
        if (this.width < this.exa2.getCoordX()) {
            throw new IllegalArgumentException("exa2X > width");
        }
        if (this.width < this.file.getCoordX()) {
            throw new IllegalArgumentException("filex > width");
        }

    }

    public Exas getExa1() {
        return this.exa1;
    }

    public Exas getExa2() {
        return this.exa2;
    }

    public Files getFile() {
        return this.file;
    }

    public void changePosition(String nom, int x, int y) {

        if (nom == this.file.getNom()) {
            this.file.setCoordX(x);
            this.file.setCoordY(y);

        } else if (nom == this.exa1.getNom()) {
            this.exa1.setCoordX(x);
            this.exa1.setCoordY(y);
        } else if (nom == this.exa2.getNom()) {
            this.exa2.setCoordX(x);
            this.exa2.setCoordY(y);
        } else {
            throw new IllegalArgumentException(nom + " is not in the level");
        }

        checkPositions();
    }

    public void saveStartingPositions() {
        this.positions = new ArrayList<Integer>(
                this.exa1.getCoordX(),
                this.exa1.getCoordY(),
                this.exa2.getCoordX(),
                this.exa2.getCoordY(),
                this.file.getCoordX(),
                this.file.getCoordY());
    }

    public boolean hasWon() {
        return this.positions.get(0) == this.positions.get(2) && this.positions.get(1) == this.positions.get(3);
    }

}