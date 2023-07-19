package ids.interfaccia;

import ids.BarcaCerrutoTraina.TurniHandler;

public class ComandoScegliDipendente implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Scegli dipendente";

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
		System.out.println("Inserisci codice fiscale");
		String codiceFiscale = Parser.getInstance().read();
		try {
			System.out.println(TurniHandler.getInstance().scegliDipendente(codiceFiscale));
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE);
			comando.esegui();
		} catch (Exception e) {
			System.out.println("Codice fiscale fornito non memorizzato");
			esegui();
		}

	}
}
