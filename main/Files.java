package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/*
	abstrac class fr FileFIFO, FileLIFO, FileTD
*/
public abstract class Files {
	private String nom;

	private int coordX;
	private int coordY;

	// constructeur
	public File(String nom, int coordX, int coordY) {
		this.nom = nom;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public int getCoordX() {
		return this.coordX;
	}

	public void setCoordX(int x) {
		this.coordX = x;
	}

	public int getCoordY() {
		return this.coordY;
	}

	public void setCoordY(int y) {
		this.coordY = y;
	}

	// geteur nom
	public String getNom() {
		return this.nom;
	}

}
