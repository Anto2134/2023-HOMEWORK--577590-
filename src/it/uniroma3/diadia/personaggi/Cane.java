package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio {
	private Attrezzo regaloDelCane;
	private String ciboPreferito;
	private boolean regaloPosato = false;


	public Cane(String nome, String presentaz, String ciboPreferito, Attrezzo regalo) {
		super(nome, presentaz);
		this.ciboPreferito = ciboPreferito;
		this.regaloDelCane = regalo;
	}

	@Override
	public String agisci(Partita partita) {
		partita.setCfu(partita.getCfu()-1);
		return "Giocatore morso";
	}

	@Override
	public String riceviRegalo(Attrezzo regalo, Partita partita) {
		if(regalo.getNome().equals(ciboPreferito)) {
			partita.getStanzaCorrente().addAttrezzo(regaloDelCane);
			regaloPosato = true;
			return "il cane ti lascia andare con un regalo";
		}
		else {
			partita.getStanzaCorrente().addAttrezzo(regalo);
			return agisci(partita);
		}
	}
	
	public String getRegalo() {
		return this.regaloDelCane.getNome();
	}
	
	public String getCiboPreferito() {
		return this.ciboPreferito;
	}
}
