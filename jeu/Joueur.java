package jeu;

import java.util.*;
import plateau.*;

/**
 * Classe représentant un joueur
 */
public class Joueur {

    private final String nom;
    private int score;
    private int scoreAffiché;
    private final boolean ia;
    private String couleur;
    private boolean armee;
    private int nbArmee;

    private int nbBois;
    private int nbArgile;
    private int nbLaine;
    private int nbBlé;
    private int nbMinerai;
    private int totalRessources;

    private Port niveauPort;
    
    // Ces listes contiennent les routes et bâtiments que possède le joueur sur le plateau
    private ArrayList<Cases> bats;
    private ArrayList<Cases> routes;
    private ArrayList<Cartes> cartes;
    
    private int totalCartes;

    private int nbColonies;
    private int nbVilles;
    private int nbRoutes;
    private int totalPieces;

    public Joueur(String n, boolean ia, String c) {
        nom=n;
        score=0;
        scoreAffiché=0;
        this.ia=ia;
        this.armee=false;
        this.nbArmee=0;
        
        if(c.equals("Bleu") || c.equals("bleu"))
            this.couleur="\033[4;34m";
        else if(c.equals("Blanc") || c.equals("blanc"))
            this.couleur="\033[4;37m";
        else if(c.equals("Rouge") || c.equals("rouge"))
            this.couleur="\033[4;31m";
        else if(c.equals("Vert") || c.equals("vert"))
            this.couleur="\033[4;32m";    
            
        nbArgile=0;
        nbBlé=0;
        nbBois=0;
        nbLaine=0;
        nbMinerai=0;
        totalRessources=nbArgile+nbBlé+nbBois+nbLaine+nbMinerai;

        this.niveauPort=new Port();
        
        bats=new ArrayList<Cases>();
        routes=new ArrayList<Cases>();
        cartes = new ArrayList<Cartes>();
        totalCartes=0;
        
        nbColonies=5;
        nbVilles=4;
        nbRoutes=15;
        totalPieces=nbColonies+nbVilles+nbRoutes;
    }
    
    /**
     * Mets à  jour le nombre de ressources que possède le Joueur
     */
    public void majNbRessources(){
        totalRessources=nbArgile+nbBois+nbBlé+nbLaine+nbMinerai;
    }

    /**
     * Mets à  jour le nombre de pièces (routes, colonnies etc...) que possède le Joueur hors du plateau
     */

    public void majNbPieces(){
        totalPieces=nbColonies+nbVilles+nbRoutes;
    }

    // GETTER ET SETTER DU NOMBRE D'ARGILE
    public int getNbArgile() {
        return nbArgile;
    }

    public void setNbArgile(int n) {
        this.nbArgile = n;
        majNbRessources();
    }

    public void incrArgile(int n){
        this.nbArgile=this.nbArgile+n;
        majNbRessources();
    }

    public void decrArgile(int n){
        this.nbArgile=this.nbArgile-n;
        majNbRessources();
    }

    // GETTER ET SETTER DU NOMBRE DE BLE
    public int getNbBlé() {
        return nbBlé;
    }

    public void setNbBlé(int n) {
        this.nbBlé = n;
        majNbRessources();
    }

    public void incrBlé(int n){
        this.nbBlé=nbBlé+n;
        majNbRessources();
    }

    public void decrBlé(int n){
        this.nbBlé=nbBlé-n;
        majNbRessources();
    }

    // GETTER ET SETTER DU NOMBRE DE BOIS
    public int getNbBois() {
        return nbBois;
    }

    public void setNbBois(int n) {
        this.nbBois = n;
        majNbRessources();
    }

    public void incrBois(int n){
        this.nbBois=this.nbBois+n;
        majNbRessources();
    }

    public void decrBois(int n){
        this.nbBois=this.nbBois-n;
        majNbRessources();
    }

    // GETTER ET SETTER DU NOMBRE D'ARGILE
    public int getNbLaine() {
        return nbLaine;
    }

    public void setNbLaine(int n) {
        this.nbLaine = n;
        majNbRessources();
    }

    public void incrLaine(int n){
        this.nbLaine=this.nbLaine+n;
        majNbRessources();
    }

    public void decrLaine(int n){
        this.nbLaine=this.nbLaine-n;
        majNbRessources();
    }

    // GETTER ET SETTER DU NOMBRE DE MINERAIS
    public int getNbMinerai() {
        return nbMinerai;
    }

    public void setNbMinerai(int n) {
        this.nbMinerai = n;
        majNbRessources();
    }

    public void incrMinerai(int n){
        this.nbMinerai=this.nbMinerai+n;
        majNbRessources();
    }

    public void decrMinerai(int n){
        this.nbMinerai=this.nbMinerai-n;
        majNbRessources();
    }

    // GETTER récupérant les bâtiments possédées par le joueur sur le plateau
    public ArrayList<Cases> getBats(){
        return bats;
    }

    // GETTER récupérant les routes possédées par le joueur sur le plateau
    public ArrayList<Cases> getRoutes(){
        return routes;
    }

