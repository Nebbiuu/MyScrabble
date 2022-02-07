package SAESRABBLE;



public class MEE {

	private int [] tabFreq; // tabFreq[i] est le nombre d’exemplaires
							// (fréquence) de l’élément i
	private int nbTotEx; // nombre total d’exemplaires
	
	/**
	* pré-requis : max >= 0
	* action : crée un multi-ensemble vide dont les éléments seront
	*
	inférieurs à max
	*/
	public MEE (int max){
		this.tabFreq = new int[max];
		this.nbTotEx=0;
	}
	
	/**
	* pré-requis : les éléments de tab sont positifs ou nuls
	* action : crée un multi-ensemble dont le tableau de fréquences est
	*.
	une copie de tab
	*/
	public MEE (int[] tab){
		
		this.tabFreq= new int [tab.length];
		for(int i=0; i<tab.length; i++) {
			this.tabFreq[i]=tab[i];
			this.nbTotEx += this.tabFreq[i];
			}
		}
		
		/**
		* constructeur par copie
		*/
	public MEE (MEE e){
        this.tabFreq = new int[e.tabFreq.length];
        this.nbTotEx = e.nbTotEx;
        for(int i=0;i<e.tabFreq.length;i++){
            this.tabFreq[i]=e.tabFreq[i];
        }
    }
		/**
		* résultat : vrai ssi cet ensemble est vide
		*/
		public boolean estVide (){
			  boolean res = false;
		        if(this.nbTotEx==0){
		            res =true;
		        }
		        return res;
		}
		 
		
		/**
		* pré-requis : 0 <= i < tabFreq.length
		* action : ajoute un exemplaire de i à this
		*/
		public void ajoute (int i) {
			this.tabFreq[i]++;
            this.nbTotEx++;
		}
		
		/**
		* pré-requis : 0 <= i < tabFreq.length
		* action/résultat : retire un exemplaire de i de this s il en existe,
		*
		et retourne vrai ssi cette action a pu être effectuée
		*/
		public boolean retire (int i) {
			boolean res = false;
			if (tabFreq[i]>0) {
					this.tabFreq[i]--;
					this.nbTotEx--;
			res = true;
			}
		        return res;
		}		
		/**
		* pré-requis : this est non vide            this.nbTotEx++;

		* action/résultat : retire de this un exemplaire choisi aléatoirement
		*
		et le retourne
		*/
		public int retireAleat () {
			int i = Ut.randomMinMax(0, this.tabFreq.length - 1);
			while (tabFreq[i]==0) {
				i = Ut.randomMinMax(0, this.tabFreq.length - 1);
			}
					this.tabFreq[i]--;
					this.nbTotEx--;
		        return i;
		}
		
		/**
		* pré-requis : 0 <= i < tabFreq.length
		* action/résultat : transfère un exemplaire de i de this vers e s’il
		*
		en existe, et retourne vrai ssi cette action a pu être effectuée
		*/
		public boolean transfere (MEE e, int i) {
			
			boolean res = false;
			if (tabFreq[i]!=0) {
			
				this.retire(i);
				e.ajoute(i);
				res = true;
			}
			return res;
		}
		
		/** pré-requis : k >= 0
		* action : tranfère k exemplaires choisis aléatoirement de this vers e
		*
		dans la limite du contenu de this
		* résultat : le nombre d’exemplaires effectivement transférés
		*/
		public int transfereAleat (MEE e, int k) {
			
			int n = Ut.randomMinMax(0, tabFreq[k]-1);
			
			for (int i=0; i < n; i++) {
				this.retire(k);
				e.ajoute(k);
				
			}
			return n;
		}
		
		public int transfereAleat2 (MEE e, int k) {
			int transfere=0;
			int aleat=Ut.randomMinMax(0, this.tabFreq.length -1);
			while(k>0 && this.estVide()==false) {
				aleat=Ut.randomMinMax(0, this.tabFreq.length -1);
				if(this.transfere(e, aleat)) {
					k--;
					transfere++;
				}
			}
			return transfere;
		}
		/**
		* pré-requis : tabFreq.length <= v.length
		* résultat : retourne la somme des valeurs des exemplaires des
		*
		�l�ments de this, la valeur d un exemplaire d un �l�ment i
		*
		de this �tant �gale � v[i]
		*/
		public int sommeValeurs (int[] v){
			int somme=0;
			for(int i=0; i<this.tabFreq.length;i++) {
				somme+=this.tabFreq[i]*v[i];
			}
			return somme;
			}
		
		
		public String toString(){

	        String res = "{";
	        for(int i=0;i<this.tabFreq.length;i++){
	            if(i==tabFreq.length-1){
	                res+=this.tabFreq[i] +"}";
	            }
	            else{
	                res+=this.tabFreq[i] +",";
	            }
	            
	        }
	        /*String res = Arrays.toString(this.ensTab);*/
	        return res;
	    } 
		
		/*
		 * Permet de savoir combiend e jetons il y a dans un chevalet oudans le sac.
		 */
		public int nbJetonsMEE (MEE e) {
			int somme=0;
			for (int i=0; i<26; i++) {
				somme += e.tabFreq[i];
			}
			return somme;
		}
		
}
