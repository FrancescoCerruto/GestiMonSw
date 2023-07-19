package ids.interfaccia;

import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.TurniHandler;

public class ComandoSelezionaTurni implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Seleziona turni";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci l'id di un tuo turno");
		String idTurno1 = Parser.getInstance().read();
		
		String regexId = "\\d+";
		Pattern patternId = Pattern.compile(regexId);
		
		while (!patternId.matcher(idTurno1).matches()) {
			System.out.println("Identificativo inserito non valido. Inserisci l'id di un tuo turno");
			idTurno1 = Parser.getInstance().read();
		}
		
		System.out.println("Inserisci l'id di un turno disponibile");
		String idTurno2 = Parser.getInstance().read();
		
		while (!patternId.matcher(idTurno2).matches()) {
			System.out.println("Identificativo inserito non valido. Inserisci l'id di un turno disponibile");
			idTurno2 = Parser.getInstance().read();
		}
		
		try {
			TurniHandler.getInstance().selezionaTurni(Integer.parseInt(idTurno1), Integer.parseInt(idTurno2));
		} catch (NullPointerException e1) {
			System.out.println("Non c'è ancora una pianificazione settimanale - ritorno alla home page");
		} catch (IllegalArgumentException e) {
			System.out.println("Data del turno 2 inferiore ai 2 giorni richiesti di scarto");
			esegui();
		} catch (Exception e) {
			System.out.println("Non ci sono turni con identificativi validi - ritorno alla home page");
		}
	}
}
