package projet;



/*
	nom : nom de l'exa
	postActu : la position actuelle de l'Exas
	code : le code ecrit par le joueur
	valeurX : le variable privé X de l'exas
	ValeurT : le variable privé T de l'Exas
	valeurF : le Fichier que l'exas porte

*/
public class Exas{
	private String nom;
	private Hosts postActu;
	private String code;
	private String valeurX;
	private String valeurT;
	private Files valeurF;

/* constructeur avec un nom et postision Actuelle de l'exas*/
	public Exas(String nom,Hosts postActu){
		this.nom = nom;
		this.postActu = postActu;
		this.code = "";
		this.valeurX= "";
		this.valeurT= "";
		this.valeurF= null;
	}

//geteur nom
	public String getNom(){
		return this.nom;
	}

//geteur valeurX
	public String getX(){
		return this.valeurX;
	}

//redefinir la valeur X
	public void setX(String x){
		this.valeurX = x;
	}

//geteur T
	public String getT(){
		return this.valeurT;
	}
//redefinir la valeurT
	public void setT(String t){
		this.valeurT = t;
	}

//geteur F
	public Files getF(){
			return this.valeurF;
	}

/*redefinir la valeur F, en verifiant la pressence de fichier dans
les main et que la position Actuelle possede ou pas le fichier*/
	public boolean grabfiles(Files f){
		if(this.postActu.hasFiles(f) && this.valeurF == null){
			this.valeurF = f;
			this.postActu.del(f);
			return true;
		}
		return false;
	}

// geteut code de l'exa
	public String getCode(){
		return this.code;
	}

// jeteur le fichier dans la salle actuelle
	public boolean dropfiles(){
		if(this.valeurF != null){
			this.postActu.add(this.valeurF);
			this.valeurF = null;
			return true;
		}
		return false;
	}

// deplacer l'exa dans un autre salle en verifiant la possibiliter de l'action
	public boolean move(Hosts hosts){
		if(this.postActu.canPass(hosts.getId())){
			this.postActu.del(this);
			hosts.add(this);
			this.postActu = hosts;

			return true;
		}
		return false;
	}

}
