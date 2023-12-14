package paquet;

public class Message{
	public int ID;
	public int etat; //etat 0 = Demande de réservation; etat 1 = Appel en cours; etat 2 = appel bloqué; etat 3 = appel accepté
	public int compteur;
	public Routeur routDest;
	public Routeur routSource;
	public Routeur routDestFinale;
	public Routeur routSourceFinale;
	public int dureeAppel;
	
	public Message(int ID,Routeur source,Routeur dest,int duree) {
		this.ID = ID;
		this.routDest = dest;
		this.routSource = source;
		this.routDestFinale = dest;
		this.routSourceFinale = source;
		this.dureeAppel = duree;
	}
	
}