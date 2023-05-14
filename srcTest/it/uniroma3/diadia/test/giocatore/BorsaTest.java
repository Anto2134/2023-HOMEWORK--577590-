package it.uniroma3.diadia.test.giocatore;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class BorsaTest {
	Borsa b ;
	Attrezzo spada;
	Attrezzo osso;
	Attrezzo bastone;
	Attrezzo corda;
	Attrezzo ascia;
	Attrezzo lancia;
	Attrezzo martello;


	@Before
	public void setUp() {
		b = new Borsa();
		spada = new Attrezzo("spada",12);
		osso = new Attrezzo("osso",1);
		bastone = new Attrezzo("bastone",3);
		corda = new Attrezzo("corda",2);
		ascia = new Attrezzo("ascia",4);
		lancia = new Attrezzo("lancia",3);
		martello = new Attrezzo("martello",5);
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
	public void testIsEmptyIniziale() {
		assertTrue(b.isEmpty());
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

	@Test
	public void testGetContenutoOrdinatoPerPeso() {
		b.addAttrezzo(ascia);//4
		b.addAttrezzo(bastone);//3
		b.addAttrezzo(corda);//2
		List<Attrezzo> attrezzi = new ArrayList<>(b.getContenutoOrdinatoPerPeso());
		Iterator<Attrezzo> a = attrezzi.iterator();
		assertNotNull(a);
		assertTrue(a.hasNext());
		assertEquals("corda",a.next().getNome());
		assertTrue(a.hasNext());
		assertEquals("bastone",a.next().getNome());
		assertTrue(a.hasNext());
		assertEquals("ascia",a.next().getNome());
	}

	@Test
	public void testGetContenutoOrdinatoPerNome() {
		b.addAttrezzo(corda);//2
		b.addAttrezzo(lancia);
		b.addAttrezzo(osso);
		b.addAttrezzo(spada);
		Set<Attrezzo> attrezzi = new TreeSet<>(b.getContenutoOrdinatoPerNome());
		Iterator<Attrezzo> a = attrezzi.iterator();
		assertNotNull(a);
		assertTrue(a.hasNext());
		assertTrue(a.hasNext());
		assertEquals("corda", a.next().getNome());
		assertEquals("lancia", a.next().getNome());
		assertEquals("osso", a.next().getNome());
	}

	@Test
	public void testGetContenutoRaggruppatoPerPeso() {
		b.addAttrezzo(ascia);
		b.addAttrezzo(corda);
		Map<Integer, Set<Attrezzo>> attrezzi = new HashMap<Integer, Set<Attrezzo>>(b.getContenutoRaggruppatoPerPeso());
		assertEquals(attrezzi,b.getContenutoRaggruppatoPerPeso());
	}

	@Test
	public void testGetSortedOrdinatoPerPeso() {
		b.addAttrezzo(bastone);//3
		b.addAttrezzo(lancia);//3
		b.addAttrezzo(ascia);//4
		SortedSet<Attrezzo> attrezzi = new TreeSet<>(b.getSortedSetOrdinatoPerPeso());
		Iterator<Attrezzo> a = attrezzi.iterator();
		assertNotNull(a);
		assertEquals("bastone", a.next().getNome());
		assertEquals("lancia", a.next().getNome());
		assertEquals("ascia", a.next().getNome());
	}
}
