package paquet;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
//import java.util.Random;

public class Routeur {
	//final static int sizeBuffer = 10000; inutile avec linked list
	public Queue<Message> buffer = new LinkedList<Message>();
	public List<Link> listeLienRouteur = new ArrayList<Link>(4);
	public int nbDeLien;
	public String nom;

	public Routeur(String nom, Link lien1, Link lien2, Link lien3) {
		this.listeLienRouteur.add(lien1);
		this.listeLienRouteur.add(lien2);
		this.listeLienRouteur.add(lien3);
		this.nbDeLien = 3;
		this.nom = nom;
	}
	public Routeur(String nom,Link lien1, Link lien2, Link lien3, Link lien4) {
		this.listeLienRouteur.add(lien1);
		this.listeLienRouteur.add(lien2);
		this.listeLienRouteur.add(lien3);
		this.listeLienRouteur.add(lien4);
		this.nbDeLien = 4;
		this.nom = nom;
	}

	public int maj(){
		return -1;
	}
	public void ajouter(Message messageAEnvoyer) {
		buffer.offer(messageAEnvoyer);
	}
}
