package it.uniroma3.diadia;
import java.util.Scanner;

public class IOConsole implements IO {
	
	private Scanner scannerDiLinee;

	public void mostraMessaggio(String msg) {
		System.out.println(msg);
	}
	
	public String leggiRiga() {
		scannerDiLinee = new Scanner(System.in);
		String riga = scannerDiLinee.nextLine();
	    //scannerDiLinee.close();
		return riga;
	}
}