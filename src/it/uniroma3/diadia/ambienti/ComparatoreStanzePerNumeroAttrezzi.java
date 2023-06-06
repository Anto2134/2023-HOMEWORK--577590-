package it.uniroma3.diadia.ambienti;

import java.util.Comparator;

public class ComparatoreStanzePerNumeroAttrezzi implements Comparator<Stanza> {
	
	@Override
	public int compare(Stanza s1, Stanza s2) {
		return s2.getAttrezzi().size() - s1.getAttrezzi().size();
	}
}
