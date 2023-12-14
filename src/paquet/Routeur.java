package paquet;
import java.util.Arrays;
import java.util.List;

public class Routeur {
	final static int sizeBuffer = 10000;
	public List<Message> buffer = Arrays.asList(new Message[sizeBuffer]);
	public Link[] listeLienRouteur;

	public void Routeur() {
	}

	public void maj(){
		//On envoie le premier message de la liste dans le bon lien et on initialise le compteur à link.tpsTrajet
		//lors de lajout dans un lien, il faut diminuer de 1 la capacité résiduelle de ce meme lien
		
		//il va falloir gérer lorsque le message arrive à sa destination finale on doit changer l'état à 3
		// et si le lien est plein : le message est bloqué => état = 2 
		//==> on change l'état du message dans le lien source, on change la destination et tout et on remet le compteur à link.tpsTrajet (comme ça ça repart dans l'autre sens)
	}
}
