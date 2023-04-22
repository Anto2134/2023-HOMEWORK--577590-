package it.uniroma3.diadia.giocatore;

public class Giocatore {

	static final private int CFU_INIZIALI = 20;
	private int numCFU;
	private Borsa borsa;



	public Giocatore() {
		this.numCFU = CFU_INIZIALI;
		this.borsa = new Borsa();
	}

	public int getCfu() {         
		return this.numCFU;            
	}                            

	public void setCfu(int cfu) {
		this.numCFU = cfu;		           
	}

	public Borsa getBorsa() {
		return this.borsa;
	}

	public void setBorsa(Borsa borsa) {
		this.borsa = borsa;
	}

	public String toString() {
		return this.numCFU + this.borsa.toString();
	}
}	