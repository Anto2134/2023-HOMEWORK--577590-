package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.IO;

public class ComandoNonValido implements Comando {
	private IO io;
	private static final String NOME = "non valido";

	@Override
	public void esegui(Partita partita) {
		this.io.mostraMessaggio("Comando non valido");
	}

	@Override
	public void setParametro(String parametro) {}

	@Override
	public void setIO(IO io) {
		this.io = io;
	}

	@Override
	public String getNome() {
		return ComandoNonValido.NOME;
	}

	@Override
	public String getParametro() {
		return null;
	}
}
