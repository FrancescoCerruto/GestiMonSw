package ids.BarcaCerrutoTraina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MansioniCreator {

	private static MansioniCreator mansioniCreator = new MansioniCreator();
	
	private MansioniCreator() {}

	public static MansioniCreator getInstance() {
		return mansioniCreator;
	}

	public Boolean verificaEsistenzaFile(String path) {
		return new File(path).exists();
	}

	public Boolean verificaValiditaFile(String path) {
		BufferedReader reader;
		int punteggioRichiesto = GestiMonSw.getInstance().getNumeroDipendenti() * Dipendente.caricoLavorativo * 3 / 2;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while (line != null) {
				if (!((line.charAt(0) >= 'a' && line.charAt(0) <= 'z') &&
						(line.charAt(1) == '\t') &&
						(line.charAt(2) >= '5' && line.charAt(2) <= '7') &&
						(line.charAt(3) == '0'))) {
					reader.close();
					return false;
				}
				
				String linePunteggio = String.valueOf(line.charAt(2)) + line.charAt(3);
				
				int punteggio = Integer.parseInt(linePunteggio);
				
				punteggioRichiesto -= punteggio;
				line = reader.readLine();
			}
			reader.close();
			if (punteggioRichiesto > 0) {
				return false;
			} else {
				return true;				
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void creaMansioni(String path) {
		GestiMonSw.getInstance().creaRegistroMansioni();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			int id = GestiMonSw.getInstance().getNumeroMansioni() + 1;
			while (line != null) {
				String descrizione = String.valueOf(line.charAt(0));

				String linePunteggio = String.valueOf(line.charAt(2)) + line.charAt(3);
				
				int punteggio = Integer.parseInt(linePunteggio);
				
				Mansione mansione = new Mansione(descrizione, punteggio, id);
				GestiMonSw.getInstance().addMansione(mansione);
				line = reader.readLine();
				id++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
