package ids.interfaccia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.RichiesteHandler;

public class ComandoIndicaGiornoFerie implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Indica giorno ferie";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.RICHIEDI_FERIE));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Data del giorno di ferie (AAAA/MM/GG): ");
		String data = Parser.getInstance().read();
		
		String regexData = 	"^\\d{4}/\\d{2}/\\d{2}$";
		Pattern patternData = Pattern.compile(regexData);
		
		while (!patternData.matcher(data).matches()) {
			System.out.println("Data inserita non valida. Inserisci data del turno (AAAA/MM/DD)");
			data= Parser.getInstance().read();
		}
		
		LocalDate giorno = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		
		try {
			RichiesteHandler.getInstance().indicaGiornoFerie(giorno);
			System.out.println("Giorno di ferie aggiunto");
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.RICHIEDI_FERIE);
			comando.esegui();
		} catch (IllegalArgumentException e1) {
			System.out.println("Giorno di ferie non valido");
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.RICHIEDI_FERIE);
			comando.esegui();
		} catch (IndexOutOfBoundsException e1) {
			System.out.println("Hai esaurito il numero di giorni di ferie per la settimana richiesta");
		} catch (Exception e1) {
			System.out.println("Non puoi richiedere giorni di ferie");
		}		
	}
}
