package SAESRABBLE;



public class Scrabble {
	

	private static int [] nbPointsJeton = {1,3,3,2,1,4,2,4,1,8,10,1,2,1,1,3,8,1,1,1,1,4,10,10,10,10};
	private Joueur[] joueurs;
	private int numJoueur; // joueur courant (entre 0 et joueurs.length-1)
	private Plateau plateau;
	private MEE sac;
	
	
	/*
	 * Constructuer d'un scrabble (plateau, sac et joueurs)
	 */
	public Scrabble (String[] noms) {
		this.numJoueur=Ut.randomMinMax(0, noms.length-1);
		this.plateau= new Plateau();
		int[] sac1 = { 9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1 };
		this.sac = new MEE(sac1);
		this.joueurs = new Joueur[noms.length];
		for (int i=0;i<noms.length;i++) {
			this.joueurs[i]=new Joueur (noms[i]);
		}
	}
	/*
	 * Méthode qui affiche le plateau courant et le joueur courant.
	 */
	public String toString() {
		return plateau.toString() 
        + "\nLa main est à "
        + this.joueurs[numJoueur].toString() + "\n"
		+ this.joueurs[numJoueur].toStringChevalet() + "\n";
	}
	
	/*
	 * Méthode qui joue une partie entière.
	 */
	public void partie() {
		
		for (int i=0; i<this.joueurs.length;i++) { // les joueurs piochent leurs 7 jetons.
			this.joueurs[i].prendJetons(sac, 7);
		}
		
		int winner=0;
		int passe=0;
		numJoueur = Ut.randomMinMax(0, joueurs.length-1); // Le joueur qui commence est tiré au sort.
		boolean finie1=false;
		boolean finie2=false;
		while(finie1==false && finie2==false) {
	
			Ut.afficher(this.toString());
			

			int val= joueurs[numJoueur].joue(plateau, sac, nbPointsJeton);
			if(val == -1) {
				passe++;
				if (passe==joueurs.length) {
					finie2 = true;
				}
				
			}
			if(val==1) {
				finie1 = true;
				passe=0;
				winner = this.numJoueur;
				int scoreFini = this.joueurs[numJoueur].getScore();
				Ut.afficher("Le joueur " + this.joueurs[numJoueur].getNomByScore(scoreFini) + " a terminé la partie ! \n");
			}
			if(val==0) {
				passe=0;
			}
			numJoueur++;
			if(numJoueur>joueurs.length-1) {
				numJoueur=0;
			}
		}
		for (int n=0; n<joueurs.length;n++) {
			if(finie1==true) {
				this.joueurs[winner].ajouteScore(this.joueurs[n].nbPointsChevalet(nbPointsJeton));
				
			}
			this.joueurs[n].ajouteScore(-joueurs[n].nbPointsChevalet(nbPointsJeton));
			
		int	score=(joueurs[n].getScore());
			Ut.afficher("Le joueur " + this.joueurs[n].getNomByScore(score) + " a un score de "+ score + "\n");
			
		}
		int maxScore=-9999999; //Si après la soustraction des points de leurs chevalets les scores des joueurs sont négatifs, on peut rentrer dans la boucle Si pour afficher le gagnant.
		for (int a=0;a<joueurs.length;a++) {
			if (maxScore<joueurs[a].getScore()) {
				maxScore=joueurs[a].getScore();
				
			}

		}
		for (int b=0;b<joueurs.length;b++) {
			
			Ut.afficher(joueurs[b].getNomByScore(maxScore) + " ");
		}
		Ut.afficher(" a gagné !");
		
	}

}
