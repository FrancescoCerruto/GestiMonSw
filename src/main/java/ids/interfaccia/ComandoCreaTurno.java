package ids.interfaccia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.TurniHandler;

public class ComandoCreaTurno implements Comando  {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Crea turno";
	
	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE_DIPENDENTE));
	}

	public String getCodiceComando() {
		return codiceComando;
	}

	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void esegui() {
		//creazione del turno
		System.out.println("   Data del turno (AAAA/MM/GG): ");
		String data = Parser.getInstance().read();
		
		String regexData = 	"^\\d{4}/\\d{2}/\\d{2}$";
		Pattern patternData = Pattern.compile(regexData);
		
		while (!patternData.matcher(data).matches()) {
			System.out.println("Data inserita non valida. Inserisci data del turno (AAAA/MM/DD)");
			data= Parser.getInstance().read();
		}
		
		System.out.println("Inserisci ora di inizio del turno (hh)");
		String ora = Parser.getInstance().read();
		
		String regexOra = 	"^\\d{2}";
		Pattern patternOra = Pattern.compile(regexOra);
		
		while (!patternOra.matcher(ora).matches()) {
			System.out.println("Ora di inizio del turno non valida. Inserisci ora di inizio del turno (hh)");
			ora = Parser.getInstance().read();
		}

		LocalDate day = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		
		try {
			TurniHandler.getInstance().creaTurno(day, Integer.parseInt(ora));
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE_DIPENDENTE);
			comando.esegui();
		} catch (Exception e) {
			System.out.println("Data inserita non rientra nella settimana in pianificazione");
			esegui();
		}
		
		
	}	
}
