package ids.interfaccia;

import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.RichiesteHandler;

public class ComandoSelezionaTurno implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Seleziona turno";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci id del turno");
		String id = Parser.getInstance().read();
		
		String idRegex = "\\d+";
		Pattern idPattern = Pattern.compile(idRegex);
		while (!idPattern.matcher(id).matches()) {
			System.out.println("Id fornito non valido - Inserisci id del turno");
			id = Parser.getInstance().read();
		}
		
		try {
			RichiesteHandler.getInstance().selezionaTurno(Integer.parseInt(id));
			System.out.println("Permesso espresso");
		} catch (IllegalArgumentException e) {
			System.out.println("Non ci sono abbastanza persone a lavoro per il turno richiesto");
			esegui();
		} catch (NullPointerException e) {
			System.out.println("L'id fornito non identifica alcun turno");
			esegui();
		} catch (Exception e) {
			System.out.println("Il turno richiesto non è valido");
			esegui();
		}
	}
}
