package it.uniroma3.it.test.comandi;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.comandi.ComandoFine;
import it.uniroma3.diadia.comandi.ComandoVai;
import it.uniroma3.diadia.test.fixture.Fixture;

public class ComandoVaiTest {


	private static final String NOME_STANZA_PARTENZA = "Partenza";
	private static final String NORD = "nord";
	private Partita partita;
	private ComandoVai comandoVai;
	private Stanza partenza;


	@Before
	public void setUp() throws Exception {
		this.comandoVai = new ComandoVai();
		this.comandoVai.setIO(new IOConsole());
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale(NOME_STANZA_PARTENZA)
				.getLabirinto();
		this.partita = new Partita(labirinto);
		this.partenza = this.partita.getStanzaCorrente();
	}

	@Test
	public void testVaiStanzaNonPresente() {
		this.comandoVai.setParametro(NORD);
		this.comandoVai.esegui(this.partita);
		assertEquals(NOME_STANZA_PARTENZA, this.partita.getStanzaCorrente().getNome());
	}

	@Test
	public void testVaiStanzaPresente() {
		Stanza destinazione = new Stanza("Destinazione");
		this.partenza.impostaStanzaAdiacente(NORD, destinazione);
		this.comandoVai.setParametro(NORD);
		this.comandoVai.esegui(partita);
		assertEquals("Destinazione", this.partita.getStanzaCorrente().getNome());
	}

	@Test
	public void testVaiStanzaPresenteInDirezioneSbagliata() {
		Stanza destinazione = new Stanza("Destinazione");
		this.partenza.impostaStanzaAdiacente("sud", destinazione);
		this.comandoVai.setParametro(NORD);
		this.comandoVai.esegui(partita);
		assertEquals(NOME_STANZA_PARTENZA, this.partita.getStanzaCorrente().getNome());
	}

	@Test
	public void testPartitaConComandoVai() {
		ArrayList<String> comandiDaEseguire = new ArrayList<>();
		comandiDaEseguire.add("vai sud");
		comandiDaEseguire.add("fine");
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("iniziale")
				.addStanza("Aula N10")
				.addAdiacenza("iniziale", "Aula N10", "sud")
				.getLabirinto();
		IOSimulator io = Fixture.creaSimulazionePartitaEGioca(labirinto, comandiDaEseguire);
		assertTrue(io.hasNextMessaggio());
		assertEquals(DiaDia.MESSAGGIO_BENVENUTO, io.nextMessaggio());
		assertTrue(io.hasNextMessaggio());
		assertEquals("Aula N10", io.nextMessaggio());
		assertTrue(io.hasNextMessaggio());
		assertEquals(ComandoFine.MESSAGGIO_FINE, io.nextMessaggio());
	}

	public void assertContains(String expected, String interaRiga) {
		assertTrue(interaRiga.contains(expected));
	}

}