package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoRegala extends AbstractComando {
	private final static String NOME = "regala";
	
	public ComandoRegala() {
		super.setNome(NOME);
	}
	
	@Override
	public void esegui(Partita partita) {
		if(!partita.getGiocatore().getBorsa().hasAttrezzo(super.getParametro()))
			super.getIo().mostraMessaggio("Attrezzo non presente nella borsa");
		else {
			Attrezzo attrezzo = partita.getGiocatore().getBorsa().getAttrezzo(super.getParametro());
			partita.getStanzaCorrente().getPersonaggio().riceviRegalo(attrezzo, partita);
			partita.getGiocatore().getBorsa().removeAttrezzo(attrezzo.getNome());
			super.getIo().mostraMessaggio("Attrezzo regalato");
		}
	}
	
	
}
