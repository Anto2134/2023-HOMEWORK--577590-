package it.uniroma3.diadia.test.comandi;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.Before;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoPrendi;
import it.uniroma3.diadia.giocatore.Borsa;

public class ComandoPrendiTest {
	private ComandoPrendi prendi;
	private Partita partita;
	private String ATTREZZO_INIZIALMENTE_NELLA_STANZA = "AttrezzoDaPrendere";

	@Before
	public void setUp() throws Exception {
		this.prendi = new ComandoPrendi();
		this.prendi.setIO(new IOConsole());
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("stanza iniziale")
				.getLabirinto();
		this.partita = new Partita(labirinto);
		Attrezzo attrezzoNuovo = new Attrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA,2);
		this.partita.getStanzaCorrente().addAttrezzo(attrezzoNuovo);

	}

	@Test
	public void testEseguiConAttrezzo() {
		prendi.setParametro(ATTREZZO_INIZIALMENTE_NELLA_STANZA);
		prendi.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA));
		assertFalse(partita.getStanzaCorrente().hasAttrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA));
	}
	
	@Test
	public void testEseguiSenzaAttrezzo() {
		Attrezzo attrezzoNonPresente = new Attrezzo("non presente",1);
		this.prendi.setParametro(attrezzoNonPresente.getNome());
		this.prendi.esegui(partita);
		assertFalse(this.partita.getGiocatore().getBorsa().hasAttrezzo(attrezzoNonPresente.getNome()));
		assertFalse(this.partita.getGiocatore().getBorsa().hasAttrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA));
		assertTrue(this.partita.getStanzaCorrente().hasAttrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA));
	}
	
	@Test 
	public void testBorsaPiena() {
		Borsa borsa = this.partita.getGiocatore().getBorsa();
		borsa.addAttrezzo(new Attrezzo(("attrezzoPesante"),borsa.getPesoMax()));
		this.prendi.setParametro(ATTREZZO_INIZIALMENTE_NELLA_STANZA);
		this.prendi.esegui(partita);
		assertFalse(this.partita.getGiocatore().getBorsa().hasAttrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA));
		assertTrue(this.partita.getStanzaCorrente().hasAttrezzo(ATTREZZO_INIZIALMENTE_NELLA_STANZA));
	}
}
