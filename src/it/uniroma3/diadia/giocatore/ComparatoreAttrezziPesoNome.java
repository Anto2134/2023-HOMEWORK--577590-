package it.uniroma3.diadia.giocatore;

import java.util.Comparator;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComparatoreAttrezziPesoNome implements Comparator<Attrezzo> {


	@Override
	public int compare(Attrezzo a1, Attrezzo a2) {
		int pesoDiff = a1.getPeso() - a2.getPeso();
		if(pesoDiff != 0) {
			return pesoDiff;
		}
		return a1.getNome().compareTo(a2.getNome());
	}
}
