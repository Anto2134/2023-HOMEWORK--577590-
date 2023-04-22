package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando {
	private String parametro;
	private IO io;
	private final static String NOME = "posa";

	@Override
	public void esegui(Partita partita) {
		if(!partita.getGiocatore().getBorsa().hasAttrezzo(parametro)) {
			this.io.mostraMessaggio("Attrezzo " + parametro + " non presente nella borsa");
			return;
		}
		Attrezzo attrezzoDaPosare = partita.getGiocatore().getBorsa().removeAttrezzo(parametro);
		partita.getStanzaCorrente().addAttrezzo(attrezzoDaPosare);
		this.io.mostraMessaggio("Attrezzo " + parametro + " posato!");

		for(Attrezzo attrezzo : partita.getGiocatore().getBorsa().getAttrezzi()) {
			if(attrezzo != null) {
				io.mostraMessaggio("Attrezzi nella borsa:\n");
				io.mostraMessaggio(attrezzo.getNome());
			}
		}
		if(partita.getGiocatore().getBorsa().isEmpty())
			io.mostraMessaggio("nessun attrezzo nella borsa");
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
