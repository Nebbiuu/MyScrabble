package SAESRABBLE;



public class Plateau {
	
	private Case [][] g; // g pour grille
	
	
	private static char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static int [][] plateau = 
				{{5,1,1,2,1,1,1,5,1,1,1,2,1,1,5},
				{1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
				{1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
				{2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
				{1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
				{1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
				{1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
				{5,1,1,2,1,1,1,4,1,1,1,2,1,1,5},
				{1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
				{1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
				{1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
				{2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
				{1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
				{1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
				{5,1,1,2,1,1,1,5,1,1,1,2,1,1,5}};
	
	//Constructeur 1 de plateau 
	public Plateau (Case[][] plateau) {
		this.g = plateau;
		}

	//Constructeur 2 de plateau 
	public Plateau() {
		g= new Case[15][15];
		for (int i=0; i<plateau.length;i++) {
			for(int j=0;j<plateau[i].length;j++) {
				this.g[i][j]= new Case (plateau[i][j]);
			}
		}
		
	}

	/*
	* rÃ©sultat : chaÃ®ne dÃ©crivant ce Plateau
	* Affichage du plataeu
	*/
	public String toString (){
		String grille = "    0 1 2 3 4 5 6 7 8 910 1112 1314 \n";
		Ut.sautLigne();
		for (int i=0; i<this.g.length; i++) {
			
				grille +=alphabet[i]+"  |";
					
			
			for (int j=0; j<this.g[i].length; j++) {
				
				if(g[i][j].estRecouverte()==true){
					
					grille+=g[i][j].getLettre() + "|";
				}
				else if(g[i][j].getCouleur()!=1) {
						grille+=g[i][j].getCouleur()+"|";
					
				}
				else {
					grille+="_|";
				}
			}
			grille+="\n";
		}
		return grille;
	}
	
	/**
	* pré-requis : mot est un mot accepté par CapeloDico,
	* 0 <= numLig <= 14, 0 <= numCol <= 14, sens est un élément
	* de {’h’,’v’} et l’entier maximum prévu pour e est au moins 25
	* résultat : retourne vrai ssi le placement de mot sur this à partir
	* de la case (numLig, numCol) dans le sens donné par sens à l’aide
	* des jetons de e est valide.
	*/
	public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e) {
		boolean res=false;
		
		if(this.plateauVide()==true && mot.length()>1) { //Si le plateau est vide et le mot a au moins 2 lettres
			if(passeAuMillieu(mot, numLig, numCol, sens)==true) {//Si la case du milieu est recouverte
				if (contientMot(mot, e)==true) {
					res=true;
				}
			}
		}
		if(this.plateauVide()==false && pasSuivieZone(numLig, numCol, sens, mot) ==true) {
			
			
			if(depassePas(mot, numLig, numCol, sens)==true) {
				
				if(zoneAuMoins1Recouverte(mot, numLig, numCol, sens)==true && zoneAuMoins1PasRecouverte(mot, numLig, numCol, sens)==true){
						
						if(motContientLettreR(mot, numLig, numCol, sens)==true) {
							
							if(contientZone(mot, numLig, numCol, sens, e)==true) {
								
							res = true;
						}
					}
				}
			}
		}
		
		return res;
	}
	
// Methodes pour placmentValide ci-dessous :
	
	/*
	 * retourne vrai si le mot placé passe par la case du milieu
	 */
public boolean passeAuMillieu(String mot, int numLig, int numCol, char sens) {
	boolean res = false;
	if (sens=='v') {
		for (int i=0; i<mot.length(); i++) {
			if (this.g[numLig+i][numCol]==this.g[7][7]) {
				res=true;
			}
			}
		}
	if (sens=='h') {
		for (int i=0; i<mot.length(); i++) {
			if(this.g[numLig][numCol+i]==this.g[7][7]) {
				res=true;
			}
		}
	}
	return res;
}
	/*
	 * Action : Pour chaque sens, on créé un nouveau mot qui contient les lettres du mot de bases sans 
	 * les lettres déjà placées sur le plateau. On vérifie que le chevalet contient bien 
	 * toutes les lettres de ce nouveau mot.
	 * 
	 */
	public boolean contientZone (String mot, int numLig, int numCol, char sens, MEE e){
		boolean res = false;
		String newmot = "";
		if (sens=='v') {
			for (int i=0; i<mot.length(); i++) {
				if (this.g[numLig +i][numCol].estRecouverte()==false) {
					newmot = newmot + mot.charAt(i);
					res = contientMot(newmot, e);
				}
			}	
		}	
		if (sens=='h') {
			for (int i=0; i<mot.length(); i++) {
				if (this.g[numLig][numCol+i].estRecouverte()==false) {
					newmot = newmot + mot.charAt(i);
					res = contientMot(newmot, e);
				}
			}	
		}
		return res;
	}
	/*
	 * retourne vraie si le chevalet contient les lettres qui vont être placées. 
	 */
	public boolean motContientLettreR (String mot, int numLig, int numCol, char sens) {
		boolean res = false;
		if (sens=='v') {
			for (int i=0; i<mot.length(); i++) {
				if (this.g[numLig +i][numCol].estRecouverte()==true) {
					if(this.g[numLig +i][numCol].getLettre()==mot.charAt(i)) {
						res = true;
					}
				}
			}
		}
		if (sens=='h') {
			for (int i=0; i<mot.length(); i++) {
				if (this.g[numLig][numCol+i].estRecouverte()==true) {
					if(this.g[numLig][numCol+i].getLettre()==mot.charAt(i)) {
						res = true;
					}
				}
			}
		}
		return res;
	}
	
	
	/*
	 * Retourne vraie si le mot qui va être placé reste bien dans les limites de la grille.
	 */
	public boolean depassePas(String mot, int numLig, int numCol, char sens) {
		boolean res=true;
			if (sens=='v') {
				for(int i=numLig; i<mot.length() + numLig; i++) {
					if(i>14) {
						res = false;
						
				}
					i++;
			}
		}
			if (sens=='h') {
				for (int i=numCol; i<mot.length() + numCol; i++) {
					if (i>14) {
						res = false;
						
				}
					i++;
			}
		}
			return res;
	}
	
	/*
	 * Retourne vraie si la zone du mot à placer est déjà recouverte au moins une fois par une lettre
	 */
	public boolean zoneAuMoins1Recouverte (String mot, int numLig, int numCol, char sens) {
		boolean res = false;
		int i=0;
		while (i<mot.length()) {
			if (sens=='v') {
						if (this.g[numLig+i][numCol].estRecouverte()==true) {
							res=true;
						}
						i++;
					}
			
			if (sens=='h') {
				
					if (this.g[numLig][numCol+i].estRecouverte()==true) {
						res=true;
					}
					i++;
				}
			}
		
		return res;
	}
	
	/*
	 * Retourne vraie si la zone contient au moins une case non recouverte par un jeton
	 */
	public boolean zoneAuMoins1PasRecouverte (String mot, int numLig, int numCol, char sens) {
		boolean res = false;
		int i=0;
		while (i<mot.length()) {
			if (sens=='v') {
				if (this.g[numLig+i][numCol].estRecouverte()==false) {
					res=true;
				}
				i++;
			}
			if (sens=='h') {
				if (this.g[numLig][numCol+i].estRecouverte()==false) {
					res=true;
				}
				i++;
			}
		}
		return res;
	}
	
	/*
	 * Renvoie vraie si la zone du mot qui va être placé n'est pas suivie ou précédée par d'autres lettres déjà placées ou du bord de la grille.
	 * Faux sinon.
	 */
	public boolean pasSuivieZone(int numLig, int numCol, char sens, String mot) {
		boolean res = false;
		if(sens == 'v') {
			if(numLig==0 || this.g[numLig-1][numCol].estRecouverte()==false ) {
				if(numLig + mot.length()-1>=14 || this.g[numLig + mot.length()][numCol].estRecouverte()==false) {
					
					res=true;	
				
			}
				 
				
			}
		}
		if(sens == 'h') {
			if(numCol==0 || this.g[numLig][numCol-1].estRecouverte()==false) {
				if(numCol + mot.length()-1>=14 || this.g[numLig][numCol+mot.length()].estRecouverte()==false) {
					
						res=true;
					}
				}
			}
	return res;
	}
		
	// Action: renvoie un boolean indiquant si les lettres du "mot" placÃ© en
    // parametre sont prÃ©sentes sur le chevalet 'e'
	public boolean contientMot(String mot, MEE e) {
        boolean res = true;
        int i = 0;
        MEE copie = new MEE(e);
        int indice = 0;
        int[] indiceMot = motTab(mot);
        while (res == true && i < mot.length()) {
            indice = indiceMot[i];
            if (copie.retire(indice) == true) {
            	res = true;
            } 
            else {
                res = false;
            }
            i++;
        }
        
        return res;
    }
	
	// Action : Retorne un tableaux d'entiers correspondant 
	//aux indices des lettres du mot dans l'alphabet	
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
	
	// Action : renvoie l'indice de la lettre en paramètre dans l'alphabet
	public int lettreTab(char test) {
		int indiceLettre=0;
		while(test!=alphabet[indiceLettre]) {
			indiceLettre++;
		}
		return indiceLettre;
	}
	
	//Action renvoie vrai si le plateau est vide
	public boolean plateauVide() {
		boolean res= true;
			for (int ligne=0; ligne < 15; ligne++) {
				for (int col=0; col< g[ligne].length; col++) {
					if(g[ligne][col].estRecouverte()==true) {
						res=false;
					}
				}
			}
		
		return res;
	}
	//Fin des methodes pour placementValide
	
	
	/**
	* prÃ©-requis : le placement de mot sur this Ã  partir de la case
	* (numLig, numCol) dans le sens donnÃ© par sens est valide
	* rÃ©sultat : retourne le nombre de points rapportÃ©s par ce placement, le
	* nombre de points de chaque jeton Ã©tant donnÃ© par le tableau nbPointsJet.
	*/
	public int nbPointsPlacement(String mot, int numLig, int numCol,char sens, int[] nbPointsJet) {
		int couleur;
		int motTriple=0;
		int motDouble=0;
		int scoreMot=0;
		if(sens =='v') {
			for (int i=0; i<mot.length();i++) {
				couleur=this.g[numLig+i][numCol].getCouleur();
				
				if(this.g[numLig+i][numCol].estRecouverte()==false) {
					
					if(couleur==1) {
						scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
					}
					if(couleur==2) {
						scoreMot += 2*nbPointsJet[lettreTab(mot.charAt(i))];
					}
					if(couleur==3) {
						scoreMot += 3*nbPointsJet[lettreTab(mot.charAt(i))];
					}
					if(couleur==4) {
						scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
						motDouble+=1;
					}
					if(couleur==5) {
						scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
						motTriple+=1;
					}
				}
				
				else if(this.g[numLig][numCol+i].estRecouverte()==true){
				
				if(couleur==1) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==2 && this.g[numLig+i][numCol].estRecouverte()==false) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				else if(couleur==3 && this.g[numLig+i][numCol].estRecouverte()==false) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==4 && this.g[numLig+i][numCol].estRecouverte()==false) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==5 && this.g[numLig+i][numCol].estRecouverte()==false) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				}
			}
			if(motDouble==1) {
				scoreMot=scoreMot*2;
			}
			if(motDouble==2) {
				scoreMot=scoreMot*4;
			}
			if (motTriple==1) {
				scoreMot=scoreMot*3;
			}
			if (motTriple==2) {
				scoreMot=scoreMot*9;
			}
		}
		else if(sens=='h'){ 
			for (int i=0; i<mot.length();i++) {
				couleur=this.g[numLig][numCol+i].getCouleur();
				
				if(this.g[numLig][numCol+i].estRecouverte()==false) {
					if(couleur==1) {
						scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
					}
					if(couleur==2) {
						scoreMot += 2*nbPointsJet[lettreTab(mot.charAt(i))];
					}
					if(couleur==3) {
						scoreMot += 3*nbPointsJet[lettreTab(mot.charAt(i))];
					}
					if(couleur==4) {
						scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
						motDouble+=1;
					}
					if(couleur==5) {
						scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
						motTriple+=1;
					}
					
				}
				else if(this.g[numLig][numCol+i].estRecouverte()==true) {
				
				if(couleur==1) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==2) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==3) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==4) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				if(couleur==5) {
					scoreMot += nbPointsJet[lettreTab(mot.charAt(i))];
				}
				}
			}
			if(motDouble==1) {
				scoreMot=scoreMot*2;
			}
			if(motDouble==2) {
				scoreMot=scoreMot*4;
			}
			if (motTriple==1) {
				scoreMot=scoreMot*3;
			}
			if (motTriple==2) {
				scoreMot=scoreMot*9;
			}
		}
		return scoreMot;
	}
	
	/**
	* pré-requis : le placement de mot sur this à partir de la case
	* (numLig, numCol) dans le sens donné par sens à l’aide des
	* jetons de e est valide.
	* action/résultat : effectue ce placement et retourne le
	* nombre de jetons retirés de e.
	*/
	public int place(String mot, int numLig, int numCol, char sens, MEE e){
		int compteurjet=0;
		if(sens =='v') {
			for (int i=0; i<mot.length();i++) {
			
				if(this.g[numLig+i][numCol].estRecouverte()==false) {
					this.g[numLig+i][numCol].setLettre(mot.charAt(i));
					
					compteurjet++;
					e.retire(lettreTab(mot.charAt(i)));
					
				}
			}
		}
		if(sens =='h') {
			for (int i=0; i<mot.length();i++) {
			
			
				if (this.g[numLig][numCol+i].estRecouverte()==false) {
					this.g[numLig][numCol+i].setLettre(mot.charAt(i));
					
					e.retire(lettreTab(mot.charAt(i)));
					compteurjet++;
				}
			}
		}
		return compteurjet;
	}
	

}







