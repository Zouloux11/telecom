package paquet;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Link {
	public int tpsTrajet;
	public int capacite = 100;
	public int capaciteResiduelle;
	public List<Message> canal = new ArrayList<Message>(capacite);
	public int typeDeLien; //1 = CA/CA ; 2 = CA/CTS; 3 = CTS/CTS
	public Routeur sortie1;
	public Routeur sortie2;

	public Link(int ca, int tps, int type) {
		this.capacite = ca;
		this.tpsTrajet = tps;
		this.capaciteResiduelle = ca;
		this.typeDeLien = type;
	}

	public void Associer(Routeur un, Routeur deux) {
		this.sortie1 = un;
		this.sortie2 = deux;
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
		Iterator<Message> iterator = canal.iterator();
		while (iterator.hasNext()) {
			Message message = iterator.next();
			if (message.compteur > 0) {
				message.compteur --;;
			}

			// Si le compteur du message est à 0
			if (message.compteur == 0) {
				switch (message.etat) {
				case 0:
					// État 0 : Mettez le compteur à -1 et ajoutez le message au buffer du routeur destination
					message.compteur = -1;
					message.routDest.buffer.add(message);
					break;

				case 1:
					// État 1 : Supprimez le message
					iterator.remove();
					break;
				case 2:
					// État 2 : Supprimez le message et ajoutez-le au buffer du routeur destination
					iterator.remove();
					message.routDest.buffer.add(message);
					break;

				case 3:
					// État 3 : Passez à l'état 1, ajoutez le message au buffer du routeur destination et mettre le compteur à la durée de l'appel
					message.etat = 1;
					message.routDest.buffer.add(message);
					message.compteur = message.dureeAppel;
					break;

				}
			}
		}
	}

	public boolean chercherMessageEtMAJSonEtat(int issue, int identifiant) {
		for (Message message : canal) {
			if (message.ID == identifiant) {
				System.out.println("LEZTSO");
				//Une fois qu'on l'a trouvé on change son sens de parcours et on met son état à 3
				message.etat = issue;
				message.compteur = this.tpsTrajet;
				Routeur save = message.routDest;
				message.routDest = message.routSource;
				message.routSource = save;
				return true; 
			}
		}
		return false;
	}

}