package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class ComandoPosa implements Comando {
	private String parametro;
	private IO io;
	private final static String NOME = "posa";


	@Override
	public void esegui(Partita partita) {
		Borsa borsa = partita.getGiocatore().getBorsa();
		Attrezzo attrezzoDaPosare = borsa.getAttrezzo(parametro);
		if(!borsa.hasAttrezzo(parametro)) {
			this.io.mostraMessaggio("Attrezzo " + parametro + " non presente nella borsa");
			return;
		}
		boolean attrezzoPosato = partita.getStanzaCorrente().addAttrezzo(attrezzoDaPosare);
		if (!attrezzoPosato) {
			this.io.mostraMessaggio("Non c'è più spazio per nuovi attrezzi nella stanza");
			return;
		}

		borsa.removeAttrezzo(parametro);
		this.io.mostraMessaggio("Attrezzo " + parametro + " posato!");
	}


	@Override
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	@Override
	public void setIO(IO io) {
		this.io = io;
	}

	@Override
	public String getNome() {
		return NOME;
	}

	@Override
	public String getParametro() {
		return parametro;
	}
}
