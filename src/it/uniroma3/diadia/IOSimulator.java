package it.uniroma3.diadia;

import java.util.ArrayList;

import java.util.Iterator;

public class IOSimulator implements IO {

	private ArrayList<String> righeDaLeggere;
	private int indiceRigheDaLeggere;
	private ArrayList<String> messaggiProdotti;
	private int indiceMessaggiMostrati;
	private Iterator<String> itMessaggiProdotti;

	public IOSimulator(ArrayList<String> righeDaLeggere2) {
		this.righeDaLeggere = new ArrayList<String>();
		this.righeDaLeggere = righeDaLeggere2;
		this.indiceRigheDaLeggere = 0;
		this.messaggiProdotti = new ArrayList<String>();
		this.itMessaggiProdotti = messaggiProdotti.iterator();
		this.indiceMessaggiMostrati = 0;
	}

	@Override
	public void mostraMessaggio(String messaggio) {

		this.messaggiProdotti.add(messaggio);
		//this.messaggiProdotti[this.indiceMessaggiProdotti] = messaggio;
		//this.indiceMessaggiProdotti++;
	}

	@Override
	public String leggiRiga() {
		String rigaLetta = this.righeDaLeggere.get(indiceRigheDaLeggere);
		this.indiceRigheDaLeggere++;
		//String rigaLetta = this.righeDaLeggere[this.indiceRigheDaLeggere];
		//this.indiceRigheDaLeggere++;
		return rigaLetta;
	}

	public String nextMessaggio() {
		String next = this.messaggiProdotti.get(indiceMessaggiMostrati);
		this.indiceMessaggiMostrati++;
		//for(int i = 0; i < this.messaggiProdotti.size(); i++) {
			//next = this.messaggiProdotti.get(i);
		//}
		/*while(this.itMessaggiProdotti.hasNext()) {
			next = this.itMessaggiProdotti.next();
		}*/
		//String next = this.messaggiProdotti[this.indiceMessaggiMostrati];
		//this.indiceMessaggiMostrati++;
		return next;
	}

	public boolean hasNextMessaggio() {
		return this.itMessaggiProdotti.hasNext();
	}

}