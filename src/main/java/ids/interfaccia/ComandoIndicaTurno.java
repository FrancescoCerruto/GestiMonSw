package ids.interfaccia;

import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.DisponibilitaHandler;

public class ComandoIndicaTurno implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Indica turno";
	
	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.INDICA_TURNO));
	}
	
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
		
		System.out.println(DisponibilitaHandler.getInstance().indicaTurno(Integer.parseInt(idTurno1)));
		stampaComandi();
		Comando comando = Parser.getInstance().getComando(ElencoComandi.INDICA_TURNO);
		comando.esegui();
	}
}
