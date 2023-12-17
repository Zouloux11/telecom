package paquet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main {
	public static List<Routeur> listeRouteur = new ArrayList<Routeur>(5);
	public static List<Link> listeLien = new ArrayList<Link>(9);
	public static int nbAppelsBloques;
	public static List<Message> RemplirListeMessages() {
		// Création de la liste de messages
		List<Message> listeMessages = new ArrayList<Message>(100000);

		// Création d'un objet Random
		Random random = new Random();

		// Remplissage de la liste avec 100000 messages
		for (int i = 0; i < 100000; i++) {
			// 1 = 0,1 sec, donc 3000 = 5 minutes et 600 = 1 minutes (équitablement répartie entre 1 et 5 minutes)
			int dureeAleatoire = random.nextInt(2400) + 601;
			int source = random.nextInt(4);
			int destination = random.nextInt(4);
			while (source == destination) {
				source = random.nextInt(4);
				destination = random.nextInt(4);
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

		Link linkCA1_CA2 = new Link(10,10,1);
		Link linkCA2_CA3 = new Link(10,10,1);
		Link linkCTS1_CTS2 = new Link(1000,10,3);
		Link linkCA1_CTS1 = new Link(100,10,2);
		Link linkCA1_CTS2 = new Link(100,10,2);
		Link linkCA2_CTS1 = new Link(100,10,2);
		Link linkCA2_CTS2 = new Link(100,10,2);
		Link linkCA3_CTS1 = new Link(100,10,2);
		Link linkCA3_CTS2 = new Link(100,10,2);

		listeLien.add(linkCA1_CA2);
		listeLien.add(linkCA1_CTS1);
		listeLien.add(linkCA1_CTS2);
		listeLien.add(linkCA2_CA3);
		listeLien.add(linkCA2_CTS1);
		listeLien.add(linkCA2_CTS2);
		listeLien.add(linkCA3_CTS1);
		listeLien.add(linkCA3_CTS2);
		listeLien.add(linkCTS1_CTS2);

		Routeur CA1 = new RouteurCA(linkCA1_CA2,linkCA1_CTS1,linkCA1_CTS2);
		Routeur CA2 = new RouteurCA(linkCA1_CA2,linkCA2_CA3,linkCA2_CTS1,linkCA2_CTS2);
		Routeur CA3 = new RouteurCA(linkCA2_CA3,linkCA3_CTS1,linkCA3_CTS2);
		Routeur CTS1 = new RouteurCTS(linkCTS1_CTS2,linkCA1_CTS1,linkCA2_CTS1,linkCA3_CTS1);
		Routeur CTS2 = new RouteurCTS(linkCTS1_CTS2,linkCA1_CTS2,linkCA2_CTS2,linkCA3_CTS2);

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

		List<Message> listeMessages = new ArrayList<Message>(100000);
		//initialisation de la liste de 100 000 messages
		listeMessages = RemplirListeMessages();

		int x = 1000000;
		int numeroMsg = 0;
		while(x > 0) {
			//System.out.println(x);
			x --;
			//Envoie des appels
			int envoie = random.nextInt(5); //1 chance sur 50 d'envoyer un appel
			if (envoie == 2 & numeroMsg < 100000) {
				System.out.println(numeroMsg);
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
				int jeSuisFrauduleux = routeur.maj(nbAppelsBloques);
					supprimerOuAppeler(listeLien,-jeSuisFrauduleux);
			}

			System.out.println("Voici le nombre d'appels bloqués : " + nbAppelsBloques);

		}

	}
	public static void supprimerOuAppeler(List<Link> listeLien,int id) {
		for (int i = 0; i<9; i++) {
			Iterator<Message> iterator = listeLien.get(i).canal.iterator();
			while (iterator.hasNext()) {
				Message message = iterator.next();
				if (message.ID == id) {
					iterator.remove();
				}
				if (message.ID == -id) {
					message.etat = 1;
					message.compteur = message.dureeAppel;
				}
			}
		}
	}


}