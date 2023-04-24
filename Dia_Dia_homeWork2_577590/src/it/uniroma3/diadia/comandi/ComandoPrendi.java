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
		if(!partita.getStanzaCorrente().hasAttrezzo(this.parametro)) 
			io.mostraMessaggio("Attrezzo non presente nella stanza");
		else {
			while(partita.getStanzaCorrente().getAttrezzo(parametro)!=null) {
				boolean preso = false;
				Attrezzo attrezzoPreso = partita.getStanzaCorrente().getAttrezzo(parametro);
				partita.getStanzaCorrente().removeAttrezzo(attrezzoPreso);
				partita.getGiocatore().getBorsa().addAttrezzo(attrezzoPreso);
				preso = true;
				if (preso)
					io.mostraMessaggio("Attrezzo:" + attrezzoPreso.getNome() + " preso");
				if (!preso)
					io.mostraMessaggio("Attrezzo non preso");
			}
			io.mostraMessaggio("Attrezzi nella borsa:\n");
			for(Attrezzo attrezzo : partita.getGiocatore().getBorsa().getAttrezzi()) {
				if(attrezzo!=null)
					io.mostraMessaggio(attrezzo.getNome());
			}
		}

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
