package paquet;

import java.util.Arrays;
import java.util.List;

public class Link {
	public int tpsTrajet;
	public int capacite = 100;
	public int capaciteResiduelle;
	public List<Message> canal = Arrays.asList(new Message[capacite]);
	
	public Link(int ca, int tps) {
		this.capacite = ca;
		this.tpsTrajet = tps;
		this.capaciteResiduelle = ca;
	}
	
	public void maj() {
		//Pour chaque message dans la liste :
		//si compteur > 0 : 
			//on enleve 1
		//si compteur = 0:
			//si etat = 0
				//on le met à -1 et on ajoute le message au buffeur du routeur destination associé
			//si etat = 1
				//on supprime le message
			//si etat = 2
				//on supprime et on ajoute le message au buffeur du routeur destination associé (dans ce cas là on détruit progressivement les réservation)
			//si etat = 3
				//on passe l'état à 1 et on ajoute le message au buffeur du routeur destination associé (dans ce cas on transmet l'acceptation du message)
				//et on met le compteur à dureeAppel. (comme ça il se self destruct en arrivant à 0)
	}

}