package plateau;

import java.util.*;
import jeu.Joueur;

/**
 * 
 * Classe modélisant le plateau du jeu par un tableau à 2 dimensions
 */
public class Plateau {
	
	private Cases[][] plateau;
	private ArrayList<String> typesBiome;
	private ArrayList<Integer> numeroCase;

	public Plateau() {
		this.numeroCase = new ArrayList<Integer>();
		Collections.addAll(this.numeroCase,12,11,4,8,5,2,6,3,8,10,9,6,9,4,5);
		this.plateau=new Cases[9][9];
		this.typesBiome = new ArrayList<String>();
		typesBiome.add("desert");

		// Ajoute tout les types de biôme possibles à l'ArrayList "typesBiome"
		for(int i = 0; i<3;i++) {
			typesBiome.add("Forêt");
			typesBiome.add("Pré");
			typesBiome.add("Champs");
			typesBiome.add("Colline");
			typesBiome.add("Montagne");
		}

		this.setBiomes();
		this.setOther();
		this.setNumero();
	}

	/**
	 * Méthode assiocant un attribut biôme à chaque case du tableau
	 * de coordonnées impaires 
	*/
	public void setBiomes() {
		int x = 1;
		int y = 1;
		Random randomizer = new Random();
		while(typesBiome.size() != 0 ) {
			for(y=1;y<plateau.length;y=y+2) {
				for(x=1;x<plateau[y].length;x= x+2) {
					int tmpBiome = randomizer.nextInt(typesBiome.size());
					plateau[y][x] = new Cases(x,y,typesBiome.get(tmpBiome));
					typesBiome.remove(tmpBiome);
				}
			}
		}
	}

	/** 
	 * Méthode associant un numéro à chaque case du tableau
	 * de coordonnées impaires 
	*/
	public void setNumero() {
		int x = 1;
		int y = 1;
		for(y=1;y<7;y=y+2) {
			if(plateau[y][x].getTypeBiome()=="desert") {
				plateau[y][x].setNum(0);
			}
			else {
				plateau[y][x].setNum(numeroCase.get(0));
				numeroCase.remove(0);
			}
		}
		for(x=1;x<7;x=x+2) {
			if(plateau[y][x].getTypeBiome()=="desert") {
				plateau[y][x].setNum(0);
			}
			else {
				plateau[y][x].setNum(numeroCase.get(0));
				numeroCase.remove(0);
			}
		}
		for(y=7;y>1;y=y-2) {
			if(plateau[y][x].getTypeBiome()=="desert") {
				plateau[y][x].setNum(0);
			}
			else {
				plateau[y][x].setNum(numeroCase.get(0));
				numeroCase.remove(0);
			}
		}
		for(x=7;x>3;x=x-2) {
			if(plateau[y][x].getTypeBiome()=="desert") {
				plateau[y][x].setNum(0);
			}
			else {
				plateau[y][x].setNum(numeroCase.get(0));
				numeroCase.remove(0);
			}
		}
		for(y=1;y<7;y=y+2) {
			if(plateau[y][x].getTypeBiome()=="desert") {
				plateau[y][x].setNum(0);
			}
			else {
				plateau[y][x].setNum(numeroCase.get(0));
				numeroCase.remove(0);
			}
		}
		for(y=5;y>1;y=y-2) {
			if(plateau[y][x+2].getTypeBiome()=="desert") {
				plateau[y][x+2].setNum(0);
			}
			else {
				plateau[y][x+2].setNum(numeroCase.get(0));
				numeroCase.remove(0);
			}
		}
		
	}
	
	/**
	 * Méthode assiociant une case quelconque aux cases du tableau qui ne sont pas des biomes
	 */
	public void setOther() {
		for(int y = 0;y<plateau.length;y++) {
			for(int x = 0 ;x<plateau[y].length;x++) {
				if((x%2==0 && y%2==0) || (x%2==0 && y%2==1)|| (x%2==1 && y%2==0)) {
					plateau[y][x] = new Cases(x,y);
				}
			}
		}
	}
	
