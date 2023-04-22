package it.uniroma3.diadia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;

public class PartitaTest {
	Partita p;
	Labirinto l;
	Giocatore g;
	
	@Before
	public void setUp() {
		p = new Partita();
		l = new Labirinto();
		g = new Giocatore();
	}

	@Test
	public void testGetCfu() {
		p.setCfu(10);
		assertEquals(10,p.getCfu());
	}
	@Test
	public void testIsFinita() {
		p.setFinita();
		assertTrue(p.isFinita());
	}


}
