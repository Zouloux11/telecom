package paquet;

import java.util.Random;

public class RouteurCA extends Routeur {

	public RouteurCA(Link lien1, Link lien2, Link lien3) {
		super(lien1,lien2,lien3);	
	}
	public RouteurCA(Link lien1, Link lien2, Link lien3,Link lien4) {
		super(lien1,lien2,lien3,lien4);	
	}
	public void maj(){
		//On envoie le premier message de la liste dans le bon lien et on initialise le compteur à link.tpsTrajet
		//lors de lajout dans un lien, il faut diminuer de 1 la capacité résiduelle de ce meme lien
		// et changer les sources/destinations temporaire

		//il va falloir gérer lorsque le message arrive à sa destination finale on doit changer l'état à 3
		// et si le lien est plein : le message est bloqué => état = 2 
		Message premierMessage = buffer.poll();
		Random random = new Random();
		if (premierMessage.routDestFinale != this) {
			premierMessage.routSource = this;
			int lienAuHasard = 8;
			do {
				lienAuHasard = random.nextInt(this.nbDeLien);
			}while(listeLienRouteur.get(lienAuHasard).typeDeLien != 2);
			if (this == listeLienRouteur.get(lienAuHasard).sortie1)
				premierMessage.routDest = listeLienRouteur.get(lienAuHasard).sortie2;
			else {
				premierMessage.routDest = listeLienRouteur.get(lienAuHasard).sortie1;
			}
			//Si il y a de la place dans ce lien on le met
			if (listeLienRouteur.get(lienAuHasard).canal.size() < listeLienRouteur.get(lienAuHasard).capacite) {
				listeLienRouteur.get(lienAuHasard).canal.add(premierMessage);
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
		else {
			boolean messageTrouved = false;
			int i = 0;
			while (messageTrouved == false) {
				i ++;
				messageTrouved = listeLienRouteur.get(i).chercherMessageEtMAJSonEtat(3,premierMessage.ID);
			}
		}



	}

}

