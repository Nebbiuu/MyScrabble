package SAESRABBLE;



public class Joueur {

	private String nom;
	private MEE chevalet;
	private int score;
	private static char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	
	/*
	 * Constructeur de base
	 */
	public Joueur (String unNom) {
		this.nom=unNom;
		this.score=0;
		this.chevalet= new MEE (26);
	}
	
	/*
	 * Methode pour afficher :
	 */
	public String toString() {
		String res = this.nom + " \nscore : " + this.score + " \nchevalet :" + this.chevalet;
		return res;
	}
	/*
	 * ACtion : Affiche les lettres du chevalet du joueur courant. 
	 */
	public String toStringChevalet() {
		MEE chevalet = new MEE(this.chevalet);
		String res = "Lettres : ";
		for (int i=0; i<26; i++) {
			while (chevalet.retire(i)==true) {
				res = res + alphabet[i];
			}
		}
		return res;
	}
	
	/*
	 * accesseur en lecture de l'attribut score:
	 */
	public int getScore() {
		return this.score;
	}
	
	//Retourne le nom du joueur qui a le score en paramètre
	public String getNomByScore(int score) {
		if(this.score==score) {
			return this.nom;
		}
		return "";
	}
	//Ajoute l'entier en paramètre au score du joueur
	public void ajouteScore(int nb) {
		this.score+=nb;
	}
	
	/**
	* pré-requis : nbPointsJet indique le nombre de points rapportés par
	* chaque jeton/lettre
	* résultat : le nombre de points total sur le chevalet de ce joueur
	* suggestion : bien relire la classe MEE !
	*/
	public int nbPointsChevalet (int[] nbPointsJet){
		return chevalet.sommeValeurs(nbPointsJet);
	}
	
	/**
	* pré-requis : les éléments de s sont inférieurs é 26
	* action : simule la prise de nbJetons jetons par this dans le sac s,
	* dans la limite de son contenu.
	*/
	public void prendJetons (MEE s, int nbJetons) {
		s.transfereAleat2(this.chevalet, nbJetons);
	}	
	/**
	* pré-requis : les éléments de s sont infïérieurs à 26
	* et nbPointsJet.length >= 26
	* action : simule le coup de this : this choisit de passer son tour,
	* d'échanger des jetons ou de placer un mot
	* résultat : -1 si this a passé son tour, 1 si son chevalet est vide,
	* et 0 sinon
	*/
	public int joue(Plateau p, MEE s, int[] nbPointsJet) {
		
	int res = 0;
	Ut.afficher("Tapez 1 pour passer votre tour, tapez 2 pour échanger des jetons, tapez 3 pour jouer un mot");
	int n = Ut.saisirEntier();
	while (n!=1 && n !=2 && n!=3) {
		Ut.afficher("Tapez 1 pour passer votre tour, tapez 2 pour échanger des jetons, tapez 3 pour jouer un mot");
		n = Ut.saisirEntier();
	}
	
	if (n==1) {
		res =-1;
	}
	else if (n==2) {
		this.echangeJetons(s);
			res=0;
	}
	else if (n==3) {
			this.joueMot(p, s, nbPointsJet);
		res=0;
	}
	if (this.chevalet.estVide()==true) {
		res =1;
	}
	return res;
	}
	
	/** pré-requis : les éléments de s sont inférieurs é 26
	* et nbPointsJet.length >= 26
	* action : simule le placement déun mot de this (le mot, sa position
	* sur le plateau et sa direction, sont saisies au clavier)
	* résultat : vrai ssi ce coup est valide selon les règles du jeu.
	*/
	public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {
		boolean res = false;
		boolean valid=false;
		while (valid==false) {
		Ut.afficher("Saisir un mot en majuscule :");
		String mot = Ut.saisirChaine();
		Ut.afficher("Sasir la ligne :");
		int nbLig = Ut.saisirEntier();
		while (nbLig>14) {
			Ut.afficher("Saisir une ligne valide entre 0 et 14 :");
			nbLig = Ut.saisirEntier();
		}
		Ut.afficher("Saisir la colonne :");
		int nbCol= Ut.saisirEntier();
		while (nbCol>14) {
			Ut.afficher("Saisir une colonnevalide entre 0 et 14 :");
			nbCol = Ut.saisirEntier();
		}
		Ut.afficher("Saisir le sens (v pour vertical ou h pour horizontal:");
		char sens = Ut.saisirCaractere();
		while (sens !='v' && sens !='h') {
			Ut.afficher("Le sens peut Ãªtre seulement v ou h :");
			sens = Ut.saisirCaractere();
		}
		
		valid = p.placementValide(mot, nbLig, nbCol, sens, this.chevalet);
		
		if (valid==true) {
			res = true;
			joueMotAux(p, s, nbPointsJet, mot, nbLig, nbCol, sens);
		}
		else {
			Ut.afficher("Le mot n'est pas valide \n");
			
		}
		}
		return res;
	}
	
