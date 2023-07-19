package ids.interfaccia;

import ids.BarcaCerrutoTraina.PianificazioneHandler;

public class ComandoPianificazioneManuale implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Pianificazione manuale";
	
	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE));
	}

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		//creo la pianificazione
		try {
			PianificazioneHandler.getInstance().pianificazioneManuale();
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE);
			comando.esegui();
		} catch (Exception e) {
			System.out.println("Numero dipendenti insufficiente - torno alla home page");
		}
	}
}
