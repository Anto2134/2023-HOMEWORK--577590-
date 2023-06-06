package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {
	private final static String NOME = "guarda";
	
	public ComandoGuarda() {
		super.setNome(NOME);
	}

	@Override
	public void esegui(Partita partita) {
		super.getIo().mostraMessaggio("Stanza corrente: " + partita.getStanzaCorrente().toString());
		super.getIo().mostraMessaggio("Informazioni giocatore: "+ partita.getGiocatore().toString());
	}
}
