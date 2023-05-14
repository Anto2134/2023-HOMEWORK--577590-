package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {
	private final static String NOME = "guarda";
	private IO io;
	private String parametro;

	@Override
	public void esegui(Partita partita) {
		if(parametro == null) {
			io.mostraMessaggio(partita.getStanzaCorrente().getDescrizione());
			io.mostraMessaggio(partita.getGiocatore().toString());
		}
		else if(this.parametro.equals("nome") && parametro != null)
			io.mostraMessaggio(partita.getGiocatore().getBorsa().getContenutoOrdinatoPerNome().toString());
		else if(this.parametro.equals("peso1") && parametro != null)
			io.mostraMessaggio(partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().toString());
		else if(this.parametro.equals("peso2") && parametro != null)
			io.mostraMessaggio(partita.getGiocatore().getBorsa().getContenutoRaggruppatoPerPeso().toString());
		else if(this.parametro.equals("peso3") && parametro != null)
			io.mostraMessaggio(partita.getGiocatore().getBorsa().getSortedSetOrdinatoPerPeso().toString());
		else if (!this.parametro.equals("nome") || !this.parametro.equals("peso1") || 
				!this.parametro.equals("peso2") || !this.parametro.equals("peso3"))
			io.mostraMessaggio("Comando non valido");
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
		return null;
	}
}
