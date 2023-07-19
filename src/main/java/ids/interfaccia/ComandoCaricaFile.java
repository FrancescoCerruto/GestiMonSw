package ids.interfaccia;

import java.io.FileNotFoundException;

import ids.BarcaCerrutoTraina.Dipendente;
import ids.BarcaCerrutoTraina.GestiMonSw;
import ids.BarcaCerrutoTraina.MansioniHandler;
import ids.Util.FileCreator;

public class ComandoCaricaFile implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Carica file";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserire file path");
		String path = Parser.getInstance().read();
		
		//utility di test
		int punteggioRichiesto = GestiMonSw.getInstance().getNumeroDipendenti() * Dipendente.caricoLavorativo * 3 / 2;
		FileCreator.createMansioniFile(punteggioRichiesto);
		
		try {
			MansioniHandler.getInstance().caricaFile(path);
			System.out.println("Mansioni create");
		} catch (FileNotFoundException e) {
			System.out.println("Il file richiesto non esiste");
		} catch (IllegalArgumentException e) {
			System.out.println("Il file fornito non rispetta i requisiti di creazione");
		}
	}
}
