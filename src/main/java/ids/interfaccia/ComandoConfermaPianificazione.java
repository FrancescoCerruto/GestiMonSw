package ids.interfaccia;

import ids.BarcaCerrutoTraina.PianificazioneHandler;

public class ComandoConfermaPianificazione implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Conferma pianificazione";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		try {
			PianificazioneHandler.getInstance().terminaPianificazione();
		} catch (Exception e) {
			System.out.println("Ci sono degli errori nella pianificazione creata e non è stato possibile memorizzarla - torno alla home page");
		}
	}
}
