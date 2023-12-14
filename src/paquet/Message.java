package paquet;

public class Message{
	public int ID;
	public int etat; //etat 0 = Demande de réservation; etat 1 = Appel en cours; etat 2 = appel bloqué; etat 3 = appel accepté
	public int compteur;
	public Routeur routDest;
	public Routeur routSource;
	static Routeur routDestFinale;
	static Routeur routSourceFinale;
	static int dureeAppel;
	
	public void Message() {
		
	}
	
}