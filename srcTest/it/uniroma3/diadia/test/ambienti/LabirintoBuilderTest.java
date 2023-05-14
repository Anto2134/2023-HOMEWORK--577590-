package it.uniroma3.diadia.test.ambienti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class LabirintoBuilderTest {
	private LabirintoBuilder labirinto;
	
	@Before
	public void setUp() {
		labirinto = new LabirintoBuilder();
	}

	@Test
	public void testAddStanzaIniziale() {
		labirinto.addStanzaIniziale("Atrio");
		labirinto.getLabirinto();
		String attesa = labirinto.getLabirinto().getStanzaIniziale().getNome();
		assertEquals("Atrio", attesa);
	}
	
	@Test
	public void testAddStanzaVincente() {
		labirinto.addStanzaVincente("N10");
		String attesa = labirinto.getLabirinto().getStanzaVincente().getNome();
		assertEquals("N10", attesa);
	}
	
	@Test
	public void testAddAttrezzoConUnaStanza() {
		labirinto.addStanzaIniziale("Atrio");
		labirinto.addAttrezzo("osso",1);
		labirinto.getLabirinto();
		Attrezzo osso = new Attrezzo("osso",1);
		assertEquals(osso.getNome(), labirinto.getListaStanze().get("Atrio").getAttrezzo("osso").getNome());
	}
	
	@Test
	public void testAddAttrezzoConPiùStanze() {
		labirinto.addStanzaIniziale("Atrio");
		labirinto.addStanza("N10");
		labirinto.addAttrezzo("osso",1);
		assertEquals(new Attrezzo("osso",1).getNome(), labirinto.getListaStanze().get("N10").getAttrezzo("osso").getNome());
	}
	
	@Test
	public void testAddAdiacenza() {
		labirinto.addStanza("Atrio");
		labirinto.addStanza("N10");
		labirinto.addAdiacenza("Atrio", "N10", "nord");
		assertEquals(new Stanza("N10").getNome(), labirinto.getListaStanze().get("Atrio").getStanzaAdiacente("nord").getNome());
	}

}
