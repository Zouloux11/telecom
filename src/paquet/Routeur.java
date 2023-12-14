package paquet;
import java.util.Arrays;
import java.util.List;

public class Routeur {
	final static int sizeBuffer = 10000;
	public List<Message> buffer = Arrays.asList(new Message[sizeBuffer]);
	public List<Link> listeLienRouteur = Arrays.asList(new Link[4]);

	public Routeur(Link lien1, Link lien2, Link lien3) {
		this.listeLienRouteur.add(lien1);
		this.listeLienRouteur.add(lien2);
		this.listeLienRouteur.add(lien3);
	}
	public Routeur(Link lien1, Link lien2, Link lien3, Link lien4) {
		this.listeLienRouteur.add(lien1);
		this.listeLienRouteur.add(lien2);
		this.listeLienRouteur.add(lien3);
		this.listeLienRouteur.add(lien4);
	}

	public void maj(){
		//On envoie le premier message de la liste dans le bon lien et on initialise le compteur à link.tpsTrajet
		//lors de lajout dans un lien, il faut diminuer de 1 la capacité résiduelle de ce meme lien
		// et changer les sources/destinations temporaire
		
		//il va falloir gérer lorsque le message arrive à sa destination finale on doit changer l'état à 3
		// et si le lien est plein : le message est bloqué => état = 2 
		//==> on change l'état du message dans le lien source, on change la destination et tout et on remet le compteur à link.tpsTrajet (comme ça ça repart dans l'autre sens)
	}
}
