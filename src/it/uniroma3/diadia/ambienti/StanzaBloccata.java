package it.uniroma3.diadia.ambienti;



public class StanzaBloccata extends Stanza {

	private String attrezzoSbloccante;
	private Direzione direzioneBloccata;

	public StanzaBloccata(String nome, String attrezzoSbloccante, Direzione direzioneBloccata) {
		super(nome);
		this.attrezzoSbloccante = attrezzoSbloccante;
		this.direzioneBloccata = direzioneBloccata;
	}

	@Override
	public Stanza getStanzaAdiacente(Direzione direzione) {
		if(direzione.equals(direzioneBloccata) && !super.hasAttrezzo(attrezzoSbloccante))
			return this;
		//if(this.hasAttrezzo(attrezzoSbloccante))
			//return this.getStanzaAdiacente(direzione);
		return super.getStanzaAdiacente(direzione);
	}
	
	public String getNomeAttrezzoSbloccante() {
		return this.attrezzoSbloccante;
	}
	
	/*@Override
	public Stanza getStanzaAdiacente(String direzione) {
		if(direzione.equals(this.direzioneBloccata) && !super.hasAttrezzo(attrezzoSbloccante))
			return this;
		return super.getStanzaAdiacente(direzione);
	}*/

	/*@Override
	public String getDescrizione() {
		String bloccata = "Stanza bloccata nella direzione: " + direzioneBloccata + "/n prendi il " + 
				attrezzoSbloccante + "e posalo nella stanza per sbloccarla";
		if(!this.hasAttrezzo(attrezzoSbloccante))
			return bloccata;
		return super.getDescrizione();
	}*/



}