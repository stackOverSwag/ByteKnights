package projet;

import java.util.ArrayList;
import java.util.Iterator;

/*
	nom: nom du fichier
	contenu: le contenu du fichier en ArrayList ( apres avoir lu par le lecteur)
	iter: iterateur du contenu
*/
public class Files{
	private String nom;
	private ArrayList<String> contenu;
	private Iterator<String> iter;

// constructeur
	public Files(String nom, ArrayList<String> contenu){
		this.nom = nom;
		this.contenu = contenu;
		this.iter = contenu.iterator();
	}

// geteur nom
	public String getNom(){
		return this.nom;
	}

//geteur contenu
	public ArrayList<String> getContenu(){
		return this.contenu;
	}

//geteur iterateur
	public Iterator<String> getIter(){
		return this.iter;
	}

//mise a jour du code et l'iterateur de l'exa
 public void update(ArrayList<String> code){
	 this.contenu = code;
	 this.iter = code.iterator();
 }

}
