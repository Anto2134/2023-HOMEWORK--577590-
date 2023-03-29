package it.uniroma3.diadia;


import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	static final private String[] elencoComandi = {"vai", "aiuto", "fine","prendi attrezzo","posa attrezzo"};

	private Partita partita;
	private IOConsole io; 

	public DiaDia(IOConsole console) {
		this.partita = new Partita();
		this.io = console;
	}

	public void gioca() {
		String istruzione; 
		Scanner scannerDiLinee;

		io.mostraMessaggio(MESSAGGIO_BENVENUTO);	
		do	{	
			istruzione = io.leggiRiga();}
		while (!processaIstruzione(istruzione));
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire = new Comando(istruzione);

		String nome = comandoDaEseguire.getNome();

		if(nome!= null) {
			if (nome.equals("fine")) {
				this.fine(); 
				return true;
			}

			else 
				if (nome.equals("prendi"))
					this.prendi(comandoDaEseguire.getParametro());
				else 
					if (nome.equals("posa"))
						this.posa(comandoDaEseguire.getParametro());
					else 
						if (nome.equals("vai"))
							this.vai(comandoDaEseguire.getParametro());
						else 
							if (nome.equals("aiuto"))
								this.aiuto();
							else
								io.mostraMessaggio("Comando sconosciuto");
			if (this.partita.vinta()) {
				io.mostraMessaggio("Hai vinto!");
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}   

	// implementazioni dei comandi dell'utente:

	private void prendi(String nomeAttrezzo) {
		if(this.partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo)==null) 
			io.mostraMessaggio("Attrezzo non presente nella stanza");
		else {
			while(this.partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo)!=null) {
				boolean preso = false;
				Attrezzo attrezzoPreso = this.partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);
				this.partita.getLabirinto().getStanzaCorrente().removeAttrezzo(attrezzoPreso);
				this.partita.getGiocatore().getBorsa().addAttrezzo(attrezzoPreso);
				preso = true;
				if (preso)
					io.mostraMessaggio("Attrezzo:" + attrezzoPreso.getNome() + " preso");
				if (!preso)
					io.mostraMessaggio("Attrezzo non preso");
			}
			io.mostraMessaggio("Attrezzi nella borsa:\n");
			for(Attrezzo attrezzo : this.partita.getGiocatore().getBorsa().getAttrezzi()) {
				if(attrezzo!=null)
					io.mostraMessaggio(attrezzo.getNome());
			}
		}
	}

	private void posa(String nomeAttrezzo) {
		if(!this.partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo)) {
			this.io.mostraMessaggio("Attrezzo " + nomeAttrezzo + " non presente nella borsa");
			return;
		}
		Attrezzo attrezzoDaPosare = this.partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
		this.partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoDaPosare);
		this.io.mostraMessaggio("Attrezzo " + nomeAttrezzo + " posato!");

		
		for(Attrezzo attrezzo : this.partita.getGiocatore().getBorsa().getAttrezzi()) {
			if(attrezzo != null) {
				io.mostraMessaggio("Attrezzi nella borsa:\n");
				io.mostraMessaggio(attrezzo.getNome());
			}
		}
		if(this.partita.getGiocatore().getBorsa().isEmpty())
			io.mostraMessaggio("nessun attrezzo nella borsa");

	}

	private void aiuto() {
		for(int i=0; i< elencoComandi.length; i++) 
			io.mostraMessaggio(elencoComandi[i]+" ");
		io.mostraMessaggio("");
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if(direzione==null)
			io.mostraMessaggio("Dove vuoi andare ?");
		Stanza prossimaStanza=null;
		prossimaStanza = this.partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			io.mostraMessaggio("Direzione inesistente");
		else {
			this.partita.getLabirinto().setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getCfu();
			this.partita.setCfu(cfu--);
		}
		io.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		io.mostraMessaggio("Grazie di aver giocato!");  // si desidera smettere
	}

	public static void main(String[] argc) {
		IOConsole console = new IOConsole();
		DiaDia gioco = new DiaDia(console);
		gioco.gioca();

	}
}
