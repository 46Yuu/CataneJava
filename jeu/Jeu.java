package jeu;

import java.util.*;
import plateau.Cases;
import plateau.Plateau;
/**
 * Classe modélisant le Jeu de Catane (version console de commande)
 */
public class Jeu {
	private Plateau plateau;
	private Joueur[] Joueurs;
	private int tour;
	private Scanner scanReponse;
	private boolean finTour = false;
	private Pile pileCarte;
	private int nTour;

	public static final String RESET = "\033[0m";  // Text Reset
	public static final String BLACK = "\033[0;30m";   // BLACK
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
	public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
	public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
	public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
	
	public final String bleu= "\033[4;34m";   // BLUE
	public final String blanc = "\033[4;37m";  // WHITE
	public final String rouge= "\033[4;31m";    // RED
	public final String vert = "\033[4;32m";  // GREEN
	public ArrayList<String> couleursJoueurs;
	
	 /**
     * Lance toutes les fonctions nécessaire pour jouer une partie de Catane.
     */
	public Jeu() {
		this.nTour=0;
		pileCarte = new Pile();
		this.scanReponse= new Scanner(System.in);
		int tmp = combienDeJoueur();
		this.Joueurs = new Joueur[tmp];
		setCouleurs();
		setJoueur();
		this.plateau = new Plateau();
		setInitial();
		boolean fini = fini();
		while(!fini) {
			tourDe(this.tour);
			fini = fini();
		}
		afficherStats();
	}
	
