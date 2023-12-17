package paquet;


public class RouteurCTS extends Routeur {

	public RouteurCTS(Link lien1, Link lien2, Link lien3) {
		super(lien1,lien2,lien3);	
	}
	public RouteurCTS(Link lien1, Link lien2, Link lien3,Link lien4) {
		super(lien1,lien2,lien3,lien4);	
	}
	public void maj(){
		//On envoie le premier message de la liste dans le bon lien et on initialise le compteur à link.tpsTrajet
		//lors de lajout dans un lien, il faut diminuer de 1 la capacité résiduelle de ce meme lien
		// et changer les sources/destinations temporaire
		// et si le lien est plein : le message est bloqué => état = 2 
		Message premierMessage = buffer.poll();
		premierMessage.routSource = this;
		int j = 0;
		//On cherche le lien qui mène au bon CA final
		while(listeLienRouteur.get(j).sortie1 != premierMessage.routDestFinale && listeLienRouteur.get(j).sortie2 != premierMessage.routDestFinale) {
			j++;
		}
		premierMessage.routDest = premierMessage.routDestFinale;
		//Si il y a de la place dans ce lien on le met
		if (listeLienRouteur.get(j).canal.size() < listeLienRouteur.get(j).capacite) {
			listeLienRouteur.get(j).canal.add(premierMessage);
		}
		//Sinon il y a un échec et  on renvoie d'où ça vient en état 2
		else {
			boolean messageTrouved = false;
			int i = 0;
			while (messageTrouved == false) {
				i ++;
				messageTrouved = listeLienRouteur.get(i).chercherMessageEtMAJSonEtat(2,premierMessage.ID);
			}
		}
	}



}


