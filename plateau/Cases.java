package plateau;

/**
 * Classe modélisant une case du tableau que contient le plateau du jeu
 */
public class Cases {
	
	private final String type;
	private int numero;
	private int x;
	private int y;
	private String proprietaire;

	/**
	 * 0 = vide, 
	 * 1 = Colonnie, 
	 * 2 = Ville
	 */
	private int typeBat;

	private boolean buildable;
	private boolean voleur;

	/**
	 * Constructeur d'une case "biôme" du tableau que contient le plateau du jeu
	 * @param x : coordonnés sur l'axe X
	 * @param y : coordonnés sur l'axe Y
	 * @param type : type de la case 
	 */
	public Cases(int x,int y,String type) {
		this.type=type;
		this.numero=0;
		this.x=x;
		this.y=y;
		this.proprietaire="";
		this.typeBat=0;
		this.buildable=false;
		this.voleur=false;
	}

	/**
	 * Constructeur d'une case quelconque du tableau que contient le plateau du jeu
	 * @param x : coordonnés sur l'axe X
	 * @param y : coordonnés sur l'axe Y
	 */
	public Cases(int x,int y) {
		this.type="";
		this.numero=0;
		this.x=x;
		this.y=y;
		this.proprietaire="";
		this.typeBat=0;
		this.buildable=true;
		this.voleur=false;
	}

	/**
	 * Renvoie le type de la case: biôme, bâtiment ou route
	 */
	public String getTypeCase() {
		if(x%2 == 1 && y%2 ==1) {
			return "biome";
		}
		if(x%2 == 0 && y%2 == 0) {
			return "batiment";
		}
		else {
			return "routes";
		}
	}

	/**
	 * Retourne le type de biôme de la case (Forêt, Pré etc...)
	 */
	public String getTypeBiome() {
		return this.type;
	}

	/**
	 * Renvoie le nom du Joueur propriétaire de la case
	 */
	public String getProprietaire() {
		return this.proprietaire;
	}

	/**
	 * Renboie le numéro de la case
	 */
	public int getNum() {
		return this.numero;
	}

	/**
	 * Renvoie le type de bâtiment de la case
	 */
	public int getTypeBat() {
		return this.typeBat;
	}

	/**
	 * Renvoie les coordonées de la case sur l'axe X
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Renvoie les coordonées de la case sur l'axe Y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Renvoie true si on peut construire une route ou un bâtiment sur la case
	 */
	public boolean getBuildable() {
		return this.buildable;
	}

	/**
	 * Renvoie true si le voleur se trouve sur la case, sinon false
	 */
	public boolean getVoleur() {
		return this.voleur;
	}

	/**
	 * Défini comme n le numéro de la case
	 * @param n : numéro de la case
	 */
	public void setNum(int n) {
		this.numero=n;
	}

	/**
	 * Défini le nouveau propriétaire de la case
	 * @param nom : nom du nouveau propriétaire
	 */
	public void setProprietaire(String nom) {
		this.proprietaire= nom;
	}

	/**
	 * Définit le type de bâtiment que peut contenir la case: 
	 * 0 = vide, 
	 * 1 = Colonnie, 
	 * 2 = Ville
	 */
	public void setTypeBat(int x) {
		this.typeBat=x;
	}

	/**
	 * Définit si l'on peut construire sur la case ou non
	 * @param bool : true pour construisable, sinon false
	 */
	public void setBuildable(boolean bool) {
		this.buildable=bool;
	}

	/**
	 * Définit si la case possède un voleur ou non
	 * @param bool : true si le voleur se trouve dessus, sinon false
	 */
	public void setVoleur(boolean bool) {
		this.voleur=bool;
	}

	/**
	 * Méthode affichant les informations d'une Case dans le terminal (TEST)
	 */
	@Override
	public String toString() {
		if(this.getTypeCase()=="biome") {
			return "{Location: x="+this.x+" y="+ this.y+" ,typeCase: "+this.getTypeCase() +" "+this.type+" ,numero: "+this.numero+"}";
		}
		if(this.getTypeCase()=="batiment") {
			return "{Location: x="+this.x+" y="+ this.y+" ,typeCase: "+this.getTypeCase() +" "+this.type+" ,numero: "+this.numero+" ,Proprietaire: "+this.proprietaire+" ,typeBat: "+this.typeBat+"}";
		}
		if(this.getTypeCase()=="routes") {
			return "{Location: x="+this.x+" y="+ this.y+" ,typeCase: "+this.getTypeCase() +" "+this.type+" ,numero: "+this.numero+" ,Proprietaire: "+this.proprietaire+"}";
		}
		else {
			return "{Location: x="+this.x+" y="+ this.y+" ,typeCase: "+this.getTypeCase()+" ,numero: "+this.numero+" ,Proprietaire: "+this.proprietaire+" ,typeBat: "+this.typeBat+"}";
		}

	}
	
	// mod2 biome : 1;1
	// mod2 batiment : 0;0
	// mod2 routes : 1;0 ou 0;1
}
