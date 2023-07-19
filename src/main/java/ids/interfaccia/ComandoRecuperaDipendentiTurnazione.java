package ids.interfaccia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoRecuperaDipendentiTurnazione implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Recupera dipendenti";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE_TERMINA_TURNO));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		//recupero la lista dei dipendenti
		Map<String, List<String>> dipendenti = DipendenteHandler.getInstance().recuperaDipendentiTurnazione();
		List<String> keyList = new ArrayList<>(dipendenti.keySet());
		for (String s : keyList) {
			List<String> datiDipendente = new ArrayList<>(dipendenti.get(s));
			for (String d : datiDipendente) {				
				System.out.print(d + " ");
			}
			System.out.println();
		}
		
		stampaComandi();
		Comando comando = Parser.getInstance().getComando(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE_TERMINA_TURNO);
		comando.esegui();
	}
}