	 /**
     * Demande et récupère le nombre de joueur pour la partie.
     */
	public int combienDeJoueur() {
		System.out.println("Combien de joueurs ? (3 ou 4)");
		boolean bon = false;
		int nb =0;
		while(!bon) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire 3 ou 4:");
				scanReponse.next();
			}
			nb = scanReponse.nextInt();
			if(nb <3 || nb >4) {
				System.out.println("Nombre non accepté. Veuillez écrire 3 ou 4:");
			}
			else {
				bon = true;
			}
		}
		return nb;
	}

	/**
     * Crée les joueurs en laissant le choix d'un joueur humain ou IA , son nom si c'est un humain , et la couleur du joueur.
     */
	public void setJoueur() {
		for(int i=0;i<this.Joueurs.length;i++) {
			while(this.Joueurs[i]==null) {
				System.out.println("Joueur "+(i+1)+" est un 'humain' ou un 'IA'?");
				String reponse = scanReponse.next();
				if(reponse.equals("humain")|| reponse.equals("Humain")) {
					System.out.println("Quel est le nom du Joueur "+(i+1)+" ?");
					String nomJoueur = scanReponse.next();
					String coulJoueur=" ";
					while(!(coulJoueur.equals("Bleu") || coulJoueur.equals("Blanc")|| coulJoueur.equals("Rouge") || coulJoueur.equals("Vert") || coulJoueur.equals("bleu") || coulJoueur.equals("blanc") || coulJoueur.equals("rouge") || coulJoueur.equals("vert"))){
						System.out.print("Choisissez la couleur du Joueur "+(i+1)+") "+nomJoueur+" : ");
						choixCouleur();
						coulJoueur = scanReponse.next();
					}retirerChoixCouleur(coulJoueur);
					this.Joueurs[i] = new Joueur(nomJoueur,false,coulJoueur);
				}
				if(reponse.equals("IA")||reponse.equals("ia")) {
					String nomIA = "IA"+(i+1);
					String coulJoueur=" ";
					while(!(coulJoueur.equals("Bleu") || coulJoueur.equals("Blanc")|| coulJoueur.equals("Rouge") || coulJoueur.equals("Vert") || coulJoueur.equals("bleu") || coulJoueur.equals("blanc") || coulJoueur.equals("rouge") || coulJoueur.equals("vert"))){
						System.out.print("Choisissez la couleur du Joueur "+(i+1)+") (IA) : ");
						choixCouleur();
						coulJoueur = scanReponse.next();
					}retirerChoixCouleur(coulJoueur);
					this.Joueurs[i] = new Joueur(nomIA,true,coulJoueur);
				}
			}
		}
	}

	/**
     * Met en place les couleurs qui vont être utilisés pour le choix des couleurs des joueurs. 
     */
	public void setCouleurs(){
		couleursJoueurs=new ArrayList<String>();
		couleursJoueurs.add(bleu);
		couleursJoueurs.add(blanc);
		couleursJoueurs.add(rouge);
		couleursJoueurs.add(vert);
	}
	
	/**
     * Retire des choix de couleurs possible ceux déjà  utilisés.
     */
	public void retirerChoixCouleur(String couleur){
		if(couleur.equals("Bleu") || couleur.equals("bleu") )
			couleursJoueurs.remove("\033[4;34m");
		if(couleur.equals("Blanc")|| couleur.equals("blanc"))
			couleursJoueurs.remove("\033[4;37m");
		if(couleur.equals("Rouge")|| couleur.equals("rouge"))
			couleursJoueurs.remove("\033[4;31m");
		if(couleur.equals("Vert")|| couleur.equals("vert"))
			couleursJoueurs.remove("\033[4;32m");
		}

	/**
     * Affiche les choix possibles de couleur.
     */
	public void choixCouleur(){
		for(int i=0;i<couleursJoueurs.size();i++){
			if(couleursJoueurs.get(i).equals("\033[4;34m"))
				System.out.print(bleu+"Bleu"+RESET+"  ");
			else if(couleursJoueurs.get(i).equals("\033[4;37m"))
				System.out.print(blanc+"Blanc"+RESET+"  ");
			else if(couleursJoueurs.get(i).equals("\033[4;31m"))
				System.out.print(rouge+"Rouge"+RESET+"  ");
			else if(couleursJoueurs.get(i).equals("\033[4;32m"))
				System.out.println(vert+"Vert"+RESET+"  ");
		}
	}
	
	/**
     * Lance toutes les fonctions nécessaire pour faire le premier tour des joueurs afin qu'ils placent leur premieres colonies et premieres routes. 
     */
	public void setInitial() {
		boolean retour = false;
		for(this.tour =0;tour<this.Joueurs.length;this.tour++) {
			this.nTour++;
			this.afficherPlateau(plateau.getPlateau());
			ArrayList<Cases> colPoss = this.plateau.getColPossible();
			System.out.println("\n"+this.Joueurs[this.tour].getNom() + " veuillez poser votre premiere colonie.");
			Cases tmp = choixColIni(colPoss,this.tour,retour);
			ArrayList<Cases> routePoss = this.plateau.getRoutesIni(tmp,this.Joueurs[this.tour]);
			this.afficherPlateau(plateau.getPlateau());
			System.out.println("\n"+this.Joueurs[this.tour].getNom() + " veuillez poser la route.");
			choixRouteIni(routePoss,this.tour);
		}
		setInitialRetour(true);
	}
	
	/**
	 * Lance les fonctions nécessaire pour faire le deuxieme tour a l'envers pour placer les secondes colonies et routes , et recupérer les ressources de celles ci. 
     *	@param retour : boolean indiquant true si tout les joueurs on déjà  placé leur premiere colonie , et permet ainsi d'obtenir les ressources grace au placement du second.
     */
	public void setInitialRetour(boolean retour) {
		for(tour =this.Joueurs.length-1;tour>-1;tour--) {
			this.nTour++;
			this.afficherPlateau(plateau.getPlateau());
			ArrayList<Cases> colPoss = this.plateau.getColPossible();
			System.out.println("\n"+this.Joueurs[this.tour].getNom() + " veuillez poser votre deuxieme colonie.");
			Cases tmp =choixColIni(colPoss,tour,retour);
			ArrayList<Cases> routePoss = this.plateau.getRoutesIni(tmp,this.Joueurs[this.tour]);
			this.afficherPlateau(plateau.getPlateau());
			System.out.println("\n"+this.Joueurs[this.tour].getNom() + " veuillez poser la route.");
			choixRouteIni(routePoss,this.tour);
		}
		tour = 0;
	}

	/**
     * Choix de placement des deux premieres colonies pour le joueur de ce tour.
     * @param colPoss : ArrayList<Cases> indiquant les colonies placable pour le joueur.
     * @param tour : int permettant de savoir quel joueur doit placer sa colonie.
     * @param retour : boolean qui permet d'obtenir les ressources si true.
     */
	public Cases choixColIni(ArrayList<Cases> colPoss,int tour,boolean retour) {
		int compteur = 0;
		for(Cases c: colPoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(colPoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <colPoss.size()) {
			boolean valider = validerChoix(reponse,colPoss);
			if(valider) {
				this.plateau.setCol(this.plateau.getPlateau()[colPoss.get(reponse).getY()][colPoss.get(reponse).getX()], this.Joueurs[tour]);
				this.Joueurs[tour].decrNbColonies(1);
				if(retour == true) {
					ArrayList<Cases> gainRessource = this.plateau.getIniGain(this.plateau.getPlateau()[colPoss.get(reponse).getY()][colPoss.get(reponse).getX()]);
					while(!gainRessource.isEmpty()) {
						ajoutIniGain(gainRessource);
						gainRessource.remove(0);
					}
				}
				this.Joueurs[tour].getBats().add(this.plateau.getPlateau()[colPoss.get(reponse).getY()][colPoss.get(reponse).getX()]);
				this.Joueurs[tour].incrScore(1);
				this.Joueurs[tour].incrScoreAff(1);
				checkPort(this.plateau.getPlateau()[colPoss.get(reponse).getY()][colPoss.get(reponse).getX()],this.Joueurs[tour]);
				return this.plateau.getPlateau()[colPoss.get(reponse).getY()][colPoss.get(reponse).getX()];
			}
			else {
				return choixColIni(colPoss,tour,retour);
			}
		}
		else {
			System.out.println("Nombre non accepté. Réecrivez un nombre:");
			return choixColIni(colPoss,tour,retour);
		}
	}

	/**
	 * Choix des deux premieres routes que le joueur de ce tour doit placer.
     * @param routePoss : ArrayList<Cases> des routes construisables pour le joueur.
     * @param tour : int permettant de savoir quel joueur doit placer sa route.
     */
	public void choixRouteIni(ArrayList<Cases> routePoss,int tour) {
		int compteur = 0;
		for(Cases c: routePoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(routePoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <routePoss.size()) {
			boolean valider = validerChoix(reponse,routePoss);
			if(valider) {
				this.plateau.setRoute(this.plateau.getPlateau()[routePoss.get(reponse).getY()][routePoss.get(reponse).getX()], this.Joueurs[tour]);
				this.Joueurs[tour].decrNbRoutes(1);
				this.Joueurs[tour].getRoutes().add(this.plateau.getPlateau()[routePoss.get(reponse).getY()][routePoss.get(reponse).getX()]);
			}
			else {
				choixRouteIni(routePoss,tour);
			}
		}
		else {
			System.out.println("Nombre non accepté. Réecrivez un nombre:");
			choixRouteIni(routePoss,tour);
		}
	}

	/**
	 * Donne les ressources obtenus grace a la construction de la deuxieme colonie pour le joueur.
	 * @param gainRessource : ArrayList<Cases> qui indique les cases biomes autour de la case de la deuxieme colonie crée pendant l'initialisation de la partie.
     */
	public void ajoutIniGain(ArrayList<Cases> gainRessource) {
		if(gainRessource.get(0).getTypeBiome().equals("Forêt")) {
			System.out.println(this.Joueurs[this.tour].getNom()+" a obtenu "+1+ " bois.");
			this.Joueurs[this.tour].incrBois(1);
		}
		if(gainRessource.get(0).getTypeBiome().equals("Pré")) {
			System.out.println(this.Joueurs[this.tour].getNom()+" a obtenu "+1+ " laine.");
			this.Joueurs[this.tour].incrLaine(1);
		}
		if(gainRessource.get(0).getTypeBiome().equals("Champs")) {
			System.out.println(this.Joueurs[this.tour].getNom()+" a obtenu "+1+ " blé.");
			this.Joueurs[this.tour].incrBlé(1);
		}
		if(gainRessource.get(0).getTypeBiome().equals("Colline")) {
			System.out.println(this.Joueurs[this.tour].getNom()+" a obtenu "+1+ " argile.");
			this.Joueurs[this.tour].incrArgile(1);
		}
		if(gainRessource.get(0).getTypeBiome().equals("Montagne")) {
			System.out.println(this.Joueurs[this.tour].getNom()+" a obtenu "+1+ " minerai.");
			this.Joueurs[this.tour].incrMinerai(1);
		}
	}

	
	/**
	 * Lance le tour de chaque joueur jusqu'a qu'ils terminent leur tour.
	 * @param tour : int permettant de savoir quel joueur doit jouer. 
     */
	public void tourDe(int tour) {
		this.nTour++;
		finTour = false;
		System.out.println("\nC'est le tour de "+this.Joueurs[this.tour].getNom() + " tour numéro : "+this.nTour);
		if(this.Joueurs[this.tour].getIA()) {
			lancerEtAjout();
			while(finTour == false) {
				actions();
			}
			if(tour==this.Joueurs.length-1) {
				this.tour =0;
			}
			else {
				this.tour++;
			}
		}
		else {
			lancerEtAjout();
			while(finTour == false) {
				actions();
			}
			if(tour==this.Joueurs.length-1) {
				this.tour =0;
			}
			else {
				this.tour++;
			}
		}
	}
	
	/**
	 * Appelle la fonction pour lancer les dés et ajouter les ressources aux joueur qui ont des batiments autour du biome avec le nombre resultat des dés. 
     */
	public void lancerEtAjout() {
		int DésTotal = lancerDés();
		Cases[] resultat;
		if(DésTotal == 4 || DésTotal == 5 || DésTotal == 6 || DésTotal == 8 || DésTotal == 9 ) {
			resultat = getResultat(2,DésTotal);
			ajoutRessources(resultat);
		}
		else if(DésTotal == 7) {
			voleur();
			voleurPlacement();
		}
		else {
			resultat = getResultat(1,DésTotal);
			ajoutRessources(resultat);
		}
	}
	
	/**
	 * Lance les dés quand le joueur écrit "lancer" ou "Lancer".
	 * @return Le résultat des deux dés.
     */
	public int lancerDés() {
		int Dés1 = 0;
		int Dés2 =0;
		int DésTotal = 0;
		while(DésTotal==0) {
			System.out.println(this.Joueurs[tour].getNom()+" écrivez 'Lancer' pour lancer vos Dés !");
			String tmp ="";
			if(this.Joueurs[tour].getIA()) {
				tmp = "Lancer";
				System.out.println("Lancer");
			}
			else {
				tmp = scanReponse.next();
			}
			if(tmp.equals("Lancer") || tmp.equals("lancer")) {
				Random random = new Random();
				Dés1 = random.nextInt(6)+1;
				Dés2 = random.nextInt(6)+1;
				DésTotal = Dés1+Dés2;
			}
			else {
				System.out.println("Reponse non accepté. Veuillez bien écrire 'lancer' ou 'Lancer'.");
			}	
		}
		System.out.println(this.Joueurs[tour].getNom()+ " a obtenu "+Dés1+" et "+Dés2+". Total : "+DésTotal+"\n");
		return DésTotal;
	}

	/**
	 * Recupère les Cases biomes qui ont le meme nombre que le résultat des dés "DésTotal".
	 * @param n : int indiquant le nombre de cases que le tableau doit retourner. 2 si le resultat des dés est : 4, 5, 6, 8, 9 ; et 1 si le resultat est autre.
	 * @param DésTotal : int indiquant le résultat des dés.
	 * @return Un tableau de cases Biomes qui correspondent au résultat des dés.
     */
	public Cases[] getResultat(int n,int DésTotal) {
		int x =1;
		int y =1;
		Cases[] res =new Cases[n];
		int resN = 0;
		while(resN<res.length) {
			for(y=1;y<this.plateau.getPlateau().length;y=y+2) {
				for(x=1;x<this.plateau.getPlateau()[y].length;x= x+2) {
					if(DésTotal == this.plateau.getPlateau()[y][x].getNum()) {
						res[resN]=this.plateau.getPlateau()[y][x];
						resN++;
					}
				}
			}
		}
		return res;
	}

	/**
	 * Ajoute les ressources obtenus par les proprietaires des batiments adjacent aux biomes qui correspondent au résultat des dés.
	 * @param res : tableau de Cases contenant les cases biomes qui correspondent au résultat des dés.
     */
	public void ajoutRessources(Cases[] res) {
		for(int caseRes = 0;caseRes<res.length;caseRes++) {
			if(!res[caseRes].getVoleur()) {
				ArrayList<Cases> gainRessource = this.plateau.getBatResultat(res[caseRes]);
				while(!gainRessource.isEmpty()) {
					for(int nJoueur = 0;nJoueur<this.Joueurs.length;nJoueur++) {
						if(this.Joueurs[nJoueur].getNom().equals(gainRessource.get(0).getProprietaire())) {
							if(gainRessource.get(0).getTypeBat()==2) {
								gainType(res,caseRes,nJoueur,2);
							}
							if(gainRessource.get(0).getTypeBat()==1) {
								gainType(res,caseRes,nJoueur,1);
							}
						}
					}
					gainRessource.remove(0);
				}
			}
		}
	}
	
	/**
	 * Donne les gains de chaque type de biome a chaque joueur.
	 * @param res : tableau des Cases biomes qui correspondent au total des dés.
	 * @param caseRes : int indiquant quel case du tableau res doit on acceder.
	 * @param nJoueur : int du joueur qui va obtenir la ressource.
	 * @param n : nombre de ressource que le joueur va recevoir selon si c'est une colonie ou une ville.
     */
	public void gainType(Cases[] res,int caseRes,int nJoueur,int n) {
		if(res[caseRes].getTypeBiome() == "Forêt") {
			System.out.println(this.Joueurs[nJoueur].getNom()+" a obtenu "+n+ " bois.");
			this.Joueurs[nJoueur].incrBois(n);
		}
		if(res[caseRes].getTypeBiome() == "Pré") {
			System.out.println(this.Joueurs[nJoueur].getNom()+" a obtenu "+n+ " laine.");
			this.Joueurs[nJoueur].incrLaine(n);
		}
		if(res[caseRes].getTypeBiome() == "Champs") {
			System.out.println(this.Joueurs[nJoueur].getNom()+" a obtenu "+n+ " blé.");
			this.Joueurs[nJoueur].incrBlé(n);
		}
		if(res[caseRes].getTypeBiome() == "Colline") {
			System.out.println(this.Joueurs[nJoueur].getNom()+" a obtenu "+n+ " argile.");
			this.Joueurs[nJoueur].incrArgile(n);
		}
		if(res[caseRes].getTypeBiome() == "Montagne") {
			System.out.println(this.Joueurs[nJoueur].getNom()+" a obtenu "+n+ " minerai.");
			this.Joueurs[nJoueur].incrMinerai(n);
		}
	}

	/**
	 * Appelle les fonctions qui font tout ce qui est necessaire au voleur quand on obtient 7 aux dés.
     */
	public void voleur() {
		int tmp=0;
		for(int i =0;i<this.Joueurs.length;i++) {
			if(this.Joueurs[i].getTotalRessources()>7) {
				tmp = this.Joueurs[i].getTotalRessources()/2;
				System.out.println(this.Joueurs[i].getNom()+" veuillez choisir "+tmp+" de vos ressources a défausser");
				while(tmp>0) {
					defausserChoix(this.Joueurs[i],tmp);
					tmp = defausser(this.Joueurs[i],tmp);
				}
			}
		}
	}

	/**
	 * Demande au joueur de choisir quel ressource veut t'il défausser en affichant les choix possibles et son nombre de ressource.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
     */
	public void defausserChoix(Joueur j,int n){
		afficherRessources(j);
		System.out.println(j.getNom()+" vous devez encore défausser "+n+" ressources.");
		System.out.println("Choisissez une ressource que vous voulez défausser :");
		if(j.getNbBois()>0) {
			System.out.print("'bois' ");
		}
		if(j.getNbLaine()>0) {
			System.out.print("'laine' ");
		}
		if(j.getNbBlé()>0) {
			System.out.print("'blé' ");
		}
		if(j.getNbArgile()>0) {
			System.out.print("'argile' ");
		}
		if(j.getNbMinerai()>0) {
			System.out.print("'minerai'\n");
		}
	}

	/**
	 * Appel les fonctions pour défausser une ressource selon la réponse du joueur.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
	 * @return Le nombre de ressources a défausser.
     */
	public int defausser(Joueur j,int n) {
		String reponse ="";
		ArrayList<String> deffausseIA = new ArrayList<String>();
		if(j.getIA()) {
			if(j.getNbBois()>0) {
				deffausseIA.add("bois");
			}
			if(j.getNbLaine()>0) {
				deffausseIA.add("laine");
			}
			if(j.getNbBlé()>0) {
				deffausseIA.add("blé");
			}
			if(j.getNbArgile()>0) {
				deffausseIA.add("argile");
			}
			if(j.getNbMinerai()>0) {
				deffausseIA.add("minerai");
			}
			Random r = new Random();
			int tmp = r.nextInt(deffausseIA.size());
			reponse = deffausseIA.get(tmp);
			System.out.println(reponse);
		}
		else {
			reponse = scanReponse.next();
		}
		if(reponse.equals("bois") && j.getNbBois()>0) {
			return defausserNombreBois(j,n,reponse);
		}
		else if(reponse.equals("laine") && j.getNbLaine()>0) {
			return defausserNombreLaine(j,n,reponse);
		}
		else if(reponse.equals("blé") && j.getNbBlé()>0) {
			return defausserNombreBlé(j,n,reponse);
		}
		else if(reponse.equals("argile") && j.getNbArgile()>0) {
			return defausserNombreArgile(j,n,reponse);
		}
		else if(reponse.equals("minerai") && j.getNbMinerai()>0) {
			return defausserNombreMinerai(j,n,reponse);
		}
		else {			
			System.out.println("Erreur , veuillez réecrire votre choix :");
			return defausser(j,n);
		}
	}

	/**
	 * Demande la validation du joueur pour défausser n ressources.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources a défausser.
	 * @param reponse : choix de ressource du joueur a défausser.
	 * @return true si le joueur répond "oui" , false si le joueur répond "non".
     */
	public boolean validerChoixDéfausse(Joueur j,int n,String reponse) {
		System.out.println("Validez vous votre choix suivant : défausser "+n+" "+reponse+" ?");
		String rep = "";
		if(j.getIA()) {
			rep = "oui";
			System.out.println("oui");
		}
		else{
			rep = scanReponse.next();
		}
		if(rep.equals("oui")) {
			return true;
		}
		if(rep.equals("non")) {
			return false;
		}
		else {
			System.out.println("Choix non accepté. Veuillez ecrire soit 'oui', soit 'non'.");
			return validerChoixDéfausse(j,n,reponse);
		}
	}

	/**
	 * Demande le nombre de bois a défausser.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
	 * @param reponse : choix de ressource du joueur a défausser.
	 * @return Le nombre de bois a défausser.
     */
	public int defausserNombreBois(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous défausser de "+reponse+", ou écrivez -1 pour retourner au choix des ressources.");
		int tmp = 0;
		if(j.getIA()) {
			if(j.getNbBois()<n) {
				tmp = j.getNbBois();
			}
			else {
				tmp = n;
			}
		}
		else{
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			tmp = scanReponse.nextInt();
		}
		System.out.println(tmp);
		if(tmp>=0 && tmp<=j.getNbBois() && tmp<=n) {
			boolean valider = validerChoixDéfausse(j,tmp,reponse);
			if(valider) {
				j.decrBois(tmp);
				return n-tmp;
			}
			else {
				return defausserNombreBois(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbBois()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return defausserNombreBois(j,n,reponse);
		}
		else {
			return n;
		}
	}

	/**
	 * Demande le nombre de laine a défausser.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
	 * @param reponse : choix de ressource du joueur a défausser.
	 * @return Le nombre de laine a défausser.
     */
	public int defausserNombreLaine(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous défausser de "+reponse+", ou écrivez -1 pour retourner au choix des ressources.");
		int tmp = 0;
		if(j.getIA()) {
			if(j.getNbLaine()<n) {
				tmp = j.getNbLaine();
			}
			else {
				tmp = n;
			}
		}
		else{
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			tmp = scanReponse.nextInt();
		}
		System.out.println(tmp);
		if(tmp>=0 && tmp<=j.getNbLaine() && tmp<=n) {
			boolean valider = validerChoixDéfausse(j,tmp,reponse);
			if(valider) {
				j.decrLaine(tmp);
				return n-tmp;
			}
			else {
				return defausserNombreLaine(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbLaine()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return defausserNombreLaine(j,n,reponse);
		}
		else {
			return n;
		}
	}

	/**
	 * Demande le nombre de blé a défausser.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
	 * @param reponse : choix de ressource du joueur a défausser.
	 * @return Le nombre de blé a défausser.
     */
	public int defausserNombreBlé(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous défausser de "+reponse+", ou écrivez -1 pour retourner au choix des ressources.");
		int tmp = 0;
		if(j.getIA()) {
			if(j.getNbBlé()<n) {
				tmp = j.getNbBlé();
			}
			else {
				tmp = n;
			}
		}
		else{
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			tmp = scanReponse.nextInt();
		}
		System.out.println(tmp);
		if(tmp>=0 && tmp<=j.getNbBlé() && tmp<=n) {
			boolean valider = validerChoixDéfausse(j,tmp,reponse);
			if(valider) {
				j.decrBlé(tmp);
				return n-tmp;
			}
			else {
				return defausserNombreBlé(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbBlé()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return defausserNombreBlé(j,n,reponse);
		}
		else {
			return n;
		}
	}
	
	/**
	 * Demande le nombre de argile a défausser.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
	 * @param reponse : choix de ressource du joueur a défausser.
	 * @return Le nombre d'argile a défausser.
     */
	public int defausserNombreArgile(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous défausser de "+reponse+", ou écrivez -1 pour retourner au choix des ressources.");
		int tmp = 0;
		if(j.getIA()) {
			if(j.getNbArgile()<n) {
				tmp = j.getNbArgile();
			}
			else {
				tmp = n;
			}
		}
		else{
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			tmp = scanReponse.nextInt();
		}
		System.out.println(tmp);
		if(tmp>=0 && tmp<=j.getNbArgile() && tmp<=n) {
			boolean valider = validerChoixDéfausse(j,tmp,reponse);
			if(valider) {
				j.decrArgile(tmp);
				return n-tmp;
			}
			else {
				return defausserNombreArgile(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbArgile()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return defausserNombreArgile(j,n,reponse);
		}
		else {
			return n;
		}
	}

	/**
	 * Demande le nombre de minerai a défausser.
	 * @param j : joueur qui doit défausser des ressources.
	 * @param n : nombre de ressources restantes a défausser.
	 * @param reponse : choix de ressource du joueur a défausser.
	 * @return Le nombre de minerai a défausser.
     */
	public int defausserNombreMinerai(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous défausser de "+reponse+", ou écrivez -1 pour retourner au choix des ressources.");
		int tmp = 0;
		if(j.getIA()) {
			if(j.getNbMinerai()<n) {
				tmp = j.getNbMinerai();
			}
			else {
				tmp = n;
			}
		}
		else{
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			tmp = scanReponse.nextInt();
		}
		System.out.println(tmp);
		if(tmp>=0 && tmp<=j.getNbMinerai() && tmp<=n) {
			boolean valider = validerChoixDéfausse(j,tmp,reponse);
			if(valider) {
				j.decrMinerai(tmp);
				return n-tmp;
			}
			else {
				return defausserNombreMinerai(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbMinerai()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return defausserNombreMinerai(j,n,reponse);
		}
		else {
			return n;
		}
	}

	/**
	 * Lance les fonctions necessaires pour enlever et laisser le choix au joueur de placer le voleur puis de voler un joueur.
     */
	public void voleurPlacement() {
		this.afficherPlateau(plateau.getPlateau());
		afficherRessources(this.Joueurs[this.tour]);
		System.out.println(this.Joueurs[this.tour].getNom()+" veuillez choisir la case où placer le voleur.");
		ArrayList<Cases> placePoss = this.plateau.getCasesBiomes();
		int compteur = 0;
		for(Cases c:placePoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
	
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(placePoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <placePoss.size()) {
			boolean valider = validerChoix(reponse,placePoss);
			if(valider) {
				removeVoleur();
				this.plateau.getPlateau()[placePoss.get(reponse).getY()][placePoss.get(reponse).getX()].setVoleur(true);
				volerA(this.plateau.getPlateau()[placePoss.get(reponse).getY()][placePoss.get(reponse).getX()]);
			}
			else {
				voleurPlacement();
			}
		}
		if(reponse <-1 || reponse >placePoss.size()) {
			System.out.println("Nombre non accepté.");
			voleurPlacement();
		}
	}

	/**
	 * Ajoute a volPossible les choix possible de vol.
	 * @param batPoss : ArrayList<Cases> des batiments qui sont autour du biome ou le voleur est placé.
	 * @param volPossible : ArrayList<String> qui contiendra les nom des proprietaires des batiments autour du biome qui ne sont pas le joueur de ce tour.
     */
	public void choixVolPoss(ArrayList<Cases> batPoss,ArrayList<String> volPossible) {
		System.out.println("Vous pouvez voler a :");
		int compteur =0;
		for(Cases caseN: batPoss) {
			if(caseN.getProprietaire()!=this.Joueurs[this.tour].getNom()) {
				if(!appartient(caseN.getProprietaire(),volPossible)) {
					System.out.println(compteur+") "+caseN.getProprietaire());
					volPossible.add(caseN.getProprietaire());
					compteur++;
				}
			}
		}
	}

	/**
	 * Verifie si le joueur "nom" appartient a la liste "volPossible" ou non.
	 * @param nom : String du nom du joueur.
	 * @param volPossible : ArrayList<String> contenant les nom que le joueur de ce tour peut voler.
	 * @return true si le joueur appartient a l'arraylist , false sinon.
     */
	public boolean appartient(String nom,ArrayList<String> volPossible) {
		for(String s:volPossible) {
			if(s.equals(nom)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Effectue le vol de ressource sur la case c.
	 * @param c : Cases correspondant a la case a voler.
     */
	public void volerA(Cases c) {
		ArrayList<Cases> batPoss= this.plateau.getBatResultat(c);
		ArrayList<String> volPossible = new ArrayList<String>();
		if(!batPoss.isEmpty()) {
			choixVolPoss(batPoss,volPossible);
			if(!volPossible.isEmpty()) {
				int reponse = 0;
				if(!this.Joueurs[this.tour].getIA()) {
					while(!scanReponse.hasNextInt()) {
						System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
						scanReponse.next();
					}
					reponse = scanReponse.nextInt();
				}
				else {
					Random r = new Random();
					reponse = r.nextInt(volPossible.size());
					System.out.println(reponse);
				}
				if(reponse>=0 && reponse<volPossible.size()) {
					boolean valider = validerVol(volPossible.get(reponse));
					if(valider) {
						for(int i =0;i<this.Joueurs.length;i++) {
							if(this.Joueurs[i].getNom()==volPossible.get(reponse) && this.Joueurs[i].getTotalRessources()>0) {
								Random randomizer = new Random();
								ArrayList<String> ressourceVol = getRessourceVol(this.Joueurs[i]);
								int rand = randomizer.nextInt(ressourceVol.size());
								incrAndDecrVol(ressourceVol,rand,this.Joueurs[i]);
							}
							if(this.Joueurs[i].getNom()==volPossible.get(reponse) && this.Joueurs[i].getTotalRessources()==0) {
								System.out.println("Dommage , ce joueur n'as plus de ressources.");
							}
						}
					}
					else {
						volerA(c);
					}
				}
				if(reponse<0 || reponse>volPossible.size()) {
					System.out.println("Nombre non accepté.");
					volerA(c);
				}
			}
			else {
				System.out.println("Il n'y a personne a voler.");
			}
		}	
	}

	/**
	 * Recupere les ressources possible a voler et renvoie cette ArrayList<String>.
	 * @param j : Joueur qui va perdre ses ressources.
	 * @return Un ArrayList de String contenant les ressources possible a voler.
     */
	public ArrayList<String> getRessourceVol(Joueur j){
		ArrayList<String> ressourceVol = new ArrayList<String>();
		if(j.getNbBois()>0) {
			ressourceVol.add("bois");
		}
		if(j.getNbLaine()>0) {
			ressourceVol.add("laine");
		}
		if(j.getNbBlé()>0) {
			ressourceVol.add("blé");
		}
		if(j.getNbArgile()>0) {
			ressourceVol.add("argile");
		}
		if(j.getNbMinerai()>0) {
			ressourceVol.add("minerai");
		}
		return ressourceVol;
	}

	/**
	 * Incremente et decremente la ressource choisie aléatoirement pour le joueur de ce tour , et le joueur volé.
	 * @param ressourceVol : la liste des ressources possible a voler.
	 * @param rand : le resultat du choix aléatoire apartenant a ressourceVol.
	 * @param j : le joueur qui va se faire voler.
     */
	public void incrAndDecrVol(ArrayList<String> ressourceVol,int rand,Joueur j) {
		if(ressourceVol.get(rand).equals("bois")) {
			this.Joueurs[this.tour].incrBois(1);
			j.decrBois(1);
			System.out.print(this.Joueurs[this.tour].getNom()+" a obtenu 1 bois. ");
			System.out.print(j.getNom()+" a perdu 1 bois.\n");
		}
		if(ressourceVol.get(rand).equals("laine")) {
			this.Joueurs[this.tour].incrLaine(1);
			j.decrLaine(1);
			System.out.print(this.Joueurs[this.tour].getNom()+" a obtenu 1 laine. ");
			System.out.print(j.getNom()+" a perdu 1 laine.\n");
		}
		if(ressourceVol.get(rand).equals("blé")) {
			this.Joueurs[this.tour].incrBlé(1);
			j.decrBlé(1);
			System.out.print(this.Joueurs[this.tour].getNom()+" a obtenu 1 blé. ");
			System.out.print(j.getNom()+" a perdu 1 blé.\n");
		}
		if(ressourceVol.get(rand).equals("argile")) {
			this.Joueurs[this.tour].incrArgile(1);
			j.decrArgile(1);
			System.out.print(this.Joueurs[this.tour].getNom()+" a obtenu 1 argile. ");
			System.out.print(j.getNom()+" a perdu 1 argile.\n");
		}
		if(ressourceVol.get(rand).equals("minerai")) {
			this.Joueurs[this.tour].incrMinerai(1);
			j.decrMinerai(1);
			System.out.print(this.Joueurs[this.tour].getNom()+" a obtenu 1 minerai. ");
			System.out.print(j.getNom()+" a perdu 1 minerai.\n");
		}
	}

	/**
	 * Enleve le voleur du plateau.
     */
	public void removeVoleur() {
		ArrayList<Cases> toutBiome = this.plateau.getCasesBiomesRemove();
		for(Cases c:toutBiome) {
			if(c.getVoleur()) {
				c.setVoleur(false);
			}
		}
	}

	/**
	 * Demande la validation de vol sur le joueur "reponse".
	 * @param reponse : nom du joueur a voler.
	 * @return true si le joueur répond "oui" , false si le joueur répond "non".
     */
	public boolean validerVol(String reponse) {
		System.out.println("Validez vous votre choix suivant : voler "+reponse+" ?");
		String rep = "";
		if(this.Joueurs[this.tour].getIA()) {
			rep = "oui";
			System.out.println("oui");
		}
		else{
			rep = scanReponse.next();
		}
		if(rep.equals("oui")) {
			return true;
		}
		if(rep.equals("non")) {
			return false;
		}
		else {
			System.out.println("Choix non accepté. Veuillez ecrire soit 'oui', soit 'non'.");
			return validerVol(reponse);
		}
	}

	/**
	 * Appelle toutes les fonctions necessaires permettant au joueur de choisir les actions a faire pendant son tour.
     */
	public void actions() {
		this.afficherPlateau(plateau.getPlateau());
		afficherRessources(this.Joueurs[tour]);
		ArrayList<Cases> routesPoss = this.plateau.getRoutesPossPour(this.Joueurs[tour]);
		ArrayList<Cases> colPoss = this.plateau.getColPossPour(this.Joueurs[tour]);
		ArrayList<Cases> colUpgradePoss = this.plateau.getColUpgradable(this.Joueurs[tour]);
		System.out.println(this.Joueurs[this.tour].getNom()+" ,voici vos actions :");
		Boolean possRoute;
        Boolean possCol;
        Boolean possUpgrade;
        Boolean possVoirCartes = !this.Joueurs[this.tour].getCartes().isEmpty();
        Boolean possEchange = possEchange(this.Joueurs[this.tour]);
        if(possRoute(routesPoss) && !routesPoss.isEmpty()) {
            possRoute = true;
        }
        else {
            possRoute = false;
        }
        if(possCol(colPoss) && !colPoss.isEmpty()) {
            possCol = true;
        }
        else {
            possCol = false;
        }
        if(possUpgrade(colUpgradePoss) && !colUpgradePoss.isEmpty()) {
            possUpgrade = true;
        }
        else {
            possUpgrade = false;
        }
		Boolean possCarteDev = possCarteDev();
		choixPossible(possRoute,possCol,possUpgrade,possCarteDev,possVoirCartes,possEchange);
		choix(routesPoss,colPoss,colUpgradePoss,possRoute,possCol,possUpgrade,possCarteDev,possVoirCartes,possEchange);
	}
	
	/**
	 * Verifie si le joueur de ce tour a assez de ressource et si il a des routes construisable et renvoie true en les affichant si il a assez de ressources et peut construire , et false sinon.
	 * @param routesPoss : ArrayList<Cases> contenant toutes les routes construisables par le joueur de ce tour.
	 * @return true si le joueur a assez de ressources et qu'il peut construire une route , false sinon.
     */
	public boolean possRoute(ArrayList<Cases> routesPoss) {
		if(this.Joueurs[tour].getNbArgile()>=1 && this.Joueurs[tour].getNbBois()>=1) {
			int compteur = 0;
			System.out.println("\nRoutes construisable :");
			for(Cases c: routesPoss) {
				System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
				compteur++;
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Verifie si le joueur de ce tour a assez de ressource et si il a des colonies construisable et renvoie true en les affichant si il a assez de ressources et peut construire , et false sinon.
	 * @param colPoss : ArrayList<Cases> contenant toutes les colonies construisables par le joueur de ce tour.
	 * @return true si le joueur a assez de ressources et qu'il peut construire une colonie , false sinon.
     */
	public boolean possCol(ArrayList<Cases> colPoss) {
		if(this.Joueurs[tour].getNbArgile()>=1 && this.Joueurs[tour].getNbBois()>=1 && this.Joueurs[tour].getNbLaine()>=1 && this.Joueurs[tour].getNbBlé()>=1 && !colPoss.isEmpty()) {
			int compteur = 0;
			System.out.println("\nColonie construisable :");
			for(Cases c: colPoss) {
				System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
				compteur++;
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Verifie si le joueur de ce tour a assez de ressource et si il a des colonies améliorables et renvoie true en les affichant si il a assez de ressources et peut l'améliorer , et false sinon.
	 * @param colPoss : ArrayList<Cases> contenant toutes les colonies améliorables en villes par le joueur de ce tour.
	 * @return true si le joueur a assez de ressources et qu'il peut améliorer une colonie , false sinon.
     */
	public boolean possUpgrade(ArrayList<Cases> colUpgradePoss) {
		if(this.Joueurs[tour].getNbBlé()>=2 && this.Joueurs[tour].getNbMinerai()>=3) {
			int compteur = 0;
			System.out.println("\nColonie améliorable :");
			for(Cases c: colUpgradePoss) {
				System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
				compteur++;
			}
			return true;
		}	
		else {
			return false;
		}
	}

	/**
	 * Verifie si le joueur a assez de ressources pour acheter une carte developpement.
	 * @return true si le joueur a assez de ressources pour acheter une carte developpement et qu'il reste des cartes dans la pile , false sinon.
     */
	public boolean possCarteDev() {
		if(this.Joueurs[tour].getNbBlé()>=1 && this.Joueurs[tour].getNbMinerai()>=1 && this.Joueurs[tour].getNbLaine()>=1 && !this.pileCarte.isEmpty()) {
			System.out.println("\nCarte développement achetable");
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Verifie si le joueur a assez de ressources pour faire un échange par rapport a ses reductions de prix avec des ports.
	 * @param j = joueur qui veut faire les echanges.
	 * @return true si le joueur a assez de ressources pour faire un échange , false sinon.
     */
	public boolean possEchange(Joueur j) {
		if(!j.getPort().getNivPort() && j.getTotalRessources()>3 ) {
			return true;
		}
		else if(j.getPort().getNivPort() && j.getTotalRessources()>2) {
			return true;
		}
		else if(j.getNbBois()>1 && j.getPort().getPortBois()) {
			return true;
		}
		else if(j.getNbLaine()>1 && j.getPort().getPortLaine()) {
			return true;
		}
		else if(j.getNbBlé()>1 && j.getPort().getPortBlé()) {
			return true;
		}
		else if(j.getNbArgile()>1 && j.getPort().getPortArgile()) {
			return true;
		}
		else if(j.getNbMinerai()>1 && j.getPort().getPortMinerai()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Affiche tout les choix que le joueur peut faire ce tour.
	 * @param possRoute : boolean correspondant a si le joueur peut construire une route.
	 * @param possCol : boolean correspondant a si le joueur peut construire une colonie.
	 * @param possUpgrade : boolean correspondant a si le joueur peut améliorer une colonie.
	 * @param possCarteDev : boolean correspondant a si le joueur peut acheter une carte developpement.
	 * @param possVoirCartes : boolean correspondant a si le joueur peut voir et utiliser des cartes qu'il possede.
	 * @param possEchange : boolean correspondant a si le joueur peut echanger des ressources avec la banque.
     */
	public void choixPossible(boolean possRoute, boolean possCol, boolean possUpgrade, boolean possCarteDev, boolean possVoirCartes,boolean possEchange) {
		System.out.print("\nEcrivez votre choix entre : ");
		if(possRoute) {
			System.out.print("'route' ");
		}
		if(possCol) {
			System.out.print("'colonie' ");
		}
		if(possUpgrade) {
			System.out.print("'ville' ");
		}
		if(possCarteDev) {
			System.out.print("'carte' ");
		}
		if(possVoirCartes) {
			System.out.print("'main' ");
		}
		if(possEchange) {
			System.out.print("'echange' ");
		}
		System.out.println("'couts' 'info' 'fin'.");
	}

	/**
	 * Recupere le choix du joueur et appelle les fonctions nécessaire a effectuer ce choix.
	 * @param routesPoss : ArrayList<Cases> liste de cases où le joueur peut construire de nouvelles routes.
	 * @param colPoss : ArrayList<Cases> liste de cases où le joueur peut construire de nouvelles colonies.
	 * @param colUpgradePoss : ArrayList<Cases> liste de cases où le joueur peut améliorer une colonie.
	 * @param possRoute : boolean correspondant a si le joueur peut construire une route.
	 * @param possCol : boolean correspondant a si le joueur peut construire une colonie.
	 * @param possUpgrade : boolean correspondant a si le joueur peut améliorer une colonie.
	 * @param possCarteDev : boolean correspondant a si le joueur peut acheter une carte developpement.
	 * @param possVoirCartes : boolean correspondant a si le joueur peut voir et utiliser des cartes qu'il possede.
	 * @param possEchange : boolean correspondant a si le joueur peut echanger des ressources avec la banque.
     */
	public void choix(ArrayList<Cases> routesPoss,ArrayList<Cases> colPoss,ArrayList<Cases> colUpgradePoss,boolean possRoute, boolean possCol, boolean possUpgrade, boolean possCarteDev, boolean possVoirCartes,boolean possEchange) {
		ArrayList<String> choixPoss = new ArrayList<String>();
		String reponse = "";
		if(this.Joueurs[tour].getIA()) {
			if(possRoute) {
				choixPoss.add("route");
			}
			if(possCol) {
				choixPoss.add("colonie");
			}
			if(possUpgrade) {
				choixPoss.add("ville");
			}
			if(possCarteDev) {
				choixPoss.add("carte");
			}
			if(possVoirCartes) {
				choixPoss.add("main");
			}
			choixPoss.add("fin");
			Random r = new Random();
			int tmp = r.nextInt(choixPoss.size());
			reponse = choixPoss.get(tmp);
			System.out.println(reponse);
		}
		else {
			reponse = scanReponse.next();
		}
		if(reponse.equals("route") && possRoute) {
			choixRoute(routesPoss);
		}
		else if(reponse.equals("colonie") && possCol) {
			choixCol(colPoss);
		}
		else if(reponse.equals("ville") && possUpgrade) {
			choixVille(colUpgradePoss);
		}
		else if(reponse.equals("carte") && possCarteDev) {
			buyCarte(this.Joueurs[this.tour]);
		}
		else if(reponse.equals("fin")) {
			this.finTour=true;
		}
		else if(reponse.equals("couts")){
			afficherFicheConstruction();
		}
		else if(reponse.equals("echange") && possEchange) {
			echange();
		}
		else if(reponse.equals("info")) {
			afficherInfo();
		}
		else if(reponse.equals("main") && possVoirCartes) {
			voirCarte(this.Joueurs[this.tour].getCartes(),routesPoss);
		}
		else {			
			System.out.println("Erreur , veuillez réecrire votre choix :");
			choix(routesPoss,colPoss,colUpgradePoss,possRoute,possCol,possUpgrade,possCarteDev,possVoirCartes,possEchange);
		}
	}

	/**
	 * Recupere le choix du joueur et appelle les fonctions nécessaire a effectuer ce choix.
	 * @param n : ArrayList<Cases> liste de cases où le joueur peut construire de nouvelles routes.
	 * @param listChoix : ArrayList<Cases> qui contient les cases de changement possible.
	 * @return true si le joueur répond "oui" , false si le joueur répond "non".
     */
	public boolean validerChoix(int n,ArrayList<Cases> listChoix) {
		System.out.println("Validez vous votre choix suivant : case "+n+ ") {"+listChoix.get(n).getY()+";"+listChoix.get(n).getX()+"}");
		String rep = "";
		if(this.Joueurs[this.tour].getIA()) {
			rep = "oui";
			System.out.println("oui");
		}
		else{
			rep = scanReponse.next();
		}
		if(rep.equals("oui")) {
			return true;
		}
		if(rep.equals("non")) {
			return false;
		}
		else {
			System.out.println("Choix non accepté. Veuillez ecrire soit 'oui', soit 'non'.");
			return validerChoix(n,listChoix);
		}
	}

	/**
	 * Demande au joueur de ce tour quel route veut t'il faire puis construit cette route.
	 * @param routesPoss : ArrayList<Cases> liste de cases où le joueur peut construire de nouvelles routes.
     */
	public void choixRoute(ArrayList<Cases> routesPoss) {
		int compteur =0;
		System.out.println("Choisissez une des routes possible en écrivant le nombre associé , ou écrivez -1 pour retourner au choix d'actions.");
		System.out.println("Routes construisables :");
		for(Cases c: routesPoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(routesPoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <routesPoss.size()) {
			boolean valider = validerChoix(reponse,routesPoss);
			if(valider) {
				buyRoute(routesPoss.get(reponse),this.Joueurs[tour]);
			}
			else {
				choixRoute(routesPoss);
			}
		}
		if(reponse ==-1) {
			actions();
		}
		if(reponse <-1 || reponse > routesPoss.size()){
			System.out.println("Nombre non accepté.");
			choixRoute(routesPoss);
		}
	}

	/**
	 *	Enleve les ressources et construit la route appartenant au joueur j.
	 * @param c : case a construire et ajouter aux routes du joueur j.
	 * @param j : joueur qui a construit la route.
     */
	public void buyRoute(Cases c, Joueur j) {
		this.plateau.setRoute(c, j);
		j.decrArgile(1);
		j.decrBois(1);
		j.decrNbRoutes(1);
		j.getRoutes().add(c);
		System.out.println("Vous avez construit la route : {"+c.getY()+";"+c.getX()+"}");
	}

	/**
	 * Demande au joueur de ce tour quel colonie veut t'il faire puis construit cette colonie.
	 * @param colPoss : ArrayList<Cases> liste de cases où le joueur peut construire de nouvelles colonies.
     */
	public void choixCol(ArrayList<Cases> colPoss) {
		int compteur = 0;
		System.out.println("Choisissez une des colonies possible en écrivant le nombre associé , ou écrivez -1 pour retourner au choix d'actions.");
		System.out.println("Colonie construisables :");
		for(Cases c: colPoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(colPoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <colPoss.size()) {
			boolean valider = validerChoix(reponse,colPoss);
			if(valider) {
				buyCol(colPoss.get(reponse),this.Joueurs[tour]);
			}
			else {
				choixCol(colPoss);
			}
		}
		if(reponse ==-1) {
			actions();
		}
		if(reponse <-1 || reponse > colPoss.size()) {
			System.out.println("Nombre non accepté.");
			choixCol(colPoss);
		}
	}

	/**
	 * Enleve les ressources et construit la colonie appartenant au joueur j.
	 * @param c : case a construire et ajouter aux batiments du joueur j.
	 * @param j : joueur qui a construit le batiment.
     */
	public void buyCol(Cases c,Joueur j) {
		this.plateau.setCol(c, j);
		j.decrArgile(1);
		j.decrBois(1);
		j.decrBlé(1);
		j.decrLaine(1);
		j.decrNbColonies(1);
		j.getBats().add(c);
		j.incrScore(1);
		j.incrScoreAff(1);
		checkPort(c,j);
		System.out.println("Vous avez construit la colonie : {"+c.getY()+";"+c.getX()+"}");
	}

	/**
	 * Demande au joueur de ce tour quel colonie veut t'il améliorer puis l'ameliore en ville.
	 * @param colUpgradePoss : ArrayList<Cases> liste de cases où le joueur peut améliorer une colonie.
     */
	public void choixVille(ArrayList<Cases> colUpgradePoss) {
		int compteur = 0;
		System.out.println("Choisissez une des villes possible en écrivant le nombre associé , ou écrivez -1 pour retourner au choix d'actions.");
		System.out.println("Colonies améliorables :");
		for(Cases c: colUpgradePoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(colUpgradePoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <colUpgradePoss.size()) {
			boolean valider = validerChoix(reponse,colUpgradePoss);
			if(valider) {
				buyVille(colUpgradePoss.get(reponse),this.Joueurs[tour]);
			}
			else {
				choixVille(colUpgradePoss);
			}
		}
		if(reponse ==-1) {
			actions();
		}
		if(reponse <-1 || reponse > colUpgradePoss.size()) {
			System.out.println("Nombre non accepté.");
			choixVille(colUpgradePoss);
		}
	}

	/**
	 * Enleve les ressources et améliore la colonie appartenant au joueur j.
	 * @param c : case a améliorer.
	 * @param j : joueur qui a amélioré le batiment.
     */
	public void buyVille(Cases c,Joueur j) {
		this.plateau.setVille(c, j);
		j.decrBlé(2);
		j.decrMinerai(3);
		j.incrNbColonies(1);
		j.decrNbVilles(1);
		j.incrScore(1);
		j.incrScoreAff(1);
		System.out.println("Vous avez construit la ville : {"+c.getY()+";"+c.getX()+"}");
	}

	/**
	 * Enleve les ressources et ajoute une carte developpement au joueur j.
	 * @param j : joueur qui a construit le batiment.
     */
	public void buyCarte(Joueur j) {
		j.decrBlé(1);
		j.decrLaine(1);
		j.decrMinerai(1);
		Cartes tmp = this.pileCarte.piocher();
		j.incrCartes();
		if(tmp.getType().equals("Point")) {
			System.out.println("Vous avez pioché une carte Point !");
			j.incrScore(1);
		}
		else {
			j.getCartes().add(tmp);
		}
	}

	/**
	 * Affiche les cartes que le joueur possède.
	 * @param cartes : ArrayList<Cartes> contenant toutes les cartes que le joueur j possède.
	 * @param routesPoss : ArrayList<Cases> contenant toutes les routes construisables par le joueur de ce tour.
     */
	public void voirCarte(ArrayList<Cartes> cartes,ArrayList<Cases> routesPoss) {
		if(this.Joueurs[tour].getTotalCartes()>0) {
			System.out.println("\nCartes possédées: ");
			for(int i =0;i<this.Joueurs[this.tour].getScore()-this.Joueurs[this.tour].getScoreAff();i++) {
				System.out.print("[Point] ");
			}
			for(Cartes c:cartes) {
				if(c.getType().equals("Chevalier")) {
					System.out.print("["+c.getType()+"] ");
				}
				else {
					if(c.getEffet()==0) {
						System.out.print("["+c.getType()+" : construction de route] ");
					}
					if(c.getEffet()==1) {
						System.out.print("["+c.getType()+" : invention] ");
					}
					if(c.getEffet()==2) {
						System.out.print("["+c.getType()+" : monopole] ");
					}
				}
			}
			choixUseCarte(this.Joueurs[this.tour],cartes,routesPoss);
		}
	}
	
	/**
	 * Affiche les cartes que le joueur peut joueur et appelle les fonctions pour utiliser la carte si nécessaire. 
	 * @param j : joueur qui va utiliser ses cartes.
	 * @param cartes : ArrayList<Cartes> contenant toutes les cartes que le joueur j possède.
	 * @param routesPoss : ArrayList<Cases> contenant toutes les routes construisables par le joueur de ce tour.
     */
	public void choixUseCarte(Joueur j,ArrayList<Cartes> cartes, ArrayList<Cases> routesPoss) {
		int compteur = 0;
		System.out.println("\n"+this.Joueurs[this.tour].getNom()+" écrivez le numéro de la carte a jouer, ou -1 pour revenir au choix d'actions.");
		System.out.println("\nCartes jouables :");
		for(Cartes c:cartes) {
			if(c.getType().equals("Chevalier") || c.getType().equals("Progrès")) {
				if(c.getEffet()==-1) {
					System.out.println(compteur+") ["+c.getType()+"] ");
				}
				if(c.getEffet()==0 && !routesPoss.isEmpty() && routesPoss.size()>1) {
					System.out.println(compteur+") ["+c.getType()+" : construction de route]");
				}
				if(c.getEffet()==1) {
					System.out.println(compteur+") ["+c.getType()+" : invention]");
				}
				if(c.getEffet()==2) {
					System.out.println(compteur+") ["+c.getType()+" : monopole]");
				}
			}
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(cartes.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <cartes.size()) {
			boolean valider = validerChoixCarte(reponse,cartes);
			if(valider) {
				useCarte(reponse,cartes,this.Joueurs[tour],routesPoss);
			}
			else {
				choixUseCarte(j,cartes,routesPoss);
			}
		}
		if(reponse ==-1) {
			actions();
		}
		if(reponse <-1 || reponse > cartes.size()){
			System.out.println("Nombre non accepté.");
			choixUseCarte(j,cartes,routesPoss);
		}
	}
	
	/**
	 * Demande la validation du joueur pour utiliser la carte.
	 * @param n : choix de la carte.
	 * @param cartes : ArrayList<Cartes> contenant toutes les cartes que le joueur j possède.
	 * @return true si le joueur répond "oui" , false si le joueur répond "non".
     */
	public boolean validerChoixCarte(int n,ArrayList<Cartes> cartes) {
		if(cartes.get(n).getEffet()==-1) {
			System.out.println("Validez vous votre choix suivant : utiliser la carte "+n+") ["+cartes.get(n).getType()+"] ");
		}
		if(cartes.get(n).getEffet()==0) {
			System.out.println("Validez vous votre choix suivant : utiliser la carte "+n+") ["+cartes.get(n).getType()+" : construction de route]");
		}
		if(cartes.get(n).getEffet()==1) {
			System.out.println("Validez vous votre choix suivant : utiliser la carte "+n+") ["+cartes.get(n).getType()+" : invention]");
		}
		if(cartes.get(n).getEffet()==2) {
			System.out.println("Validez vous votre choix suivant : utiliser la carte "+n+") ["+cartes.get(n).getType()+" : monopole]");
		}
		String rep = ""; 
		if(this.Joueurs[this.tour].getIA()) {
			rep = "oui";
			System.out.println("oui");
		}
		else{
			rep = scanReponse.next();
		}
		if(rep.equals("oui")) {
			return true;
		}
		if(rep.equals("non")) {
			return false;
		}
		else {
			System.out.println("Choix non accepté. Veuillez ecrire soit 'oui', soit 'non'.");
			return validerChoixCarte(n,cartes);
		}
	}

	/**
	 * Utilise la carte choisie par le joueur et appelle les fonctions de la cartes.
	 * @param n : choix du joueur.
	 * @param cartes : ArrayList<Cartes> contenant toutes les cartes que le joueur j possède.
	 * @param j : joueur qui va utiliser les cartes.
	 * @param routesPoss : ArrayList<Cases> contenant toutes les routes construisables par le joueur de ce tour.
     */
	public void useCarte(int n,ArrayList<Cartes> cartes , Joueur j,ArrayList<Cases> routesPoss) {
		if(cartes.get(n).getEffet()==-1) {
			System.out.println(j.getNom()+" à  utilisé une carte Chevalier.");
			j.incrArmee();
			plusGrandeArmee();
			voleurPlacement();
		}
		if(cartes.get(n).getEffet()==0 && !routesPoss.isEmpty() && routesPoss.size()>1) {
			System.out.println(j.getNom()+" à  utilisé une carte Progrès : construction (2 routes).");
			for(int i = 0;i<2;i++) {
				System.out.println("Choix numéro "+ (i+1));
				choixRouteCarte(this.plateau.getRoutesPossPour(this.Joueurs[this.tour]));
			}
		}
		if(cartes.get(n).getEffet()==1) {
			System.out.println(j.getNom()+" à  utilisé une carte Progrès : invention (2 ressources au choix).");
			for(int i = 0;i<2;i++) {
				System.out.println("\nChoix numéro "+ (i+1));
				choixRessource(this.Joueurs[this.tour]);
			}
		}
		if(cartes.get(n).getEffet()==2) {
			System.out.println(j.getNom()+" à  utilisé une carte Progrès : monopole (d'une ressource).");
			monopole(this.Joueurs[this.tour]);
		}
		j.getCartes().remove(n);
	}
	
	/**
	 * Permet au joueur de creer une route gratuitement (appellé deux fois par utilisation de carte).
	 * @param routesPoss : ArrayList<Cases> contenant toutes les routes construisables par le joueur de ce tour.
     */
	public void choixRouteCarte(ArrayList<Cases> routesPoss) {
		this.afficherPlateau(plateau.getPlateau());
		int compteur =0;
		System.out.println("Choisissez une des routes possible en écrivant le nombre associé.");
		System.out.println("Routes construisables :");
		for(Cases c: routesPoss) {
			System.out.println(compteur +") {"+c.getY()+";"+c.getX()+"}");
			compteur++;
		}
		int reponse = 0;
		if(!this.Joueurs[this.tour].getIA()) {
			while(!scanReponse.hasNextInt()) {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
				scanReponse.next();
			}
			reponse = scanReponse.nextInt();
		}
		else {
			Random r = new Random();
			reponse = r.nextInt(routesPoss.size());
			System.out.println(reponse);
		}
		if(reponse >=0 && reponse <routesPoss.size()) {
			boolean valider = validerChoix(reponse,routesPoss);
			if(valider) {
				this.plateau.setRoute(this.plateau.getPlateau()[routesPoss.get(reponse).getY()][routesPoss.get(reponse).getX()],this.Joueurs[this.tour]);
				this.Joueurs[this.tour].decrNbRoutes(1);
				System.out.println("Vous avez construit la route : {"+routesPoss.get(reponse).getY()+";"+routesPoss.get(reponse).getX()+"}");
				}
			else {
				choixRouteCarte(routesPoss);
			}
		}
		if(reponse <0 || reponse > routesPoss.size()){
			System.out.println("Nombre non accepté.");
			choixRouteCarte(routesPoss);
		}
	}

	/**
	 * Permet au joueur de gagner une ressource gratuitement (appellé deux fois par utilisation de carte).
	 * @param j : joueur qui gagne les ressources.
     */
	public void choixRessource(Joueur j) {
		System.out.println(j.getNom()+" choisissez une ressource à  gagner: 'bois' 'laine' 'blé' 'argile' 'minerai'.");
		String rep ="";
		if(j.getIA()) {
			Random r = new Random();
			int tmp = r.nextInt(5);
			if(tmp == 0) {
				rep = "bois";
				System.out.println("bois");
			}
			else if(tmp == 1) {
				rep = "laine";
				System.out.println("laine");
			}
			else if(tmp == 2) {
				rep = "blé";
				System.out.println("blé");
			}
			else if(tmp == 3) {
				rep = "argile";
				System.out.println("argile");
			}
			else if(tmp == 4) {
				rep = "minerai";
				System.out.println("minerai");
			}
		}
		else {
			rep = scanReponse.next();
		}
		if(rep.equals("bois")) {
			System.out.println(j.getNom()+" à  obtenu 1 bois.");
			j.incrBois(1);
		}
		else if(rep.equals("laine")) {
			System.out.println(j.getNom()+" à  obtenu 1 laine.");
			j.incrLaine(1);
		}
		else if(rep.equals("blé")) {
			System.out.println(j.getNom()+" à  obtenu 1 blé.");
			j.incrBlé(1);
		}
		else if(rep.equals("argile")) {
			System.out.println(j.getNom()+" à  obtenu 1 argile.");
			j.incrArgile(1);
		}
		else if(rep.equals("minerai")) {
			System.out.println(j.getNom()+" à  obtenu 1 minerai.");
			j.incrMinerai(1);
		}
		else {
			System.out.println("Choix non accepté. Veuillez réessayer.");
			choixRessource(j);
		}	
	}

	/**
	 * Permet au joueur de recuperer toutes les ressources des autres joueurs du type de ressource choisi par le joueur.
	 * @param j : joueur qui gagne les ressources.
     */
	public void monopole(Joueur j) {
		System.out.println(j.getNom()+" choisissez un monopole: 'bois' 'laine' 'blé' 'argile' 'minerai'.");
		String rep ="";
		if(j.getIA()) {
			Random r = new Random();
			int tmp = r.nextInt(5);
			if(tmp == 0) {
				rep = "bois";
				System.out.println("bois");
			}
			else if(tmp == 1) {
				rep = "laine";
				System.out.println("laine");
			}
			else if(tmp == 2) {
				rep = "blé";
				System.out.println("blé");
			}
			else if(tmp == 3) {
				rep = "argile";
				System.out.println("argile");
			}
			else if(tmp == 4) {
				rep = "minerai";
				System.out.println("minerai");
			}
		}
		else {
			rep = scanReponse.next();
		}
		if(rep.equals("bois")) {
			int tmp =0;
			for(int i = 0;i<this.Joueurs.length;i++) {
				if(!this.Joueurs[i].equals(j)) {
					if(this.Joueurs[i].getNbBois()>0) {
						tmp=tmp+this.Joueurs[i].getNbBois();
						System.out.println(this.Joueurs[i].getNom()+" à  perdu "+this.Joueurs[i].getNbBois()+" bois.");
						this.Joueurs[i].setNbBois(0);
					}
				}
			}
			System.out.println(j.getNom()+" à  obtenu "+tmp+" bois.");
			j.incrBois(tmp);
		}
		else if(rep.equals("laine")) {
			int tmp =0;
			for(int i = 0;i<this.Joueurs.length;i++) {
				if(!this.Joueurs[i].equals(j)) {
					if(this.Joueurs[i].getNbLaine()>0) {
						tmp=tmp+this.Joueurs[i].getNbLaine();
						System.out.println(this.Joueurs[i].getNom()+" à  perdu "+this.Joueurs[i].getNbLaine()+" laines.");
						this.Joueurs[i].setNbLaine(0);
					}
				}
			}
			System.out.println(j.getNom()+" à  obtenu "+tmp+" laine.");
			j.incrLaine(tmp);
		}
		else if(rep.equals("blé")) {
			int tmp =0;
			for(int i = 0;i<this.Joueurs.length;i++) {
				if(!this.Joueurs[i].equals(j)) {
					if(this.Joueurs[i].getNbBlé()>0) {
						tmp=tmp+this.Joueurs[i].getNbBlé();
						System.out.println(this.Joueurs[i].getNom()+" à  perdu "+this.Joueurs[i].getNbBlé()+" blés.");
						this.Joueurs[i].setNbBlé(0);
					}
				}
			}
			System.out.println(j.getNom()+" à  obtenu "+tmp+" blés.");
			j.incrBlé(tmp);
		}
		else if(rep.equals("argile")) {
			int tmp =0;
			for(int i = 0;i<this.Joueurs.length;i++) {
				if(!this.Joueurs[i].equals(j)) {
					if(this.Joueurs[i].getNbArgile()>0) {
						tmp=tmp+this.Joueurs[i].getNbArgile();
						System.out.println(this.Joueurs[i].getNom()+" à  perdu "+this.Joueurs[i].getNbArgile()+" argiles.");
						this.Joueurs[i].setNbArgile(0);
					}
				}
			}
			System.out.println(j.getNom()+" à  obtenu "+tmp+" argiles.");
			j.incrArgile(tmp);
		}
		else if(rep.equals("minerai")) {
			int tmp =0;
			for(int i = 0;i<this.Joueurs.length;i++) {
				if(!this.Joueurs[i].equals(j)) {
					if(this.Joueurs[i].getNbMinerai()>0) {
						tmp=tmp+this.Joueurs[i].getNbMinerai();
						System.out.println(this.Joueurs[i].getNom()+" à  perdu "+this.Joueurs[i].getNbMinerai()+" minerais.");
						this.Joueurs[i].setNbMinerai(0);
					}
				}
			}
			System.out.println(j.getNom()+" à  obtenu "+tmp+" minerais.");
			j.incrMinerai(tmp);
		}
		else {
			System.out.println("Choix non accepté. Veuillez réessayer.");
			choixRessource(j);
		}
	}
	
		/**
	 * Lance le processus d'échange de ressources pour le Joueur du tour actuel 
	 * en fontion du nombre des ports qu'il possède
	 */
	public void echange() {
		if(!this.Joueurs[this.tour].getPort().getNivPort()) {
			int n = 4;
			int tmp =4;
			while(n>0) {
				echangeChoixPossible(this.Joueurs[this.tour],n,tmp);
				n=echangeChoix(this.Joueurs[this.tour],n,tmp);
			}
			if (n==0) {
				choixRessource(this.Joueurs[this.tour]);
			}
			else if(n==-1) {
				actions();
			}
		}
		else {
			int n =3;
			int tmp = 3;
			while(n>0) {
				echangeChoixPossible(this.Joueurs[this.tour],n,tmp);
				n =echangeChoix(this.Joueurs[this.tour],n,tmp);
			}
			if (n==0) {
				choixRessource(this.Joueurs[this.tour]);
			}
			else if(n==-1) {
				actions();
			}
		}
	}
	
	/**
	 * Affiche les ressources disponibles du Joueur j, puis lui demande de choisir les différentes
	 * resources que le Joueur j souhaite échanger 
	 * @param j : le Joueur du tour actuel
	 * @param n	: le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param tmp	: nombre de ressources que le Joueur doit initialement choisir pour effectuer l'échange
	 * pour savoir si le Joueur j a déjà  commencé l'échange ou non, par rapport à  n.
	 */
	public void echangeChoixPossible(Joueur j,int n,int tmp){
		afficherRessources(j);
		System.out.print(j.getNom()+" vous devez encore choisir "+n+" ressources pour faire un échange. \n");
		if(j.getPort().getPortBois() && j.getNbBois()>1) {
			System.out.print("Ou 2 bois. ");
		}
		if(j.getPort().getPortLaine() && j.getNbLaine()>1) {
			System.out.print("Ou 2 laines. ");
		}
		if(j.getPort().getPortBlé() && j.getNbBlé()>1) {
			System.out.print("Ou 2 blés. ");
		}
		if(j.getPort().getPortArgile() && j.getNbArgile()>1) {
			System.out.print("Ou 2 argiles. ");
		}
		if(j.getPort().getPortMinerai() && j.getNbMinerai()>1) {
			System.out.print("Ou 2 minerais.");
		}
		if(n==tmp) {
			System.out.println("Choisissez une ressource que vous voulez échanger, ou -1 pour retourner au choix d'actions:");
		}
		if(j.getNbBois()>0) {
			System.out.print("'bois' ");
		}
		if(j.getNbLaine()>0) {
			System.out.print("'laine' ");
		}
		if(j.getNbBlé()>0) {
			System.out.print("'blé' ");
		}
		if(j.getNbArgile()>0) {
			System.out.print("'argile' ");
		}
		if(j.getNbMinerai()>0) {
			System.out.print("'minerai'\n");
		}
	}
	
	/**
	 * Demande au joueur j de choisir un type de ressource à  échanger 
	 * et de l'écrire dans le terminal
	 * @param j : le Joueur du tour actuel
	 * @param n	: le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param tmp	: nombre de ressources que le Joueur doit initialement choisir pour effectuer l'échange
	 * pour savoir si le Joueur j a déjà  commencé l'échange ou non, par rapport à  n.
	 */
	public int echangeChoix(Joueur j,int n,int tmp) {
		String reponse = scanReponse.next();
		if(reponse.equals("bois") && j.getNbBois()>=0) {
			return echangerNombreBois(j,n,reponse);
		}
		else if(reponse.equals("laine") && j.getNbLaine()>=0) {
			return echangerNombreLaine(j,n,reponse);
		}
		else if(reponse.equals("blé") && j.getNbBlé()>=0) {
			return echangerNombreBlé(j,n,reponse);
		}
		else if(reponse.equals("argile") && j.getNbArgile()>=0) {
			return echangerNombreArgile(j,n,reponse);
		}
		else if(reponse.equals("minerai") && j.getNbMinerai()>=0) {
			return echangerNombreMinerai(j,n,reponse);
		}
		else if(reponse.equals("-1") && n==tmp) {
			return -1;
		}
		else {			
			System.out.println("Erreur , veuillez réecrire votre choix :");
			return echangeChoix(j,n,tmp);
		}
	}
	
	/**
	 * Demande au Joueur j combien de bois veut-il échanger 
	 * @param j : le Joueur
	 * @param n : le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param reponse : le type de ressource (afin de garder le type en mémoire 
	 * pour la méthode validerChoixEchange(Joueur j, int n, String reponse) )
	 * @return Le nombre de ressources restantes afin d'effectuer l'échange
	 */
	public int echangerNombreBois(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous échanger de bois, ou écrivez -1 pour retourner au choix des ressources.");
		if(j.getPort().getPortBois() && j.getNbBois()>1) {
			System.out.println("Vous pouvez échanger 2 de bois en une fois pour obtenir une ressource.");
		}
		while(!scanReponse.hasNextInt()) {
			System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
			scanReponse.next();
		}
		int tmp = scanReponse.nextInt();
		if(tmp>=0 && tmp<=j.getNbBois() && tmp<=2 && j.getPort().getPortBois()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrBois(tmp);
				if(j.getPort().getPortBois() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre inférieur a 2:");
				return echangerNombreBois(j,n,reponse);
			}
		}
		if(tmp>=0 && tmp<=j.getNbBois() && tmp<=n && !j.getPort().getPortBois()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrBois(tmp);
				if(j.getPort().getPortBois() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				return echangerNombreBois(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbBois()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return echangerNombreBois(j,n,reponse);
		}
		else {
			return n;
		}
	}
	
	/**
	 * Demande au Joueur j combien de laine veut-il échanger 
	 * @param j : le Joueur
	 * @param n : le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param reponse : le type de ressource (afin de garder le type en mémoire 
	 * pour la méthode validerChoixEchange(Joueur j, int n, String reponse) )
	 * @return Le nombre de ressources restantes afin d'effectuer l'échange
	 */
	public int echangerNombreLaine(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous échanger de laine, ou écrivez -1 pour retourner au choix des ressources.");
		if(j.getPort().getPortBois() &&j.getNbLaine()>1) {
			System.out.println("Vous pouvez échanger 2 de laines en une fois pour obtenir une ressource.");
		}
		while(!scanReponse.hasNextInt()) {
			System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
			scanReponse.next();
		}
		int tmp = scanReponse.nextInt();
		if(tmp>=0 && tmp<=j.getNbLaine() && tmp<=2 && j.getPort().getPortLaine()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrLaine(tmp);
				if(j.getPort().getPortLaine() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre inférieur a 2:");
				return echangerNombreLaine(j,n,reponse);
			}
		}
		if(tmp>=0 && tmp<=j.getNbLaine() && tmp<=n && !j.getPort().getPortLaine()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrLaine(tmp);
				if(j.getPort().getPortLaine() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				return echangerNombreLaine(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbLaine()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return echangerNombreLaine(j,n,reponse);
		}
		else {
			return n;
		}
	}

		/**
	 * Demande au Joueur j combien de blés veut-il échanger 
	 * @param j : le Joueur
	 * @param n : le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param reponse : le type de ressource (afin de garder le type en mémoire 
	 * pour la méthode validerChoixEchange(Joueur j, int n, String reponse) )
	 * @return Le nombre de ressources restantes afin d'effectuer l'échange
	 */
	public int echangerNombreBlé(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous échanger de blé, ou écrivez -1 pour retourner au choix des ressources.");
		if(j.getPort().getPortBois() &&j.getNbLaine()>1) {
			System.out.println("Vous pouvez échanger 2 de blés en une fois pour obtenir une ressource.");
		}
		while(!scanReponse.hasNextInt()) {
			System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
			scanReponse.next();
		}
		int tmp = scanReponse.nextInt();
		if(tmp>=0 && tmp<=j.getNbBlé() && tmp<=2 && j.getPort().getPortBlé()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrBlé(tmp);
				if(j.getPort().getPortBlé() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre inférieur a 2:");
				return echangerNombreBlé(j,n,reponse);
			}
		}
		if(tmp>=0 && tmp<=j.getNbBlé() && tmp<=n && !j.getPort().getPortBlé()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrBlé(tmp);
				if(j.getPort().getPortBlé() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				return echangerNombreBlé(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbBlé()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return echangerNombreBlé(j,n,reponse);
		}
		else {
			return n;
		}
	}
	
	/**
	 * Demande au Joueur j combien d'argiles veut-il échanger 
	 * @param j : le Joueur
	 * @param n : le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param reponse : le type de ressource (afin de garder le type en mémoire 
	 * pour la méthode validerChoixEchange(Joueur j, int n, String reponse) )
	 * @return Le nombre de ressources restantes afin d'effectuer l'échange
	 */
	public int echangerNombreArgile(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous échanger de argile, ou écrivez -1 pour retourner au choix des ressources.");
		if(j.getPort().getPortBois() &&j.getNbLaine()>1) {
			System.out.println("Vous pouvez échanger 2 de argiles en une fois pour obtenir une ressource.");
		}
		while(!scanReponse.hasNextInt()) {
			System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
			scanReponse.next();
		}
		int tmp = scanReponse.nextInt();
		if(tmp>=0 && tmp<=j.getNbArgile() && tmp<=2 && j.getPort().getPortArgile()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrArgile(tmp);
				if(j.getPort().getPortArgile() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre inférieur a 2:");
				return echangerNombreArgile(j,n,reponse);
			}
		}
		if(tmp>=0 && tmp<=j.getNbArgile() && tmp<=n && !j.getPort().getPortArgile()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrArgile(tmp);
				if(j.getPort().getPortArgile() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				return echangerNombreArgile(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbBois()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return echangerNombreArgile(j,n,reponse);
		}
		else {
			return n;
		}
	}

	/**
	 * Demande au Joueur j combien de minerais veut-il échanger 
	 * @param j : le Joueur
	 * @param n : le nombre ressources que le Joueur doit encore choisir pour effectuer l'échange
	 * @param reponse : le type de ressource (afin de garder le type en mémoire 
	 * pour la méthode validerChoixEchange(Joueur j, int n, String reponse) )
	 * @return Le nombre de ressources restantes afin d'effectuer l'échange
	 */
	public int echangerNombreMinerai(Joueur j,int n,String reponse) {
		System.out.println("Combien voulez vous échanger de minerai, ou écrivez -1 pour retourner au choix des ressources.");
		if(j.getPort().getPortBois() &&j.getNbMinerai()>1) {
			System.out.println("Vous pouvez échanger 2 de minerais en une fois pour obtenir une ressource.");
		}
		while(!scanReponse.hasNextInt()) {
			System.out.println("Reponse non accepté. Veuillez écrire un nombre:");
			scanReponse.next();
		}
		int tmp = scanReponse.nextInt();
		if(tmp>=0 && tmp<=j.getNbMinerai() && tmp<=2 && j.getPort().getPortMinerai()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrMinerai(tmp);
				if(j.getPort().getPortMinerai() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				System.out.println("Reponse non accepté. Veuillez écrire un nombre inférieur a 2:");
				return echangerNombreMinerai(j,n,reponse);
			}
		}
		if(tmp>=0 && tmp<=j.getNbMinerai() && tmp<=n && !j.getPort().getPortMinerai()) {
			boolean valider = validerChoixEchange(j,tmp,reponse);
			if(valider) {
				j.decrMinerai(tmp);
				if(j.getPort().getPortMinerai() && tmp==2) {
					return 0;
				}
				else {
					return n-tmp;
				}
			}
			else {
				return echangerNombreMinerai(j,n,reponse);
			}
		}
		if(tmp ==-1) {
			return n;
		}
		if(tmp <-1 || tmp > j.getNbMinerai()|| tmp>n) {
			System.out.println("Nombre non accepté.");
			return echangerNombreMinerai(j,n,reponse);
		}
		else {
			return n;
		}
	}

	/**
	 * Demande au Joueur j de valider un échange
	 * @param j : Joueur
	 * @param n : le nombre ressources que le Joueur doit encore choisir pour effectuer un échange
	 * @param reponse : le type de ressource que le Joueur souhaite échanger 
	 * @return true si le Joueur j accepte son choix d'échange, sinon false
	 */
	public boolean validerChoixEchange(Joueur j,int n,String reponse) {
		System.out.println("Validez vous votre choix suivant : échanger "+n+" "+reponse+" ?");
		String rep = scanReponse.next();
		if(rep.equals("oui")) {
			return true;
		}
		if(rep.equals("non")) {
			return false;
		}
		else {
			System.out.println("Choix non accepté. Veuillez ecrire soit 'oui', soit 'non'.");
			return validerChoixDéfausse(j,n,reponse);
		}
	}
	
	/**
	 * Méthode attribuant un port au Joueur j si celui-ci construit une colonnie
	 * à  proximité
	 * @param c : la Case contenant une colonnie
	 * @param j : le Joueur
	 */
	public void checkPort(Cases c,Joueur j) {
		if(((c.getY()==0 || c.getY()==1) && c.getX()==0) || (c.getY()==0 && (c.getX()==6 || c.getX()==8)) || ((c.getY()==6 || c.getY()==8) && c.getX()==8)) {
			j.getPort().setNivPort(true);
		}
		if((c.getY()==2 || c.getY()==4) && c.getX()==8) {
			j.getPort().setPortBois(true);
		}
		if(c.getY()==0 && (c.getX()==2 || c.getX()==4)) {
			j.getPort().setPortLaine(true);
		}
		if((c.getY()==4 || c.getY()==6) && c.getX()==0) {
			j.getPort().setPortBlé(true);
		}
		if(c.getY()==8 && (c.getX()==4 || c.getX()==6)) {
			j.getPort().setPortArgile(true);
		}
		if(c.getY()==8 && (c.getX()==0 || c.getX()==2)) {
			j.getPort().setPortMinerai(true);
		}
	}

	/**
	 * Méthode mettant à  jour le Joueur ayant la plus grande armée
	 */
	public void plusGrandeArmee() {
		if(this.Joueurs[this.tour].getArmee()==false) {
			int tmp =0;
			for(Joueur j : this.Joueurs) {
				if(j.getArmee()==true) {
					if(j.getNbArmee()<this.Joueurs[this.tour].getNbArmee()) {
						j.setArmee(false);
						j.decrScore(2);
						j.decrScoreAff(2);
						System.out.println(j.getNom()+ " à  perdu la plus grande armée");
						this.Joueurs[this.tour].setArmee(true);
						this.Joueurs[this.tour].incrScore(2);
						this.Joueurs[this.tour].incrScoreAff(2);
						System.out.println(this.Joueurs[this.tour].getNom() + " à  obtenu la plus grande armée");
					}
					tmp++;
				}
			}
			if(tmp == 0) {
				this.Joueurs[this.tour].setArmee(true);
				this.Joueurs[this.tour].incrScore(2);
				this.Joueurs[this.tour].incrScoreAff(2);
				System.out.println(this.Joueurs[this.tour].getNom() + " à  obtenu la plus grande armée");
			}
		}
	}
	
	/**
	 * Affiche le nombre des différentes ressources que possède le Joueur j
	 * @param j le Joueur 
	 */
	public void afficherRessources(Joueur j) {
		System.out.println("\nVous avez : "+j.getNbBois()+" bois ; "+j.getNbLaine()+" laines ; "+j.getNbBlé()+" blés ; "+j.getNbArgile()+" argiles ; "+j.getNbMinerai()+" minerais.\n");
	}

	public void afficherInfo() {
		System.out.println("-------------------------------------------");	
		System.out.println("Tour de "+this.Joueurs[this.tour].getNom());
		System.out.println("Infos sur le partie en cours : ");
		System.out.println("Tour numéro :"+this.nTour);
		int compteur =1;
		for(Joueur j:this.Joueurs) {
			if(j.getIA()) {
				System.out.println("\nCe joueur est un IA");
				System.out.println(j.getNom()+" à  "+j.getScoreAff()+" point de victoire.");
			}
			else{
				if(j.equals(this.Joueurs[this.tour])) {
					System.out.println("\nJoueur "+compteur+") "+j.getNom()+" à  "+j.getScore()+"point de victoire (PDV affiché aux autres : "+j.getScoreAff()+")");
				}
				else{
					System.out.println("\nJoueur "+compteur+" "+j.getNom()+" à  "+j.getScoreAff());
				}
			}
			System.out.println(j.getTotalRessources()+" ressources au total ,");
			System.out.println(j.getTotalCartes()+" cartes developpements ,");
			System.out.println(j.getTotalPieces()+" pieces restantes :");
			System.out.println(j.getNbRoutes()+" routes restantes , "+j.getNbColonies()+" colonies restantes et "+j.getNbVilles()+" villes restantes.");
			if(j.getArmee()) {
				System.out.println("A l'armée la plus grande avec "+j.getNbArmee()+" chevaliers.");
			}
			if(j.getPort().getNivPort()) {
				System.out.println("A un port.");
			}
			if(j.getPort().getPortBois()) {
				System.out.println("A un port de bois.");
			}
			if(j.getPort().getPortLaine()) {
				System.out.println("A un port de laine.");
			}
			if(j.getPort().getPortBlé()) {
				System.out.println("A un port de blé.");
			}
			if(j.getPort().getPortArgile()) {
				System.out.println("A un port de argile.");
			}
			if(j.getPort().getPortMinerai()) {
				System.out.println("A un port de minerai.");
			}
			System.out.println("");
		}
		System.out.println("-------------------------------------------");	
	}

	/**
	 * Méthode retournant true si un Joueur atteint un total de point de victoire >=10
	 * @return true si un Joueur atteint un total de point de victoire >=10, sinon false
	 */
	public boolean fini() {
		for(int i=0;i<this.Joueurs.length;i++) {
			if(this.Joueurs[i].getScore()>=10) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Affiche les stats actuelles des Joueurs de la partie
	 */
	public void afficherStats() {
		System.out.println("-------------------------------------------------------");
		System.out.println("La partie a durée : "+this.nTour+" tours.");
		for(Joueur j : this.Joueurs) {
			if(j.getScore()>=10) {
				if(j.getArmee()) {
					System.out.println("Victoire de "+j.getNom()+ " avec " + j.getScore() +" points et "+ (j.getScore()-j.getScoreAff()) + " cartes points de victoire et avait la plus grande armée.");
				}
				else {
					System.out.println("Victoire de "+j.getNom()+ " avec " + j.getScore() +" points et "+ (j.getScore()-j.getScoreAff()) + " cartes points de victoire.");
				}
			}
			else {
				if(j.getArmee()) {
					System.out.println(j.getNom()+ " à  " + j.getScore() +" points et "+ (j.getScore()-j.getScoreAff()) + " cartes points de victoire et avait la plus grande armée.");
				}
				else {
					System.out.println(j.getNom()+ " à  " + j.getScore() +" points et "+ (j.getScore()-j.getScoreAff()) + " cartes points de victoire.");
				}
			}
		}
		System.out.println("-------------------------------------------------------");
	}

	/**
	 * Retourne le Joueur d'après le nom
	 * @param nom : nom du Joueur
	 * @return Joueur associé au nom
	 */
	public Joueur getJoueurFromNom(String nom){
		int tmp=-1;
		for(int i=0;i<Joueurs.length;i++){
			if(Joueurs[i].getNom().equals(nom)){
				tmp=i;
			}
		}return Joueurs[tmp];
	}

	/**
	 * Affiche le plateau du jeu
	 * @param plateau : le plateau du Jeu
	 */
	public void afficherPlateau(Cases[][] plateau){
        int axeY=0;
        System.out.println("   0  1  "+GREEN_BOLD_BRIGHT+"2  3  4"+RESET+"  5"+RED_BOLD_BRIGHT+"  6  7  8"+RESET);
        int xVoleur=-1;
        int yVoleur=-1;
        for(int y = 0;y<plateau.length;y++) {
            if(axeY<3)
                System.out.print(RED_BOLD_BRIGHT+axeY+RESET+"  ");
            else if(axeY>3 && axeY<7)
                System.out.print(CYAN_BOLD_BRIGHT+axeY+RESET+"  ");
            else 
                System.out.print(axeY+"  ");
            for(int x = 0 ;x<plateau[y].length;x++) {
                if(plateau[x][y].getTypeCase()=="routes"){
                    if((plateau[x][y].getProprietaire())==""){
                        System.out.print("   ");
                    }else{
                        System.out.print(getJoueurFromNom(plateau[x][y].getProprietaire()).getCouleur()+"r"+RESET+"  ");
                    }
                }else if(plateau[x][y].getTypeCase()=="batiment"){
                    if((plateau[x][y].getProprietaire())==""){
                        System.out.print("   ");
                    }else{
                        if(plateau[x][y].getTypeBat()==1)
                            System.out.print(getJoueurFromNom(plateau[x][y].getProprietaire()).getCouleur()+"C"+RESET+"  ");
                        else
                        System.out.print(getJoueurFromNom(plateau[x][y].getProprietaire()).getCouleur()+"V"+RESET+"  ");
                    }
                }else{
                    if(plateau[x][y].getTypeBiome()=="Forêt")
                        if(plateau[x][y].getNum()>9)
                            System.out.print(BLUE_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+" ");
                        else System.out.print(BLUE_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+"  ");
                    else if(plateau[x][y].getTypeBiome()=="Pré")
                        if(plateau[x][y].getNum()>9)
                            System.out.print(GREEN_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+" ");
                        else System.out.print(GREEN_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+"  ");
                    else if(plateau[x][y].getTypeBiome()=="Colline")
                        if(plateau[x][y].getNum()>9)
                            System.out.print(YELLOW_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+" ");
                        else System.out.print(YELLOW_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+"  ");
                    else if(plateau[x][y].getTypeBiome()=="Montagne")
                        if(plateau[x][y].getNum()>9)
                            System.out.print(PURPLE_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+" ");
                        else System.out.print(PURPLE_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+"  ");
                    else if(plateau[x][y].getTypeBiome()=="Champs")
                        if(plateau[x][y].getNum()>9)
                            System.out.print(CYAN_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+" ");
                        else System.out.print(CYAN_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+"  ");
                    else if(plateau[x][y].getTypeBiome()=="desert")
                        System.out.print(RED_BOLD_BRIGHT+plateau[x][y].getNum()+RESET+"  ");
                    else System.out.print("B  ");
                }
            }
            if(axeY>5)
            System.out.print(RED_BOLD_BRIGHT+axeY+RESET);
        else if(axeY>1 && axeY<5)
            System.out.print(BLUE_BOLD_BRIGHT+axeY+RESET);
        else 
            System.out.print(axeY);
            axeY++;
            System.out.println();
        }System.out.println(YELLOW_BOLD_BRIGHT+"   0  1  2  "+RESET+"3"+PURPLE_BOLD_BRIGHT+"  4  5  6"+RESET+"  7  8");
        System.out.println();
        for(int y=1;y<plateau.length;y=y+2) {
            for(int x=1;x<plateau[y].length;x= x+2) {
                    if(plateau[y][x].getVoleur()) {
                        xVoleur=x;
                        yVoleur=y;
                        System.out.println("Coordoonnées du Voleur: ["+yVoleur+","+xVoleur+"]");
                    }
            }
        }
        System.out.println("Codes couleur: "+BLUE_BOLD_BRIGHT+"Forêt   "+GREEN_BOLD_BRIGHT+"Pré   "+YELLOW_BOLD_BRIGHT+"Colline   "+PURPLE_BOLD_BRIGHT+"Montagne   "+CYAN_BOLD_BRIGHT+"Champs   "+RED_BOLD_BRIGHT+"Desert"+RESET);
    }
	
	/**
	 * Affiche le coût des différentes constructions possibles du jeu
	 */
	public void afficherFicheConstruction(){
		System.out.println("\n----------------------------------------------------------------");
		System.out.println("Route: 1 de Bois + 1 d'Argile (+0 PV)");
		System.out.println("Colonie: 1 de Bois + 1 d'Argile + 1 de Blé + 1 de Laine (+1 PV)");
		System.out.println("Ville: 2 de Blé + 3 de Minerais (+2 PV)");
		System.out.println("Développement: 1 de Blé + 1 de Laine + 1 de Minerais (+? PV)");
		System.out.println("----------------------------------------------------------------\n");
		actions();
	}
	
	/**
	 * Lanceur du Jeu
	 */
	public static void main(String [] args) {
		Jeu test = new Jeu();
	}
	
}