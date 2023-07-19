package ids.interfaccia;

import ids.BarcaCerrutoTraina.PianificazioneHandler;

public class ComandoPianificazioneAutomatica implements Comando  {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Pianificazione automatica";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.NUOVA_PIANIFICAZIONE_AUTOMATICA));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}

	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void esegui() {		
		try {
			System.out.println(PianificazioneHandler.getInstance().pianificazioneAutomatica());
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.NUOVA_PIANIFICAZIONE_AUTOMATICA);
			comando.esegui();
		} catch (Exception e) {
			System.out.println("Non è stato possibile creare una nuova pianificazione (numero di dipendenti inferiore al minimo richiesto - ritorno alla home page");
		}
	}
}