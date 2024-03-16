package main;

/*
	nom : nom de l'exa
	postActu : la position actuelle de l'Exas
	code : le code ecrit par le joueur
	valeurX : le variable privé X de l'exas
	ValeurT : le variable privé T de l'Exas
	valeurF : le Fichier que l'exas porte

*/
public class Exas {
	private String nom;
	private String code;
	private String valeurX;
	private String valeurT;
	private Files valeurF;

	private int coordX;
	private int coordY;

	/* constructeur avec un nom et postision Actuelle de l'exas */
	public Exas(String nom) {
		this.nom = nom;
		this.code = "";
		this.valeurX = "";
		this.valeurT = "";
		this.valeurF = null;

		this.coordX = -1;
		this.coordY = -1;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// geteur code de l'exa
	public String getCode() {
		return this.code;
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

	// geteur valeurX
	public String getX() {
		return this.valeurX;
	}

	// redefinir la valeur X
	public void setX(String x) {
		this.valeurX = x;
	}

	// geteur T
	public String getT() {
		return this.valeurT;
	}

	// redefinir la valeurT
	public void setT(String t) {
		this.valeurT = t;
	}

	// geteur F
	public Files getF() {
		return this.valeurF;
	}

	public void setF(Files f) {
		this.valeurF = f;
	}

}
