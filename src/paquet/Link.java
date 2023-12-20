package paquet;

import java.util.List;
import java.util.ArrayList;
public class Link {
	public String nom;
	public int tpsTrajet;
	public int capacite = 100;
	public int capaciteResiduelle;
	public List<Message> canal = new ArrayList<Message>(capacite);
	public int typeDeLien; //1 = CA/CA ; 2 = CA/CTS; 3 = CTS/CTS
	public Routeur sortie1;
	public Routeur sortie2;

	public Link(String nom,int ca, int tps, int type) {
		this.capacite = ca;
		this.tpsTrajet = tps;
		this.capaciteResiduelle = ca;
		this.typeDeLien = type;
		this.nom = nom;
	}

	public void Associer(Routeur un, Routeur deux) {
		this.sortie1 = un;
		this.sortie2 = deux;
	}

	public void maj() {
	    // Parcourir la liste à partir de la fin
	    for (int i = canal.size() - 1; i >= 0; i--) {
	        Message message = canal.get(i);

	        if (message.compteur > 0) {
	            message.compteur--;
	        }

	        // Si le compteur du message est à 0
	        if (message.compteur == 0) {
	            switch (message.etat) {
	                case 0:
	                    // État 0 : Mettez le compteur à -1 et ajoutez le message au buffer du routeur destination
	                    message.compteur = -1;
	                    message.routDest.buffer.add(new Message(message.ID,message.routSourceFinale, message.routDestFinale, message.dureeAppel));
	                    break;

	                case 1:
	                    // État 1 : Supprimez le message
	                    System.out.println("MAYBE ?");
	                    canal.remove(i);
	                    break;

	                case 2:
	                    // État 2 : Supprimez le message et ajoutez-le au buffer du routeur destination
	                    canal.remove(i);
	                    message.routDest.buffer.add(new Message(message.ID,message.routSourceFinale, message.routDestFinale, message.dureeAppel));
	                    break;

	                case 3:
	                    // État 3 : Passez à l'état 1, ajoutez le message au buffer du routeur destination et mettre le compteur à la durée de l'appel
	                    message.etat = 1;
	                    message.routDest.buffer.add(new Message(message.ID,message.routSourceFinale, message.routDestFinale, message.dureeAppel));
	                    message.compteur = message.dureeAppel;
	                    break;
	            }
	        }
	    }
	}


	public boolean chercherMessageEtMAJSonEtat(int issue, int identifiant) {
		for (Message message : canal) {
			if (message.ID == identifiant) {
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