package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi extends AbstractComando {
	private final static String NOME = "prendi";

	public ComandoPrendi() {
		super.setNome(NOME);
	}
	
	@Override
	public void esegui(Partita partita) {
		if(!partita.getStanzaCorrente().hasAttrezzo(super.getParametro()))  {
			super.getIo().mostraMessaggio("Attrezzo non presente nella stanza");
			return;
		}
		Attrezzo attrezzoDaPrendere = partita.getStanzaCorrente().getAttrezzo(super.getParametro());
		boolean attrezzoPreso = partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPrendere);
		if (!attrezzoPreso) {
			super.getIo().mostraMessaggio("Attrezzo non preso");
			return;
		}
		partita.getStanzaCorrente().removeAttrezzo(attrezzoDaPrendere);
		super.getIo().mostraMessaggio("Attrezzo " + super.getParametro() + " preso\n");
	}
}
