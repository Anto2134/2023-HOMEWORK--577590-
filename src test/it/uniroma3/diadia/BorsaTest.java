package it.uniroma3.diadia;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class BorsaTest {
	Borsa b ;
	Attrezzo spada;
	Attrezzo osso;

	@Before
	public void setUp() {
		b = new Borsa();
		spada = new Attrezzo("spada",12);
		osso = new Attrezzo("osso",1);
	}

	@Test
	public void testaddAttrezzoPesoOk() {
		assertTrue(b.addAttrezzo(osso));
	}

	@Test
	public void testAddAttrezzoSopraPesoMax() {
		assertFalse(b.addAttrezzo(spada));
	}

	@Test
	public void testGetPesoMax() {
		assertEquals(10, b.getPesoMax());
	}

	@Test
	public void testGetPeso() {
		assertEquals(0, b.getPeso());
	}

	@Test
	public void testGetAttrezzo() {
		b.addAttrezzo(osso);
		assertEquals(osso,b.getAttrezzo("osso"));
	}

	@Test
	public void testRemoveAttrezzo() {
		b.addAttrezzo(osso);
		b.removeAttrezzo(osso.getNome());
		assertNull(b.getAttrezzo("osso"));
	}
	@Test
	public void testIsEmpty() {
		b.addAttrezzo(osso);
		assertFalse(b.isEmpty());
	}

	@Test
	public void testHasAttrezzo() {
		b.addAttrezzo(osso);
		assertTrue(b.hasAttrezzo("osso"));
	}
}
