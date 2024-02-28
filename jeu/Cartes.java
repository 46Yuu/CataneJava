package jeu;

/**
 * Classe représentant une carte de développement
 */
public class Cartes {	

	private final String type;

	private final int effet;
	// 0: carte progrès Construction
	// 1: carte progrès Invention
	// 2: carte progrès Monopole
	// -1: carte chevalier

	public Cartes(String type,int effet) {
		this.type=type;
		this.effet=effet;
	}
	
	public Cartes(String type) {
		this.type=type;
		this.effet=-1;
	}
	
	/**
	 * Retourne le nom du type de la Carte
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Récupère le type de la carte
	 */
	public int getEffet() {
		return this.effet;
	}
}
