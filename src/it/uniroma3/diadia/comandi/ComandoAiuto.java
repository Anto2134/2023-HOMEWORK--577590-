package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.IO;

public class ComandoAiuto extends AbstractComando {
	private final String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa", "guarda",
			"interagisci", "saluta", "nonValido"};;
	private final static String NOME = "aiuto";

	public ComandoAiuto(String[] elencoComandi) {
		super.setNome(NOME);
	}

	@Override
	public void esegui(Partita partita) {
		for(int i=0; i< elencoComandi.length; i++) 
			super.getIo().mostraMessaggio(elencoComandi[i] + " " );
		super.getIo().mostraMessaggio("");
	}
}
