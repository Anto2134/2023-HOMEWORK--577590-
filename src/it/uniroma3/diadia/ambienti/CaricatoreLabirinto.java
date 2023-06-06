package it.uniroma3.diadia.ambienti;

import java.io.*;

import java.util.*;

import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class CaricatoreLabirinto {

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	private static final String STANZE_MARKER = "Stanze:";             

	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";    

	/* prefisso della riga contenente il nome stanza vincente */
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";  

	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeAttrezzo> <peso> <nomeStanza> */
	private static final String ATTREZZI_MARKER = "Attrezzi:";

	/* prefisso della riga contenente le specifiche dei collegamenti tra stanza nel formato <nomeStanzaDa> <direzione> <nomeStanzaA> */
	private static final String USCITE_MARKER = "Uscite:";

	/*prefisso della riga contenente le stanze magiche, buie e bloccate*/
	private static final String MAGICHE_MARKER = "Magiche:";
	private static final String BUIE = "Buie:";
	private static final String BLOCCATE = "Bloccate:";
	/*Prefisso della riga contente i personaggi*/
	private static final String PERSONAGGI = "Personaggi:";

	/*
	 *  Esempio di un possibile file di specifica di un labirinto (vedi POO-26-eccezioni-file.pdf)

		Stanze: biblioteca, N10, N11
		Inizio: N10
		Vincente: N11
		Attrezzi: martello 10 biblioteca, pinza 2 N10
		Uscite: biblioteca nord N10, biblioteca sud N11

	 */
	private LineNumberReader reader;

	private Map<String, Stanza> nome2stanza;

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;
	private LabirintoBuilder builder;


	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this.nome2stanza = new HashMap<>();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
		this.builder = new LabirintoBuilder();
	}


	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiInizialeEvincente();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
			this.leggiMagiche();
			this.leggiPersonaggi();
			this.leggiBuie();
			this.leggiBloccate();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}


	private  String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			String riga = this.reader.readLine();
			check(riga.startsWith(marker),"era attesa una riga che cominciasse per "+marker);
			return riga.substring(marker.length());
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException  {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		for(String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			Stanza stanza = new Stanza(nomeStanza);
			this.nome2stanza.put(nomeStanza, stanza);
			builder.addStanza(nomeStanza);
		}
	}

	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(",");
		try (Scanner scannerDiParole = scanner) {
			while(scannerDiParole.hasNext()) {
				result.add(scannerDiParole.next());
			}
		}
		return result;
	}


	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		String nomeStanzaIniziale = null;
		nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER);
		Stanza stanza = new Stanza(nomeStanzaIniziale);
		this.nome2stanza.put(nomeStanzaIniziale, stanza);
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale +" non definita");
		String nomeStanzaVincente = null;
		nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER);
		Stanza stanzaV = new Stanza(nomeStanzaVincente);
		this.nome2stanza.put(nomeStanzaVincente, stanzaV);
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		this.stanzaIniziale = this.nome2stanza.get(nomeStanzaIniziale);
		this.stanzaVincente = this.nome2stanza.get(nomeStanzaVincente);
		builder.addStanzaIniziale(nomeStanzaIniziale);
		builder.addStanzaVincente(nomeStanzaVincente);
	}

	private void leggiMagiche() throws FormatoFileNonValidoException {
		String nomiMagiche = this.leggiRigaCheCominciaPer(MAGICHE_MARKER);
		for(String nomeMagica : separaStringheAlleVirgole(nomiMagiche)) {
			if(nomeMagica != null) {
				StanzaMagica stanza = new StanzaMagica(nomeMagica);
				this.nome2stanza.put(nomeMagica, stanza);
				builder.addStanzaMagica(nomeMagica);			
			}
		}
	}

	private void leggiBuie() throws FormatoFileNonValidoException {
		String nomiBuie = this.leggiRigaCheCominciaPer(BUIE);
		for (String nomeBuia : separaStringheAlleVirgole(nomiBuie)) {
			try(Scanner scannerLinea = new Scanner(nomeBuia)){
				while(scannerLinea.hasNext()) {
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("nome stanza"));
					String nomeStanza = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("nome dell'attrezzo per vedere"));
					String nomeAttrezzoPerVedere = scannerLinea.next();
					StanzaBuia stanza = new StanzaBuia(nomeStanza, nomeAttrezzoPerVedere);
					this.nome2stanza.put(nomeStanza, stanza);
					builder.addStanzaBuia(nomeStanza, nomeAttrezzoPerVedere);
				}
			}
		}
	}

	private void leggiBloccate() throws FormatoFileNonValidoException {
		String nomiBloccate = this.leggiRigaCheCominciaPer(BLOCCATE);
		for(String nomeBloccata : separaStringheAlleVirgole(nomiBloccate)) {
			if(nomeBloccata != null) {
				try(Scanner scanner = new Scanner(nomeBloccata)){
					check(scanner.hasNext(), msgTerminazionePrecoce("Nome stanza"));
					String nomeStanza = scanner.next();
					String nomeAttrezzoSbloccante = null;
					check(scanner.hasNext(), msgTerminazionePrecoce("Nome attrezzo sbloccante."));
					nomeAttrezzoSbloccante = scanner.next();
					check(scanner.hasNext(), msgTerminazionePrecoce("direzione bloccata."));
					Direzione direzioneBloccata = Direzione.valueOf(scanner.next().toUpperCase());
					StanzaBloccata stanza = new StanzaBloccata(nomeStanza, nomeAttrezzoSbloccante, direzioneBloccata);
					this.nome2stanza.put(nomeStanza, stanza);
					builder.addStanzaBloccata(nomeStanza, nomeAttrezzoSbloccante, direzioneBloccata);
				}
			}
		}
	}

	private void leggiPersonaggi() throws FormatoFileNonValidoException{
		String specifichePersonaggi = this.leggiRigaCheCominciaPer(PERSONAGGI);
		for(String specificaPersonaggio : separaStringheAlleVirgole(specifichePersonaggi)) {
			String nomePersonaggio = null;
			String presentazione = null;
			String nomeStanza = null;
			try (Scanner scannerLinea = new Scanner(specificaPersonaggio)) {
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome del personaggio."));
				nomePersonaggio = scannerLinea.next();
				if(nomePersonaggio.equals("cane")) {
					String ciboPreferito = null;
					String nomeRegalo = null;
					String pesoRegalo = null;
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione del personaggio."));
					presentazione = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("il cibo preferito"));
					ciboPreferito = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome del regalo"));
					nomeRegalo = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("il peso del regalo"));
					pesoRegalo = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome della stanza in cui collocare"
							+ "il personaggio: " + nomePersonaggio));
					nomeStanza = scannerLinea.next();
					posizionaCane(nomePersonaggio, presentazione, ciboPreferito, nomeRegalo, pesoRegalo, nomeStanza);
				}
				else if(nomePersonaggio.equals("mago")) {
					String nomeAttrezzo = null;
					String pesoAttrezzo = null;
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione del personaggio."));
					presentazione = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("nome attrezzo."));
					nomeAttrezzo = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("Peso attrezzo"));
					pesoAttrezzo = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("Nome stanza"));
					nomeStanza = scannerLinea.next();
					posizionaMago(nomePersonaggio,presentazione, nomeAttrezzo, pesoAttrezzo, nomeStanza);
				}
				else if(nomePersonaggio.equals("strega")) {
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione del personaggio."));
					presentazione = scannerLinea.next();
					check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome della stanza in cui collocare"
							+ "il personaggio: " + nomePersonaggio));
					nomeStanza = scannerLinea.next();
					posizionaStrega(nomePersonaggio, presentazione, nomeStanza);
				}
			}
		}
	}

	private void posizionaStrega(String nome, String presentazione, String nomeStanza) throws FormatoFileNonValidoException{
		Strega strega = new Strega(nome, presentazione);
		check(isStanzaValida(nomeStanza), "Strega non posizionabile, stanza inesistente");
		this.nome2stanza.get(nomeStanza).setPersonaggio(strega);
		builder.getStanza(nomeStanza).setPersonaggio(strega);
	}

	private void posizionaMago(String nome, String presentazione, String nomeAttrezzo, String pesoAttrezzo, String nomeStanza)throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			Mago mago = new Mago(nome, presentazione, attrezzo);
			check(isStanzaValida(nomeStanza),"Mago non posizionabile, stanza inesistente");
			this.nome2stanza.get(nomeStanza).setPersonaggio(mago);
			builder.getStanza(nomeStanza).setPersonaggio(mago);
		} catch(NumberFormatException e) {
			check(false,"Peso attrezzo non valido");
		}
	}

	private void posizionaCane(String nome, String presentazione, String ciboPreferito, String nomeRegalo, String pesoRegalo, String nomeStanza)throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoRegalo);
			Attrezzo regalo = new Attrezzo(nomeRegalo, peso);
			Cane cane = new Cane(nome, presentazione, ciboPreferito, regalo);
			check(isStanzaValida(nomeStanza), "Cane non posizionabile, stanza inesistente");
			this.nome2stanza.get(nomeStanza).setPersonaggio(cane);
			builder.getStanza(nomeStanza).setPersonaggio(cane);
		} catch(NumberFormatException e) {
			check(false,"Peso attrezzo non valido");
		}
	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);
		for(String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
			try (Scanner scannerLinea = new Scanner(specificaAttrezzo)) {
				while(scannerLinea.hasNext()) {
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un attrezzo."));
					String nomeAttrezzo = scannerLinea.next();
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+"."));
					String pesoAttrezzo = scannerLinea.next();
					int peso = Integer.parseInt(pesoAttrezzo);
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo "+nomeAttrezzo+"."));
					String nomeStanza = scannerLinea.next();				
					posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
					builder.addAttrezzo(nomeAttrezzo, peso, nomeStanza);
				}
			}
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),"Attrezzo "+ nomeAttrezzo+" non collocabile: stanza " +nomeStanza+" inesistente");
			this.nome2stanza.get(nomeStanza).addAttrezzo(attrezzo);
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}

	private boolean isStanzaValida(String nomeStanza) {
		return this.nome2stanza.containsKey(nomeStanza);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		for(String specifica : separaStringheAlleVirgole(specificheUscite)) {
			try (Scanner scannerDiLinea = new Scanner(specifica)) {			
				while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("le uscite di una stanza."));
					String stanzaPartenza = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la direzione di una uscita della stanza "+stanzaPartenza));
					Direzione dir = Direzione.valueOf(scannerDiLinea.next().toUpperCase());
					check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la destinazione di una uscita della stanza "+stanzaPartenza+" nella direzione "+dir));
					String stanzaDestinazione = scannerDiLinea.next();		
					impostaUscita(stanzaPartenza, dir, stanzaDestinazione);
				}
			}
		} 
	}

	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere "+msg;
	}

	private void impostaUscita(String stanzaDa, Direzione dir, String nomeA) throws FormatoFileNonValidoException {
		check(isStanzaValida(stanzaDa),"Stanza di partenza sconosciuta "+dir);
		check(isStanzaValida(nomeA),"Stanza di destinazione sconosciuta "+ dir);
		Stanza partenzaDa = this.nome2stanza.get(stanzaDa);
		Stanza arrivoA = this.nome2stanza.get(nomeA);
		partenzaDa.impostaStanzaAdiacente(dir, arrivoA);
		builder.addAdiacenza(stanzaDa, nomeA, dir);
	}


	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore) throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException("Formato file non valido [" + this.reader.getLineNumber() + "] "+messaggioErrore);		
	}

	public Stanza getStanzaIniziale() {
		return this.stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}

	public Labirinto getLabirinto() {
		return builder.getLabirinto();
	}

	public LabirintoBuilder getLab() {
		return builder;
	}
}
