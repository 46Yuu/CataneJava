package jeu;

import java.util.*;
/**
 * Classe représentant une pile de carte développement.
 */
public class Pile {
	private ArrayList<Cartes> pileCarte;
	
	/**
	 * Crée une pile et les cartes nécessaire au déroulement de la partie.
	 */
	public Pile() {
		pileCarte= new ArrayList<Cartes>();
		for(int i = 0; i<5;i++) {
			pileCarte.add(new Cartes("Point"));
		}
		for(int i = 0;i<14;i++) {
			pileCarte.add(new Cartes("Chevalier"));
		}
		for(int i = 0;i<2;i++) {
			pileCarte.add(new Cartes("Progrès",0));
			pileCarte.add(new Cartes("Progrès",1));
			pileCarte.add(new Cartes("Progrès",2));
		}
		Collections.shuffle(pileCarte);
	}
	
	/**
	 * Fonction qui permet au joueur de piocher une carte.
	 * @return renvoie la carte qui a été piochée.
	 */
	public Cartes piocher() {
		if(!pileCarte.isEmpty()) {
			Cartes res = pileCarte.get(0);
			pileCarte.remove(0);
			return res;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Verifie si la pile est vide ou non.
	 * @return true si la pile de carte est vide, false sinon.
	 */
	public boolean isEmpty() {
		return pileCarte.isEmpty();
	}
}