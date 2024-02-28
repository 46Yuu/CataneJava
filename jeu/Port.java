package jeu;

/**
 * Classe représentant un port.
 */
public class Port {
	private boolean nivPort;
	private boolean portBois;
	private boolean portLaine;
	private boolean portBlé;
	private boolean portArgile;
	private boolean portMinerai;
	
	/**
	 * Constructeur des ports d'un joueur.
	 */
	public Port() {
		this.nivPort=false;
		this.portBois=false;
		this.portLaine=false;
		this.portBlé=false;
		this.portArgile=false;
		this.portMinerai=false;
	}
	
	/**
	 * Recupere si le joueur a un port ou non.
	 * @return true si il a un port , false sinon.
	 */
	public boolean getNivPort() {
		return this.nivPort;
	}
	
	/**
	 * Recupere si le joueur a un port de bois ou non.
	 * @return true si il a un port de bois, false sinon.
	 */
	public boolean getPortBois() {
		return this.portBois;
	}
	
	/**
	 * Recupere si le joueur a un port de laine ou non.
	 * @return true si il a un port de laine, false sinon.
	 */
	public boolean getPortLaine() {
		return this.portLaine;
	}
	
	/**
	 * Recupere si le joueur a un port de blé ou non.
	 * @return true si il a un port de blé, false sinon.
	 */
	public boolean getPortBlé() {
		return this.portBlé;
	}
	
	/**
	 * Recupere si le joueur a un port d'argile ou non.
	 * @return true si il a un port d'argile, false sinon.
	 */
	public boolean getPortArgile() {
		return this.portArgile;
	}
	
	/**
	 * Recupere si le joueur a un port de minerai ou non.
	 * @return true si il a un port de minerai, false sinon.
	 */
	public boolean getPortMinerai() {
		return this.portMinerai;
	}
	
	/**
	 * Changement du boolean qui nous dit si le joueur a un port ou non.
	 * @param b : true si le joueur a obtenu un port.
	 */
	public void setNivPort(Boolean b) {
		this.nivPort=b;
	}
	
	/**
	 * Changement du boolean qui nous dit si le joueur a un port de bois ou non.
	 * @param b : true si le joueur a obtenu un port de bois.
	 */
	public void setPortBois(Boolean b) {
		this.portBois=b;
	}
	
	/**
	 * Changement du boolean qui nous dit si le joueur a un port de laine ou non.
	 * @param b : true si le joueur a obtenu un port de laine.
	 */
	public void setPortLaine(Boolean b) {
		this.portLaine=b;
	}
	
	/**
	 * Changement du boolean qui nous dit si le joueur a un port de blé ou non.
	 * @param b : true si le joueur a obtenu un port de blé.
	 */
	public void setPortBlé(Boolean b) {
		this.portBlé=b;
	}
	
	/**
	 * Changement du boolean qui nous dit si le joueur a un port d'argile ou non.
	 * @param b : true si le joueur a obtenu un port d'argile.
	 */
	public void setPortArgile(Boolean b) {
		this.portArgile=b;
	}
	
	/**
	 * Changement du boolean qui nous dit si le joueur a un port de minerai ou non.
	 * @param b : true si le joueur a obtenu un port de minerai.
	 */
	public void setPortMinerai(Boolean b) {
		this.portMinerai=b;
	}
}