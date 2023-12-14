package paquet;

public class Main {

	public static void main(String[] args) {
		link1 = new Link();
		link2 = new Link();
		link3 = new Link();
		link4 = new Link();
		link5 = new Link();
		link6 = new Link();
		link7 = new Link();
		link8 = new Link();
		link9 = new Link();
		
		ListeLiens = [];
		
		CA1 = new Routeur(,[link1,link4,link5]);
		CA2 = new Routeur(,[link1,link2,link8,link6]);
		CA3 = new Routeur(,[link2,link7,link9]);
		CTS1 = new Routeur(,[link4,link6,link7,link3]);
		CTS2 = new Routeur(,[link3,link5,link8,link9]);
		
		ListeRouteur = [];
		
		while() {
			//génération nouveaux messages
			//maj links
			//maj routeurs
			//compte échecs/réussites
			
		}

	}

}