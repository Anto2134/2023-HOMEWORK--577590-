package it.uniroma3.it.test.comandi;

import static org.junit.Assert.*;


import java.util.ArrayList;


import org.junit.Test;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.comandi.ComandoFine;
import it.uniroma3.diadia.test.fixture.Fixture;

public class ComandoFineTest {

	@Test
	public void testPartitaConComandoFine() {
		ArrayList<String> righeDaLeggere = new ArrayList<>();
		righeDaLeggere.add("fine");
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("stanza iniziale")
				.getLabirinto();
				
		IOSimulator io = Fixture.creaSimulazionePartitaEGioca(labirinto, righeDaLeggere);
		assertTrue(io.hasNextMessaggio());
		assertEquals(DiaDia.MESSAGGIO_BENVENUTO, io.nextMessaggio());
		assertTrue(io.hasNextMessaggio());
		assertEquals(ComandoFine.MESSAGGIO_FINE, io.nextMessaggio());
	}

}