	/** pré-requis : cf. joueMot et le placement de mot à partir de la case
	* (numLig, numCol) dans le sens donné par sens est valide
	* action : simule le placement d'un mot de this
	*/
	public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot,
	int numLig, int numCol, char sens) {
		this.ajouteScore(p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));
		int jetRetire = p.place(mot, numLig, numCol, sens, this.chevalet);
		this.prendJetons(s, jetRetire);
		if (jetRetire==7) { //Si le joueure fait un scrabble
			this.ajouteScore(50); // On ajoute 50 points
		}
	}

	/**
	* pré-requis : les éléments de sac sont inférieurs à 26
	* action : simule l'échange de jetons de ce joueur
	*/
	public void echangeJetons(MEE sac) {

		Ut.afficher("Saisir les jetons à  échanger : \n");
		String chaine = Ut.saisirChaine();
		
		while (estCorrectPourEchange(chaine)==false) {
			Ut.afficher("Un ou plusieurs jetons ne sont pas valide ou pas en majuscule recommence : \n");
			chaine = Ut.saisirChaine();
		}
		echangeJetonsAux(sac,chaine);
		Ut.afficher("Vous avez effectué un échange\n" + this.toStringChevalet() + " est votre nouveau chevalet. \n");
	}
	/** résultat : vrai ssi les caractïères de mot correspondent tous à des
	* lettres majuscules et l'ensemble de ces caractères est un
	* sous-ensemble des jetons du chevalet de this
	*/
	public boolean estCorrectPourEchange (String mot) {
		boolean res = false;
		for (int i=0; i< mot.length(); i++) {
			if ( estMaj(mot.charAt(i))==true && contientMot(mot, this.chevalet)==true) {
					res=true;
			}
		}
		return res;
	}
	
	public boolean estMaj (char lettre) {
		boolean res = false;
		for (int i=0; i<alphabet.length; i++) {
			if (lettre == alphabet[i]) {
				res=true;
			}
		}
		return res;
	}
	
	
	/** pré-requis : sac peut contenir des entiers de 0 é 25 et ensJetons
	* est un ensemble de jetons correct pour lééchange
	* action : simule lééchange de jetons de ensJetons avec des
	* jetons du sac tirés aléatoirement.
	*/
	public void echangeJetonsAux(MEE sac, String ensJetons) {
		int jetonsSac=sac.nbJetonsMEE(sac);
		int compteurRetire=0;
		if (sac.nbJetonsMEE(sac)>= ensJetons.length()) {

			for (int i=0; i<ensJetons.length(); i++) {
				this.chevalet.retire(lettreTab(ensJetons.charAt(i)));
				compteurRetire++;
			}
			this.prendJetons(sac, compteurRetire);
			
			for (int j=0; j< ensJetons.length(); j++) {
				sac.ajoute(lettreTab(ensJetons.charAt(j)));
			}
		}
		else {
				for (int i=0; i<jetonsSac; i++) {
					this.chevalet.retire(lettreTab(ensJetons.charAt(i)));
					compteurRetire++;
				}
				this.prendJetons(sac, compteurRetire);
				
				for (int j=0; j<jetonsSac ; j++) {
					sac.ajoute(lettreTab(ensJetons.charAt(j)));
				}
		}	
	}
	
    // Action: renvoie un boolean indiquant si les lettres du "mot" placé en
    // parametre sont présentes sur le chevalet 'e'
	public boolean contientMot(String mot, MEE e) {
        boolean res = true;
        int i = 0;
        MEE copie = new MEE(e);
        int indice = 0;
        int[] indiceMot = motTab(mot);
        while (res == true && i < mot.length()) {
            indice = indiceMot[i];
            if (copie.retire(indice) == true) {
            } 
            else {
                res = false;
            }
            i++;
        }
        
        return res;
    }
	
	//Retorne un tableaux d'entiers correspondant aux indices des lettres du mot dans l'alphabet
	public int[] motTab(String mot) {
		int[] tabMotIndice;
		tabMotIndice= new int[mot.length()];
		char lettreactu= ' ';
		for(int i=0;i<mot.length();i++) {
			lettreactu=mot.charAt(i);
			int index=0;
			while(lettreactu!=alphabet[index]) {
				index++;
			}
		tabMotIndice[i]=index;
		}
		return tabMotIndice; 	
	}
	
	//Retourne l'indice de la lettre en paramètre dans l'lphabet (A=0, B=1, Z=25, etc.)
	public int lettreTab(char test) {
		int indiceLettre=0;
		while(test!=alphabet[indiceLettre]) {
			indiceLettre++;
		}
		return indiceLettre;
	}

}
