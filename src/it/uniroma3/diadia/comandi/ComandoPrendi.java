package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	private String parametro;
	private IO io;
	private final static String NOME = "prendi";

	@Override
	public void esegui(Partita partita) {
		if(!partita.getStanzaCorrente().hasAttrezzo(this.parametro))  {
			io.mostraMessaggio("Attrezzo non presente nella stanza");
			return;
		}
		Attrezzo attrezzoDaPrendere = partita.getStanzaCorrente().getAttrezzo(parametro);
		boolean attrezzoPreso = partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPrendere);
		if (!attrezzoPreso) {
			io.mostraMessaggio("Attrezzo non preso");
			return;
		}
		partita.getStanzaCorrente().removeAttrezzo(attrezzoDaPrendere);
		this.io.mostraMessaggio("Attrezzo " + this.parametro + " preso\n");
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
		return this.parametro;
	}

}
