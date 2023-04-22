package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine implements Comando {
	private IO io;
	private final static String NOME = "Grazie di aver giocato";
	public static final Object MESSAGGIO_FINE = null;

	@Override
	public void esegui(Partita partita) {
		partita.setFinita();
		io.mostraMessaggio(NOME);
	}

	@Override
	public void setParametro(String parametro) {}

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
