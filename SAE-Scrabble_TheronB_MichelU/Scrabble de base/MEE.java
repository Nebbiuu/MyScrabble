package SAESRABBLE;



public class MEE {

	private int [] tabFreq; // tabFreq[i] est le nombre d'exemplaires
							// (fr�quence) de l'�l�ment i
	private int nbTotEx; // nombre total d'exemplaires
	
	/**
	* pr�-requis : max >= 0
	* action : cr�e un multi-ensemble vide dont les �l�ments seront
	*
	inf�rieurs à max
	*/
	public MEE (int max){
		this.tabFreq = new int[max];
		this.nbTotEx=0;
	}
	
	/**
	* pr�-requis : les �l�ments de tab sont positifs ou nuls
	* action : cr�e un multi-ensemble dont le tableau de fr�quences est
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
		* r�sultat : vrai ssi cet ensemble est vide
		*/
		public boolean estVide (){
			  boolean res = false;
		        if(this.nbTotEx==0){
		            res =true;
		        }
		        return res;
		}
		 
		
		/**
		* pr�-requis : 0 <= i < tabFreq.length
		* action : ajoute un exemplaire de i à this
		*/
		public void ajoute (int i) {
			this.tabFreq[i]++;
            this.nbTotEx++;
		}
		
		/**
		* pr�-requis : 0 <= i < tabFreq.length
		* action/r�sultat : retire un exemplaire de i de this s il en existe,
		*
		et retourne vrai ssi cette action a pu être effectu�e
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
		* pr�-requis : this est non vide            this.nbTotEx++;

		* action/r�sultat : retire de this un exemplaire choisi al�atoirement
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
		* pr�-requis : 0 <= i < tabFreq.length
		* action/r�sultat : transf�re un exemplaire de i de this vers e s'il
		*
		en existe, et retourne vrai ssi cette action a pu être effectu�e
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
		
		/** pr�-requis : k >= 0
		* action : tranf�re k exemplaires choisis al�atoirement de this vers e
		*
		dans la limite du contenu de this
		* r�sultat : le nombre d'exemplaires effectivement transf�r�s
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
		* pr�-requis : tabFreq.length <= v.length
		* r�sultat : retourne la somme des valeurs des exemplaires des
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
