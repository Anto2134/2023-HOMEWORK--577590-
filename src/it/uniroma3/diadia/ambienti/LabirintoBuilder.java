package it.uniroma3.diadia.ambienti;


import java.util.HashMap;
import java.util.Map;


import it.uniroma3.diadia.attrezzi.Attrezzo;

public class LabirintoBuilder  {
	private Labirinto labirinto;
	private Stanza ultimaStanzaAggiunta;
	private Map<String, Stanza> nome2stanza;

	public LabirintoBuilder() {
		labirinto = new Labirinto();
		nome2stanza = new HashMap<String,Stanza>();
	}

	public void aggiungiAMappaEAggiornaUltimaStanza(Stanza stanza) {
		this.ultimaStanzaAggiunta = stanza;
		this.nome2stanza.put(stanza.getNome(), stanza);
	}

	public LabirintoBuilder addStanzaIniziale(String nomeStanzaIniziale) {
		Stanza iniziale = new Stanza(nomeStanzaIniziale);
		this.labirinto.setStanzaIniziale(iniziale);
		this.aggiungiAMappaEAggiornaUltimaStanza(iniziale);
		return this;
	}

	public LabirintoBuilder addStanzaVincente(String nomeStanzaVincente) {
		Stanza vincente = new Stanza(nomeStanzaVincente);
		this.labirinto.setStanzaVincente(vincente);
		this.aggiungiAMappaEAggiornaUltimaStanza(vincente);
		return this;
	}

	public LabirintoBuilder addAdiacenza(String nomeStanzaIniziale, String nomeStanzaAdiacente, String direzione) {
		Stanza stanzaPartenza = this.nome2stanza.get(nomeStanzaIniziale);
		Stanza stanzaArrivo = this.nome2stanza.get(nomeStanzaAdiacente);
		stanzaPartenza.impostaStanzaAdiacente(direzione, stanzaArrivo);
		return this;
	}

	public LabirintoBuilder addStanza(String nome) {
		Stanza stanza = new Stanza(nome);
		this.aggiungiAMappaEAggiornaUltimaStanza(stanza);
		return this;
	}

	public LabirintoBuilder addAttrezzo(String nome, int peso) {
		Attrezzo a = new Attrezzo(nome, peso);
		this.ultimaStanzaAggiunta.addAttrezzo(a);
		return this;
	}

	public LabirintoBuilder addStanzaMagica(String nome, int sogliaMagica) {
		this.aggiungiAMappaEAggiornaUltimaStanza(new StanzaMagica(nome, sogliaMagica));
		return this;
	}

	public Labirinto getLabirinto() {
		return this.labirinto;
	}

	public LabirintoBuilder addStanzaBuia(String nome, String attrezzoPerVedere) {
		StanzaBuia stanzaBuia = new StanzaBuia(nome, attrezzoPerVedere);
		this.aggiungiAMappaEAggiornaUltimaStanza(stanzaBuia);
		return this;
	}

	public LabirintoBuilder addStanzaBloccata(String nome, String attrezzoSbloccante, String direzioneBloccata) {
		StanzaBloccata stanza = new StanzaBloccata(nome, attrezzoSbloccante, direzioneBloccata);
		this.aggiungiAMappaEAggiornaUltimaStanza(stanza);
		return this;
	}

	public Map<String, Stanza> getListaStanze() {
		return this.nome2stanza;
	}
}

