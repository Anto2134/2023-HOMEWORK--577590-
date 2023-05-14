package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;


public class StanzaMagica extends Stanza {
	private final static int SOGLIA_MAGICA = 3;
	private int contaAttrezziPosati;
	private int sogliaMagica;
	
	public StanzaMagica(String nome) {
		this(nome,SOGLIA_MAGICA);
	}
	
	public StanzaMagica(String nome, int soglia) {
		super(nome);
		this.contaAttrezziPosati = 0;
		this.sogliaMagica = soglia;
	}
	
	public Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
		StringBuilder nomeInvertito;
		int pesoX2 = attrezzo.getPeso() * 2;
		nomeInvertito = new StringBuilder(attrezzo.getNome());
		nomeInvertito = nomeInvertito.reverse();
		attrezzo = new Attrezzo(nomeInvertito.toString(), pesoX2);
		return attrezzo;
	}
	
	@Override
	public boolean addAttrezzo(Attrezzo attrezzo) {
		this.contaAttrezziPosati++;
		if(this.contaAttrezziPosati > this.sogliaMagica) 
			attrezzo = modificaAttrezzo(attrezzo);
		return super.addAttrezzo(attrezzo);
	}

	public boolean isMagica() {
		return true;
	}
	
}