    // GETTER ET SETTER DU NOMBRE DE PIECES COLONIE POSSEDEES PAR LE JOUEUR
    public int getNbColonies() {
        return nbColonies;
    }

    public void setNbColonies(int nbColonies){
        this.nbColonies = nbColonies;
        majNbPieces();
    }

    public void incrNbColonies(int n) {
        this.nbColonies = nbColonies+n;
        majNbPieces();
    }

    public void decrNbColonies(int n) {
        this.nbColonies = nbColonies-n;
        majNbPieces();
    }

    // GETTER ET SETTER DU NOMBRE DE PIECES VILLE POSSEDEES PAR LE JOUEUR
    public int getNbVilles() {
        return nbVilles;
    }

    public void setNbVilles(int nbVilles) {
        this.nbVilles = nbVilles;
        majNbPieces();
    }

    public void incrNbVilles(int n) {
        this.nbVilles = nbVilles+n;
        majNbPieces();
    }

    public void decrNbVilles(int n) {
        this.nbVilles = nbVilles-n;
        majNbPieces();
    }

    // GETTER ET SETTER DU NOMBRE DE PIECES ROUTE POSSEDEES PAR LE JOUEUR
    public int getNbRoutes() {
        return nbRoutes;
    }

    public void setNbRoutes(int nbRoutes) {
        this.nbRoutes = nbRoutes;
        majNbPieces();
    }

    public void incrNbRoutes(int n) {
        this.nbRoutes = nbRoutes+n;
        majNbPieces();
    }

    public void decrNbRoutes(int n) {
        this.nbRoutes = nbRoutes-n;
        majNbPieces();
    }
    
    // Autres Getter et Setter 

    /**
     * Récupère le nom du Joueur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère la couleur associée au Joueur
     */
    public String getCouleur(){
        return couleur;
    }

    /**
     * Récupère le score du Joueur visible par tout les autres Joueurs
     */
    public int getScoreAff() {
        return scoreAffiché;
    }
    
    /**
     * Défini score du Joueur visible par tout les autres Joueurs
     * @param score 
     */
    public void setScoreAff(int score) {
        this.scoreAffiché = score;
    }

    /**
     * Incrémente de n le score du Joueur visible par tout les autres Joueurs
     */
    public void incrScoreAff(int n) {
    	this.scoreAffiché= this.scoreAffiché+n;
    }

    /**
     * Décrémente de n le score du Joueur visible par tout les autres Joueurs
     */
    public void decrScoreAff(int n) {
    	this.scoreAffiché= this.scoreAffiché-n;
    }

    /**
     * Récupère le "vrai" score du Joueur (avec carte(s) dev...)
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Défini le "vrai" score du Joueur (avec carte(s) dev...)
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * Incrémente de n le "vrai" score du Joueur (avec carte(s) dev...)
     * @param n
     */
    public void incrScore(int n) {
    	this.score= this.score+n;
    }
    
    /**
     * Décrémente de n le "vrai" score du Joueur (avec carte(s) dev...)
     * @param n
     */
    public void decrScore(int n) {
    	this.score= this.score-n;
    }
    
    /**
     * Récupère le nombre total de ressources possédées Joueur
     */
    public int getTotalRessources() {
        return totalRessources;
    }

    /**
     * Récupère le nombre total de cartes possédées par Joueur
     */
    public int getTotalCartes() {
        return totalCartes;
    }
    
    /**
     * Incrémente de n le nombre total de cartes possédées par le Joueur
     * @param totalCartes
     * @param n
     */
    public void setTotalCartes(int totalCartes, int n) {
        this.totalCartes = totalCartes+n;
    }

    /**
     * Récupère la liste contenant toute les cartes possédées par le Joueur
     */
    public ArrayList<Cartes> getCartes(){
    	return this.cartes;
    }

    /**
     * Incrémente le nombre total de cartes du Joueur
     */
    public void incrCartes(){
    	this.totalCartes++;
    }

    /**
     * Décrémente le nombre total de cartes du Joueur
     */
    public void decrCartes() {
    	this.totalCartes--;
    }
    
    /**
     * Récupère le nombre de pièces que peut encore jouer le Joueur
     */
    public int getTotalPieces() {
        return totalPieces;
    }
    
    /**
     * Renvoie true si le Joueur est un IA, sinon false
     */
    public boolean getIA() {
    	return this.ia;
    }

    /**
     * Renvoie true si le Joueur a la plus grand armée
     */
    public boolean getArmee() {
    	return this.armee;
    }
    
    /**
     * Modifie le statut "a la plus grande armée" du Joueur
     * @param b booléen 
     */
    public void setArmee(boolean b) {
    	this.armee = b;
    }
    
    /**
     * Récupère le nombre de chevalier(s) que possède le Joueur
     */
    public int getNbArmee() {
    	return this.nbArmee;
    }
    
    /**
     * Incrémente le nombre de chevalier(s) que possède le Joueur
     */
    public void incrArmee() {
    	this.nbArmee++;
    }
    
    /**
     * Récupère l'objet Port contenant les infos des ports possédés par le Joueur
     */
    public Port getPort() {
    	return this.niveauPort;
    }
}
