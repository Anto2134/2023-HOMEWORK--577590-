package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Mago extends AbstractPersonaggio {
	private static final String MESSAGGIO_DONO = "Sei un vero simpaticone, " +
			"con una mia magica azione, troverai un nuovo oggetto " +
			"per il tuo borsone!";
	private static final String MESSAGGIO_SCUSE = "Mi spiace, ma non ho piu' nulla...";
	private Attrezzo attrezzo;
	
	
	public Mago(String nome, String presentazione, Attrezzo attrezzo) {
		super(nome, presentazione);
		this.attrezzo = attrezzo;
	}
	@Override
	public String agisci(Partita partita) {
		String msg;
		if (this.attrezzo!=null) {
			return riceviRegalo(attrezzo, partita);
		}
		else {
			msg = MESSAGGIO_SCUSE;
		}
		return msg;
	}
	@Override
	public String riceviRegalo(Attrezzo regalo, Partita partita) {
		this.attrezzo = regalo;
		partita.getStanzaCorrente().addAttrezzo(regalo);
		this.attrezzo = null;
		return MESSAGGIO_DONO;
	}
	@Override
	public String getRegalo() {
		return "non ho regali";
	}
	@Override
	public String getCiboPreferito() {
		return "non ho cibo";
	}


}
