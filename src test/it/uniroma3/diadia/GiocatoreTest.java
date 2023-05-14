package it.uniroma3.diadia;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.giocatore.Borsa;
import it.uniroma3.diadia.giocatore.Giocatore;

import org.junit.Before;


public class GiocatoreTest {
	Giocatore gTest;
	Borsa b;
	
	@Before
	public void setUp() {
		gTest = new Giocatore();
		b = new Borsa();
	}

	@Test
	public void testGetCfuDefault() {
		assertEquals(20, gTest.getCfu());
	}

	@Test
	public void testSetCfu() {
		gTest.setCfu(6);
		assertEquals(6,gTest.getCfu());
	}

	@Test
	public void testSetBorsa() {
		gTest.setBorsa(b);
		assertNotNull(gTest.getBorsa());
	}
}
