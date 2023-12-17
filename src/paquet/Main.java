package paquet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static List<Routeur> listeRouteur = new ArrayList<Routeur>(5);
	public static List<Link> listeLien = new ArrayList<Link>(9);
	public static int nbAppelsBloques = 0;
	public static List<Message> RemplirListeMessages() {
		// Création de la liste de messages
		List<Message> listeMessages = new ArrayList<Message>(100000);

		// Création d'un objet Random
		Random random = new Random();

		// Remplissage de la liste avec 100000 messages
		for (int i = 0; i < 1000000; i++) {
			// 1 = 0,1 sec, donc 3000 = 5 minutes et 600 = 1 minutes (équitablement répartie entre 1 et 5 minutes)
			int dureeAleatoire = random.nextInt(2400) + 601;
			int source = random.nextInt(3);
			int destination = random.nextInt(3);
			while (source == destination) {
				source = random.nextInt(3);
				destination = random.nextInt(3);
			}
			// Création d'un objet Message avec la durée aléatoire
			Message message = new Message(i+1,listeRouteur.get(source), listeRouteur.get(destination), dureeAleatoire);

			// Ajout du message à la liste
			listeMessages.add(message);
		}
		return listeMessages;
	}

	public static void main(String[] args) {
		Random random = new Random();

		Link linkCA1_CA2 = new Link("linkCA1_CA2",10,10,1);
		Link linkCA2_CA3 = new Link("linkCA2_CA3",10,10,1);
		Link linkCTS1_CTS2 = new Link("linkCTS1_CTS2",1000,10,3);
		Link linkCA1_CTS1 = new Link("linkCA1_CTS1",100,10,2);
		Link linkCA1_CTS2 = new Link("linkCA1_CTS2",100,10,2);
		Link linkCA2_CTS1 = new Link("linkCA2_CTS1",100,10,2);
		Link linkCA2_CTS2 = new Link("linkCA2_CTS2",100,10,2);
		Link linkCA3_CTS1 = new Link("linkCA3_CTS1",100,10,2);
		Link linkCA3_CTS2 = new Link("linkCA3_CTS2",100,10,2);

		listeLien.add(linkCA1_CA2);
		listeLien.add(linkCA1_CTS1);
		listeLien.add(linkCA1_CTS2);
		listeLien.add(linkCA2_CA3);
		listeLien.add(linkCA2_CTS1);
		listeLien.add(linkCA2_CTS2);
		listeLien.add(linkCA3_CTS1);
		listeLien.add(linkCA3_CTS2);
		listeLien.add(linkCTS1_CTS2);

		Routeur CA1 = new RouteurCA("CA1",linkCA1_CA2,linkCA1_CTS1,linkCA1_CTS2);
		Routeur CA2 = new RouteurCA("CA2",linkCA1_CA2,linkCA2_CA3,linkCA2_CTS1,linkCA2_CTS2);
		Routeur CA3 = new RouteurCA("CA3",linkCA2_CA3,linkCA3_CTS1,linkCA3_CTS2);
		Routeur CTS1 = new RouteurCTS("CTS1",linkCTS1_CTS2,linkCA1_CTS1,linkCA2_CTS1,linkCA3_CTS1);
		Routeur CTS2 = new RouteurCTS("CTS2",linkCTS1_CTS2,linkCA1_CTS2,linkCA2_CTS2,linkCA3_CTS2);

		listeRouteur.add(CA1);
		listeRouteur.add(CA3);
		listeRouteur.add(CA2);
		listeRouteur.add(CTS1);
		listeRouteur.add(CTS2);

		linkCA1_CA2.Associer(CA1, CA2);
		linkCA2_CA3.Associer(CA2, CA3);
		linkCTS1_CTS2.Associer(CTS1, CTS2);
		linkCA1_CTS1.Associer(CA1, CTS1);
		linkCA1_CTS2.Associer(CA1, CTS2);
		linkCA2_CTS1.Associer(CA2, CTS1);
		linkCA2_CTS2.Associer(CA2, CTS2);
		linkCA3_CTS1.Associer(CA3, CTS1);
		linkCA3_CTS2.Associer(CA3, CTS2);

		List<Message> listeMessages = new ArrayList<Message>(1000000);
		//initialisation de la liste de 100 000 messages
		listeMessages = RemplirListeMessages();

		int x = 25000000;
		int numeroMsg = 0;
		while(x > 0) {
			//System.out.println(x);
			x --;
			//Envoie des appels
			int envoie = random.nextInt(1); //1 chance sur 1 d'envoyer un appel
			if (envoie == 0 && numeroMsg < 1000000) {
				Message messageAEnvoyer = listeMessages.get(numeroMsg);
				numeroMsg ++; //On passe au msg suivant
				messageAEnvoyer.routSourceFinale.ajouter(messageAEnvoyer); //On ajoute le message à envoyer dans le buffer correspondant au bon CA
			}
			// Parcourir les liens
			for (Link lien : listeLien) {
				lien.maj();
			}

			// Parcourir les routeurs
			for (Routeur routeur : listeRouteur) {
				int jeSuisFrauduleux = routeur.maj();
				if (jeSuisFrauduleux < 0) {
					nbAppelsBloques ++;
				}
				if (jeSuisFrauduleux != 0) {
					supprimerOuAppeler(listeLien,jeSuisFrauduleux);
				}
			}
		}
		System.out.println("Voici le nombre d'appels bloqués : " + nbAppelsBloques);

	}

	public static void supprimerOuAppeler(List<Link> listeLien, int id) {
		for (Link lien : listeLien) {
			for (int i = lien.canal.size() - 1; i >= 0; i--) {
				Message message = lien.canal.get(i);
//				if (id < 0) {
//					System.out.println(-id +"   GNE   " + message.ID);
//				}
				if (message.ID == -id) {
					System.out.println("fdpBLOQUED==========================================");
					lien.canal.remove(i); // Supprimer le message avec l'ID spécifié
				}
				if (message.ID == id) {
					System.out.println("fdpAPPEL============================================");
					message.etat = 1;
					message.compteur = message.dureeAppel;
				}
			}
		}
	}



}