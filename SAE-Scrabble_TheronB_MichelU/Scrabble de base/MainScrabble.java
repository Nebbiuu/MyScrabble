package SAESRABBLE;



public class MainScrabble {
	public static void main (String[] args) {

		int nbjoueur = 0;
        while (nbjoueur < 1 || nbjoueur > 4) {
            System.out.println("Bonjour ! BIenvenue dans le scrabble de Boris Theron et Ugo Michel. Bon jeu ! \nEntrez le nombre de joueurs (de 1 à 4) : ");
            nbjoueur = Ut.saisirEntier();
        }
        String[] tabJoueur = new String[nbjoueur];
        for (int i = 0; i < tabJoueur.length; i++) {
            System.out.println("Quel est votre pseudo ? (Ils doivent être différent)");
            tabJoueur[i] = Ut.saisirChaine();
        }
        Scrabble partie1 = new Scrabble(tabJoueur);
        partie1.partie();
        
	}

}