	/**
	 * Retourne la taille de l'ArrayList "typesBiome"
	 * @return taille de typesBiome
	 */
	public int getSize() {
		return typesBiome.size();
	}
	
	/**
	 * Retourne le plateau du jeu
	 */
	public Cases[][] getPlateau(){
		return this.plateau;
	}
	
	/**
	 * Retourne la liste des toutes les cases où on peut construire une colonnie
	 */
	public ArrayList<Cases> getColPossible(){
		ArrayList<Cases> colPossible = new ArrayList<Cases>();
		for(int y = 0;y<plateau.length;y=y+2) {
			for(int x = 0 ;x<plateau[y].length;x=x+2) {
				if(plateau[y][x].getBuildable()==true) {
					colPossible.add(plateau[y][x]);
				}
			}
		}
		return colPossible;
	}
	
	/**
	 * Retourne la liste des toutes les cases où le Joueur j peut construire une colonnie
	 * @param j : le Joueur
	 */
	public ArrayList<Cases> getColPossPour(Joueur j){
		ArrayList<Cases> colPossible = new ArrayList<Cases>();
		for(Cases c :j.getRoutes()) {
			if(c.getX()%2==1) {
				if(plateau[c.getY()][c.getX()-1].getBuildable()==true) {
					colPossible.add(plateau[c.getY()][c.getX()-1]);
				}
				if(plateau[c.getY()][c.getX()+1].getBuildable()==true) {
					colPossible.add(plateau[c.getY()][c.getX()+1]);
				}
			}
			if(c.getX()%2==0) {
				if(plateau[c.getY()-1][c.getX()].getBuildable()==true) {
					colPossible.add(plateau[c.getY()-1][c.getX()]);
				}
				if(plateau[c.getY()+1][c.getX()].getBuildable()==true) {
					colPossible.add(plateau[c.getY()+1][c.getX()]);
				}
			}
		}
		return colPossible;
	}
	
	/**
	 * Retourne la liste des toutes les cases où le Joueur j peut construire une route
	 * @param j : le Joueur
	 */
	public ArrayList<Cases> getRoutesPossPour(Joueur j){
		ArrayList<Cases> routesPossible = new ArrayList<Cases>();
		routesPossible.addAll(getRoutePossFromBat(j));
		for(Cases c : j.getRoutes()) {
			if(c.getX()%2==1) {
				getRoutePossFromRoute1(c,j,routesPossible);
				getRoutePossFromRoute2(c,j,routesPossible);
			}
			else {
				getRoutePossFromRoute3(c,j,routesPossible);
				getRoutePossFromRoute4(c,j,routesPossible);
			}
		}
		return routesPossible;
	}
	
