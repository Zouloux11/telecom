package paquet;

public class RouteurCTS extends Routeur {

	public RouteurCTS(String nom,Link lien1, Link lien2, Link lien3) {
		super(nom,lien1,lien2,lien3);	
	}
	public RouteurCTS(String nom,Link lien1, Link lien2, Link lien3,Link lien4) {
		super(nom,lien1,lien2,lien3,lien4);	
	}
	public boolean maj(){
		//On envoie le premier message de la liste dans le bon lien et on initialise le compteur à link.tpsTrajet
		//lors de lajout dans un lien, il faut diminuer de 1 la capacité résiduelle de ce meme lien
		// et changer les sources/destinations temporaire
		// et si le lien est plein : le message est bloqué => état = 2 
		Message premierMessage = buffer.poll();
		if (premierMessage != null) {
			if (premierMessage.etat == 0) {
				premierMessage.routSource = this;
				int j = 0;
				//On cherche le lien qui mène au bon CA final
				while(listeLienRouteur.get(j).sortie1 != premierMessage.routDestFinale && listeLienRouteur.get(j).sortie2 != premierMessage.routDestFinale) {
					j++;
				}
				System.out.println("Link choisis : " + listeLienRouteur.get(j).nom + "|| dest finale : " + premierMessage.routDestFinale.nom +  "|| source finale : " + premierMessage.routSourceFinale.nom);
				premierMessage.routDest = premierMessage.routDestFinale;
				//Si il y a de la place dans ce lien on le met
				if (listeLienRouteur.get(j).canal.size() < listeLienRouteur.get(j).capacite) {
					premierMessage.compteur = listeLienRouteur.get(j).tpsTrajet;
					listeLienRouteur.get(j).canal.add(premierMessage);
					System.out.println("ça passe en CTS");
				}
				//Sinon il y a un échec et on renvoie d'où ça vient en état 2
				else {
					System.out.println("ça bloque en CTS");
					System.out.println(-premierMessage.ID);
					boolean messageTrouved = false;
					int i = 0;
					while (messageTrouved == false && i < nbDeLien) {
						System.out.println("i vaut : " + i);
						messageTrouved = listeLienRouteur.get(i).chercherMessageEtMAJSonEtat(2,premierMessage.ID);
						System.out.println("trouved vaut : " + messageTrouved);
						i ++;
					}
					return false;
				}
			}
			else {
				boolean messageTrouved = false;
				int i = 0;
				while (messageTrouved == false && i < nbDeLien) {
					System.out.println("i vaut : " + i);
					messageTrouved = listeLienRouteur.get(i).chercherMessageEtMAJSonEtat(premierMessage.etat,premierMessage.ID);
					System.out.println("trouved vaut : " + messageTrouved);
					i ++;
				}
			}
		}
		return true;
	}




}


