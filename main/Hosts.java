package projet;

import java.util.ArrayList;

/*
	files: liste des fichier dans cette salle
	exas: liste des exas dans cette salle
	registre: liste des registre dans cette salle
	forma: le forma de la salle (exemple : 2x2 ou 3x3),
	enregistrer sous forme de liste de deux argument.
	nom: nom de cette salle
	id: id de la salle
	links: la liste des autre salle connecter avec cette salle
	lastLinks: la salle qui est connecter a cette salle (losque le joueur fait LINK -1)
*/


public class Hosts{
	private ArrayList<Files> files;
	private ArrayList<Exas> exas;
	private ArrayList<Registre> registre;
	private ArrayList<String> forma;
	private int id;
	private ArrayList<Hosts> links;
	private Hosts lastLinks;

// constructeur #17
	public Hosts(ArrayList<Exas> exas, ArrayList<Files> files, ArrayList<Registre> registre,ArrayList<String> forma, int id, ArrayList<Hosts> links, Hosts lastLinks){
		this.exas = exas;
		this.files = files;
		this.registre  = registre;
		this.forma = forma;
		this.id = id;
		this.links = links;
		this.lastLinks = lastLinks;
	}

// constructeur #69
	public Hosts(ArrayList<String> forma, int id, ArrayList<Hosts> links, Hosts lastLinks){
		this.forma = forma;
		this.id = id;
		this.links = links;
		this.lastLinks = lastLinks;
		this.exas = new ArrayList<Exas>();
		this.files = new ArrayList<Files>();
		this.registre = new ArrayList<Registre>();
	}

// constructeur #77
	public Hosts(ArrayList<String> forma, int id){
		this.forma=forma;
		this.id = id;
		this.links = new ArrayList<Hosts>();
		this.lastLinks = null;
		this.exas = new ArrayList<Exas>();
		this.files = new ArrayList<Files>();
		this.registre = new ArrayList<Registre>();
	}

// geteur des exas
	public ArrayList<Exas> getExas(){
		return this.exas;
	}

// geteur des files
	public ArrayList<Files> getFiles(){
		return this.files;
	}

// geteur des registre
	public ArrayList<Registre> getRegistre(){
		return this.registre;
	}

// geteur du nombre de case dans cette salles
	public int getNbCases(){
		return Integer.parseInt(this.forma.get(0))*Integer.parseInt(this.forma.get(1));
	}

//geteur ID
	public int getId(){
		return this.id;
	}

// geteur des salles connecter
	public ArrayList<Hosts> getLinks(){
		return this.links;
	}

// geteur de la salle connecter dans l'autre sens
	public Hosts getLastLinks(){
		return this.lastLinks;
	}

// ajouter un exas dans cette salle
	public boolean add(Exas exas){
		return this.exas.add(exas);
	}

// supprimer un exas dans cette salle
	public boolean del(Exas exas){
		return this.exas.remove(exas);
	}


// ajouter un fichier dans cette salle
	public boolean add(Files files){
		return this.files.add(files);
	}

//supprimer un fichier dans cette salle
	public boolean del(Files files){
		return this.exas.remove(files);
	}

//ajouter un registre dans cette salle
	public boolean add(Registre registre){
		return this.registre.add(registre);
	}

//supprimer un registre de cette salle
	public boolean del(Registre registre){
		return this.registre.remove(registre);
	}

// connecter un autre salle a cette salle
	public boolean link(Hosts links){
		return this.links.add(links);
	}

// deconnecter un sautre alle de cette salle
	public boolean unlink(Hosts links){
		return this.links.remove(links);
	}

// changer la salle connecter a cette salle
	public void setlastLinks(Hosts links){
		this.lastLinks = links;
	}

// true si cette salle possede le fichier files, false si non
	public boolean hasFiles(Files files){

		return this.files.contains(files);
	}

// true si cette salle est connecter a la salle qui a le ID id
	public boolean canPass(int id){
		if(id == this.lastLinks.getId()) {
			return true;
		}
		for(Hosts i : this.links){
			if(i.getId() == id){
				return true;
			}
		}
		return false;
	}

// print la salle en forma ci dessous:
// [id,[forma,forma],[id_linked,id_linked, ...],[exa,exa,],[fichier,fichier,...],[registre,registre,...],lastLinks]

	public void printHost(){
		String message = "";
		message += "["+ Integer.toString(this.id)+",";
		message += "["+ this.forma.get(0)+","+ this.forma.get(1)+"],";
		message += "[";
		for(Hosts h : this.getLinks()){
			message +="["+Integer.toString(h.getId())+"],";
		}
		if(this.lastLinks !=null){
			message += Integer.toString(this.lastLinks.getId())+"],";
		}
		message +="[";
		for(Exas e :this.getExas()){
			message += "["+e.getNom()+"],";
		}
		message += "],[";
		for(Files f :this.getFiles()){
			message += "["+f.getNom()+","+f.getContenu()+"],";
		}
		message += "],";
		for(Registre r: this.getRegistre()){
			message +="["+r.getNom()+"],";
		}
		message +="],";
		message += this.lastLinks.getId();
		message +="]";

		System.out.println(message);
		System.out.println("§§§");
	}
}
