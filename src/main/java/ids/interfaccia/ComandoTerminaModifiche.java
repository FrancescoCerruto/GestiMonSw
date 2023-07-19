package ids.interfaccia;

import ids.BarcaCerrutoTraina.PianificazioneHandler;

public class ComandoTerminaModifiche implements Comando {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Termina modifiche";
	
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
			System.out.println("Ci sono errori nella pianificazione creata e non è stato possibile memorizzarla - ritorno alla home page");
		}
	}
}
