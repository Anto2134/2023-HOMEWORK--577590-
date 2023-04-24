package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaTest {
	Stanza s = new Stanza("stanzaTest");
	Stanza s2 = new Stanza("stanzaTest2");
	Attrezzo osso;
	Attrezzo lanterna;

	@Before
	public void setUp() {
		osso = new Attrezzo("osso",1);
		lanterna = new Attrezzo("lanterna",3);
	}

	@Test
	public void testAddAttrezzo() {
		assertTrue(s.addAttrezzo(lanterna));
        assertNotNull(s.hasAttrezzo("lanterna"));
	}
	@Test
	public void testGetStanzaAdiacente() {
		assertNull(s.getStanzaAdiacente("nord"));
	}
	@Test
	public void testSetStanzaAdiacente() {
		s.impostaStanzaAdiacente("sud", s2);
		assertEquals(s2,s.getStanzaAdiacente("sud"));
	}
	@Test
	public void testGetAttrezzo() {
		s.addAttrezzo(lanterna);
		assertEquals(lanterna,s.getAttrezzo(lanterna.getNome()));
	}
	@Test
	public void testRemoveAttrezzo() {
		s.addAttrezzo(lanterna);
		assertTrue(s.removeAttrezzo(lanterna));
	}
	@Test
	public void testHasAttrezzo() {
		s.addAttrezzo(lanterna);
		assertTrue(s.hasAttrezzo(lanterna.getNome()));
	}
	@Test
	public void testGetDirezioni() {
		s.impostaStanzaAdiacente("sud", s2);
		assertNotNull(s.getDirezioni());
	}
}
