package it.uniroma3.diadia.test;

import static org.junit.Assert.*;




import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.Stanza;

public class PartitaTest {
	private Partita partita;
	private Labirinto labirinto;

	@Before
	public void setUp() {
		this.labirinto = new LabirintoBuilder()
				.addStanzaIniziale("iniziale")
				.addStanzaVincente("vincente")
				.getLabirinto();
		this.partita = new Partita(this.labirinto);
	}

	@Test
	public void testStanzaVincente() {
		assertNotNull(partita.getStanzaVincente());
	}

	@Test
	public void testVincenteConStanzaCorrente() {
		this.partita.setStanzaCorrente(this.partita.getStanzaVincente());
		assertTrue(this.partita.vinta());
	}

	@Test
	public void testPartitaNonVintaConStanzaCorrenteNonVincente() {
		this.partita.setStanzaCorrente(new Stanza("non vincente"));
		assertFalse(this.partita.vinta());
	}
	
	@Test
	public void testNonVintaAInizioPartita() {
		assertFalse(partita.vinta());
	}
	
	@Test
	public void testFinitaSeVinta() {
		this.partita.setStanzaCorrente(this.partita.getStanzaVincente());
		assertTrue(this.partita.isFinita());
	}
	
	@Test
	public void testSetFinitaCon0Cfu() {
		this.partita.setCfu(0);
		assertTrue(this.partita.isFinita());
	}

	@Test
	public void testGetCfu() {
		partita.setCfu(10);
		assertEquals(10,partita.getCfu());
	}
	@Test
	public void testIsFinita() {
		partita.setFinita();
		assertTrue(partita.isFinita());
	}


}
