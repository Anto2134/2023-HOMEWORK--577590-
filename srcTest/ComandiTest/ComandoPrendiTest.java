package ComandiTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
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
		this.partita = new Partita();
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

}
