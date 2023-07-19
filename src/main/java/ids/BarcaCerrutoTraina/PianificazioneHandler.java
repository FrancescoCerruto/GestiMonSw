package ids.BarcaCerrutoTraina;

import java.util.List;

public class PianificazioneHandler {

	private static PianificazioneHandler pianificazioneHandler = new PianificazioneHandler();

	private PianificazioneHandler() {}

	public static PianificazioneHandler getInstance() {
		return pianificazioneHandler;
	}

	public List<String> pianificaTurnazioneSettimanale(String idSupervisore) {
		String idAutorizzato = GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato();
		if (idAutorizzato.equals(idSupervisore)) {
			return GestiMonSw.getInstance().getTipiPianificazione();			
		}
		return null;
	}

	public void pianificazioneManuale() throws Exception {
		GestiMonSw.getInstance().creaTurnazioneSettimanale();
	}
	
	public String pianificazioneAutomatica() throws Exception {
		GestiMonSw.getInstance().creaTurnazioneSettimanale();
		TurnazioneModifier.getInstance().inserisciTurniAutomatici();
		GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		return GestiMonSw.getInstance().getDescrizionePianificazione();
	}

	public void terminaPianificazione() throws Exception {
		GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
	}
	
	public String modificaPianificazione(String idSupervisore) {
		String idAutorizzato = GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato();
		if (idAutorizzato.equals(idSupervisore)) {
			return GestiMonSw.getInstance().getDescrizionePianificazione();
		}
		return "";
	}
}