	/**
	 * Retourne la liste des toutes les cases où le Joueur j peut construire 
	 * une route en fonction de ses différents bâtiments posés sur le plateau
	 * @param j : le Joueur
	 */
	public ArrayList<Cases> getRoutePossFromBat(Joueur j){
		ArrayList<Cases> routesPossFromBat = new ArrayList<Cases>();
		for(Cases c : j.getBats()) {
			if(c.getX()!=0) {
				if(plateau[c.getY()][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()][c.getX()-1],routesPossFromBat))) {
					routesPossFromBat.add(plateau[c.getY()][c.getX()-1]);
				}
			}
			if(c.getX()!=8) {
				if(plateau[c.getY()][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()][c.getX()+1],routesPossFromBat))) {
					routesPossFromBat.add(plateau[c.getY()][c.getX()+1]);
				}
			}
			if(c.getY()!=0) {
				if(plateau[c.getY()-1][c.getX()].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()],routesPossFromBat))) {
					routesPossFromBat.add(plateau[c.getY()-1][c.getX()]);
				}
			}
			if(c.getY()!=8) {
				if(plateau[c.getY()+1][c.getX()].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()],routesPossFromBat))) {
					routesPossFromBat.add(plateau[c.getY()+1][c.getX()]);
				}
			}
		}
		return routesPossFromBat;	
	}
	
	/**
	 * Vérifie si la case du tableau appartient à la liste des des cases où l'on peut construire une route
	 * @param c : la case
	 * @param routesPossible : liste des des cases où l'on peut construire une route
	 */
	public boolean appartientRoutePoss(Cases c,ArrayList<Cases> routesPossible) {
		for(Cases n: routesPossible) {
			if(n.getX()== c.getX() && n.getY() == c.getY()) {
				return true;
			}
		}
		return false;
	}
	
 	// Les 4 méthodes suivantes permettent de récupérer les routes construisables à partir des routes du Joueur j et évite de sortir du tableau

	public void getRoutePossFromRoute1(Cases c, Joueur j,ArrayList<Cases> routesPossible){
		if(c.getY()!=0) {
			if(plateau[c.getY()][c.getX()+1].getBuildable()==false &&plateau[c.getY()][c.getX()+1].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()-1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()-1]);
				}
				if(c.getX()>2) {
					if(plateau[c.getY()][c.getX()-2].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()][c.getX()-2],routesPossible))) {
						routesPossible.add(plateau[c.getY()][c.getX()-2]);
					}
				}
			}
			if(plateau[c.getY()][c.getX()-1].getBuildable()==false &&plateau[c.getY()][c.getX()-1].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()-1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()+1]);
				}
				if(c.getX()<6) {
					if(plateau[c.getY()][c.getX()+2].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()][c.getX()+2],routesPossible))) {
						routesPossible.add(plateau[c.getY()][c.getX()+2]);
					}
				}
			}
			else {
				if(plateau[c.getY()-1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()-1]);
				}
				if(plateau[c.getY()-1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()+1]);
				}
				if(c.getX()>2) {
					if(plateau[c.getY()][c.getX()-2].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()][c.getX()-2],routesPossible))) {
						routesPossible.add(plateau[c.getY()][c.getX()-2]);
					}
				}
				if(c.getX()<6) {
					if(plateau[c.getY()][c.getX()+2].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()][c.getX()+2],routesPossible))) {
						routesPossible.add(plateau[c.getY()][c.getX()+2]);
					}
				}
			}
		}
	}
	
	public void getRoutePossFromRoute2(Cases c, Joueur j,ArrayList<Cases> routesPossible){
		if(c.getY()!=8) {
			if(plateau[c.getY()][c.getX()+1].getBuildable()==false &&plateau[c.getY()][c.getX()+1].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()+1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()-1]);
				}
			}
			if(plateau[c.getY()][c.getX()-1].getBuildable()==false &&plateau[c.getY()][c.getX()-1].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()+1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()+1]);
				}
			}
			else {
				if(plateau[c.getY()+1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()-1]);
				}
				if(plateau[c.getY()+1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()+1]);
				}
			}
		}
	}
	
	public void getRoutePossFromRoute3(Cases c, Joueur j,ArrayList<Cases> routesPossible){
		if(c.getX()!=0) {
			if(plateau[c.getY()+1][c.getX()].getBuildable()==false &&plateau[c.getY()+1][c.getX()].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()-1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()-1]);
				}
				if(c.getY()>2) {
					if(plateau[c.getY()-2][c.getX()].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-2][c.getX()],routesPossible))) {
						routesPossible.add(plateau[c.getY()-2][c.getX()]);
					}
				}
			}
			if(plateau[c.getY()-1][c.getX()].getBuildable()==false &&plateau[c.getY()-1][c.getX()].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()+1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()-1]);
				}
				if(c.getY()<6) {
					if(plateau[c.getY()+2][c.getX()].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+2][c.getX()],routesPossible))) {
						routesPossible.add(plateau[c.getY()+2][c.getX()]);
					}
				}
			}
			else {
				if(plateau[c.getY()-1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()-1]);
				}
				if(plateau[c.getY()+1][c.getX()-1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()-1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()-1]);
				}
				if(c.getY()>2) {
					if(plateau[c.getY()-2][c.getX()].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-2][c.getX()],routesPossible))) {
						routesPossible.add(plateau[c.getY()-2][c.getX()]);
					}
				}
				if(c.getY()<6) {
					if(plateau[c.getY()+2][c.getX()].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+2][c.getX()],routesPossible))) {
						routesPossible.add(plateau[c.getY()+2][c.getX()]);
					}
				}
			}
		}
	}
	
	public void getRoutePossFromRoute4(Cases c, Joueur j,ArrayList<Cases> routesPossible){
		if(c.getX()!=8) {
			if(plateau[c.getY()+1][c.getX()].getBuildable()==false &&plateau[c.getY()+1][c.getX()].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()-1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()+1]);
				}
			}
			if(plateau[c.getY()-1][c.getX()].getBuildable()==false &&plateau[c.getY()-1][c.getX()].getProprietaire()==j.getNom()) {
				if(plateau[c.getY()+1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()+1]);
				}
			}
			else {
				if(plateau[c.getY()-1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()-1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()-1][c.getX()+1]);
				}
				if(plateau[c.getY()+1][c.getX()+1].getBuildable()==true && !(appartientRoutePoss(plateau[c.getY()+1][c.getX()+1],routesPossible))) {
					routesPossible.add(plateau[c.getY()+1][c.getX()+1]);
				}
			}
		}
	}
	
	/**
	 * Vérifie si la case c est construisable, puis construit une route 
	 * en y attribuant le nom du nouveau Joueur j propritétaire
	 * @param c : la Case
	 * @param j : le Joueur
	 */
	public void setRoute(Cases c,Joueur j) {
		if(c.getBuildable()==true) {
			c.setProprietaire(j.getNom());
			c.setBuildable(false);
		}
		else {
			System.out.println("Case non buildable.");
		}
	}
	
	/**
	 * Vérifie si la case c est construisable, puis construit une colonnie 
	 * en y attribuant le nom du nouveau Joueur j propritétaire
	 * @param c : la Case
	 * @param j : le Joueur
	 */
	public void setCol(Cases c,Joueur j) {
		if(c.getBuildable()==true) {
			c.setProprietaire(j.getNom());
			c.setTypeBat(1);
			c.setBuildable(false);
			if(c.getX()>1) {
				plateau[c.getY()][c.getX()-2].setBuildable(false);
			}
			if(c.getX()<7) {
				plateau[c.getY()][c.getX()+2].setBuildable(false);
			}
			if(c.getY()>1) {
				plateau[c.getY()-2][c.getX()].setBuildable(false);
			}
			if(c.getY()<7) {
				plateau[c.getY()+2][c.getX()].setBuildable(false);
			}
			j.getBats().add(c);
		}
		else {
			System.out.println("Case non buildable.");
		}
	}
	
	/**
	 * Vérifie si la case c appartient au joueur j, puis construit une ville
	 * @param c : la Case
	 * @param j : le Joueur
	 */
	public void setVille(Cases c,Joueur j) {
		if(c.getProprietaire()==j.getNom()) {
			c.setTypeBat(2);
		}
		else {
			System.out.println("Non proprietaire.");
		}
	}
	
	/**
	 * Retourne la liste de toutes les colonnies possédées 
	 * par le Joueur j qu'il peut améliorer en ville 
	 * @param j : le Joueur
	 */
	public ArrayList<Cases> getColUpgradable(Joueur j) {
		ArrayList<Cases> colUpgradable = new ArrayList<Cases>();
		for(Cases c : j.getBats()) {
			if(c.getTypeBat()==1) {
				colUpgradable.add(c);
			}
		}
		return colUpgradable;
	}
	
	/**
	 * Retourne la liste des routes construisables par le Joueur j lorsqu'il a 
	 * posé une de ses colonnies initiales
	 * @param c : Case de la colonnie posée
	 * @param j : Joueur j
	 */
	public ArrayList<Cases> getRoutesIni(Cases c,Joueur j) {
		ArrayList<Cases> routeChoix = new ArrayList<Cases>();
		if(c.getX()>0) {
			routeChoix.add(this.plateau[c.getY()][c.getX()-1]);
		}
		if(c.getY()>0) {
			routeChoix.add(this.plateau[c.getY()-1][c.getX()]);
		}
		if(c.getX()<8) {
			routeChoix.add(this.plateau[c.getY()][c.getX()+1]);
		}
		if(c.getY()<8) {
			routeChoix.add(this.plateau[c.getY()+1][c.getX()]);
		}
		return routeChoix;
	}
	
	/**
	 * Retourne la liste des cases "biôme" adjacentes à la Case colonnie "res"
	 * @param res : Case colonnie
	 */
	public ArrayList<Cases> getIniGain(Cases res) {
		ArrayList<Cases> gainRessource = new ArrayList<Cases>();
		if(res.getX()>0 && res.getY()>0) {
			if(this.plateau[res.getY()-1][res.getX()-1].getTypeBiome() != "desert") {
				gainRessource.add(this.plateau[res.getY()-1][res.getX()-1]);
			}
		}
		if(res.getX()<8 && res.getY()>0) {
			if(this.plateau[res.getY()-1][res.getX()+1].getTypeBiome() != "desert") {
				gainRessource.add(this.plateau[res.getY()-1][res.getX()+1]);
			}
		}
		if(res.getX()>0 && res.getY()<8) {
			if(this.plateau[res.getY()+1][res.getX()-1].getTypeBiome() != "desert") {
				gainRessource.add(this.plateau[res.getY()+1][res.getX()-1]);
			}
		}
		if(res.getX()<8 && res.getY()<8) {
			if(this.plateau[res.getY()+1][res.getX()+1].getTypeBiome() != "desert") {
				gainRessource.add(this.plateau[res.getY()+1][res.getX()+1]);
			}
		}
		return gainRessource;
	}
	
	/**
	 * Retourne la liste des cases "biôme" ne contenant pas le voleur
	 */
	public ArrayList<Cases> getCasesBiomes(){
		ArrayList<Cases> caseBiome = new ArrayList<Cases>();
		for(int y=1;y<plateau.length;y=y+2) {
			for(int x=1;x<plateau[y].length;x= x+2) {
				if(!plateau[y][x].getVoleur()) {
					caseBiome.add(plateau[y][x]);
				}
			}
		}
		return caseBiome;
	}
	
	/**
	 * Retourne la liste des cases "biôme"
	 */
	public ArrayList<Cases> getCasesBiomesRemove(){
		ArrayList<Cases> caseBiome = new ArrayList<Cases>();
		for(int y=1;y<plateau.length;y=y+2) {
			for(int x=1;x<plateau[y].length;x= x+2) {
				caseBiome.add(plateau[y][x]);
			}
		}
		return caseBiome;
	}
	
	/**
	 * Récupère tout les bâtiments adjacent à la case "res" possèdant un propriétaire 
	 * @param res : case du biôme
	 */
	public ArrayList<Cases> getBatResultat(Cases res) {
		ArrayList<Cases> gainRessource = new ArrayList<Cases>();
		if(this.plateau[res.getY()-1][res.getX()-1].getProprietaire() != "") {
			gainRessource.add(this.plateau[res.getY()-1][res.getX()-1]);
		}
		if(this.plateau[res.getY()-1][res.getX()+1].getProprietaire() != "") {
			gainRessource.add(this.plateau[res.getY()-1][res.getX()+1]);
		}
		if(this.plateau[res.getY()+1][res.getX()-1].getProprietaire() != "") {
			gainRessource.add(this.plateau[res.getY()+1][res.getX()-1]);
		}
		if(this.plateau[res.getY()+1][res.getX()+1].getProprietaire() != "") {
			gainRessource.add(this.plateau[res.getY()+1][res.getX()+1]);
		}
		return gainRessource;
	}
	
	/**
	 * Affiche dans la console la liste des diffférentes cases du plateau avec leurs caractéristiques
	 */
	public void Affichage() {
		for(int y = 0;y<plateau.length;y++) {
			for(int x = 0 ;x<plateau[y].length;x++) {
				System.out.println(plateau[y][x].toString());
			}
			System.out.println("");
		}
	}
	
	/* TEST: affichage du plateau
	public static void main(String [] args) {
		Plateau test = new Plateau();
		test.plateau[0][1].setProprietaire("blabla");
		test.Affichage();
	}
	*/
}