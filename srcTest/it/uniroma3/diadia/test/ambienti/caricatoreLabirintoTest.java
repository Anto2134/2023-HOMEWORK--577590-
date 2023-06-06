package it.uniroma3.diadia.test.ambienti;

import static org.junit.Assert.*;

import java.awt.TextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import it.uniroma3.diadia.ambienti.CaricatoreLabirinto;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class caricatoreLabirintoTest {
	private static final String PRESENTAZIONE = "Ciao sono un personaggio del gioco";
	private static final String DESCRIZIONE_LABIRINTO
	= "Stanze:biblioteca,N10,N11\n"+
			"Inizio:N10\n"+
			"Vincente:N11\n"+
			"Attrezzi:martello 10 N10,pinza 2 N11\n"+
			"Uscite:biblioteca nord N10,biblioteca sud N11\n"+
			"Magiche:Atrio,N9,N8,N7,N6\n"+
			"Personaggi:mago PRESENTAZIONE spada 3 N9, strega presentazione N7,cane presentazione osso arco 8 N10\n"+
			"Buie:Bar lanterna, N20 torcia\n"+
			"Bloccate:Tetto spada nord, N21 martello nord\n";


	@Test
	public void testCarica() throws FormatoFileNonValidoException, IOException {
		String path = "labirinto1.txt";
		char[] in = new char[1000];
		int size = 0;
		try {
			File file= new File(path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(DESCRIZIONE_LABIRINTO);
			bw.flush();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			size = br.read(in);
			System.out.println("Caratteri presenti: " + size );
			System.out.println("Il contenuto del file è il seguente: ");
			for(int i = 0; i < size; i++)
				System.out.print(in[i]);
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		CaricatoreLabirinto caricatore = new CaricatoreLabirinto(path);
		Labirinto labirinto = caricatore.getLabirinto();
		caricatore.carica();
		Direzione nord = Direzione.NORD;
		Direzione sud = Direzione.SUD;
		assertEquals("N10", caricatore.getStanzaIniziale().getNome());
		assertEquals("N11", caricatore.getStanzaVincente().getNome());
		assertTrue(caricatore.getLab().getStanza("N10").hasAttrezzo("martello"));
		assertTrue(caricatore.getLab().hasStanza("biblioteca"));
		assertTrue(caricatore.getLab().hasStanza("N10"));
		assertTrue(caricatore.getLab().hasStanza("N11"));
		assertTrue(caricatore.getStanzaIniziale().hasAttrezzo("martello"));
		assertTrue(caricatore.getStanzaVincente().hasAttrezzo("pinza"));
		assertEquals(10, caricatore.getLab().getStanza("N10").getAttrezzo("martello").getPeso());
		assertEquals("N10",caricatore.getLab().getStanza("biblioteca").getStanzaAdiacente(nord).getNome());
		assertEquals("N11",caricatore.getLab().getStanza("biblioteca").getStanzaAdiacente(sud).getNome());
		assertTrue(caricatore.getLab().hasStanza("Atrio"));
		assertTrue(caricatore.getLab().hasStanza("N9"));
		assertTrue(caricatore.getLab().hasStanza("N6"));
		assertEquals("mago", caricatore.getLab().getStanza("N9").getPersonaggio().getNome());
		assertEquals("strega", caricatore.getLab().getStanza("N7").getPersonaggio().getNome());
		assertEquals("cane", caricatore.getLab().getStanza("N10").getPersonaggio().getNome());
		assertTrue(caricatore.getLab().hasStanza("Bar"));
		assertTrue(caricatore.getLab().hasStanza("N21"));
		assertTrue(caricatore.getLab().hasStanza("Tetto"));
		
	}

}
