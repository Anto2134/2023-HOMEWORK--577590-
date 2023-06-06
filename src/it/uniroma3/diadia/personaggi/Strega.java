package it.uniroma3.diadia.personaggi;


import java.util.TreeSet;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.ComparatoreStanzePerNumeroAttrezzi;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {

	public Strega(String nome, String presentaz) {
		super(nome, presentaz);
	}

	@Override
	public String agisci(Partita partita) {
		TreeSet<Stanza> stanze = new TreeSet<>(new ComparatoreStanzePerNumeroAttrezzi());
		for(Direzione direzione : partita.getStanzaCorrente().getDirezioni()) {
			stanze.add(partita.getStanzaCorrente().getStanzaAdiacente(direzione));
		}
		if(super.haSalutato())
			partita.setStanzaCorrente(stanze.first());
		else
			partita.setStanzaCorrente(stanze.last());
		return "Ti ho spostato";
	}

	@Override
	public String riceviRegalo(Attrezzo regalo, Partita partita) {
		return "Non ho niente";
	}
	
	public String getRegalo() {
		return "Non ho niente";
	}
	
	public String getCiboPreferito() {
		return "Mi hai scambiato per un cane?";
	}

}
