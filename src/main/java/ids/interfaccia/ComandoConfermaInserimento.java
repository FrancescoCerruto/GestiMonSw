package ids.interfaccia;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoConfermaInserimento implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Conferma inserimento";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.MEMORIZZA_DIPENDENTE));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		DipendenteHandler.getInstance().confermaInserimento();

		stampaComandi();
		Comando comando = Parser.getInstance().getComando(ElencoComandi.MEMORIZZA_DIPENDENTE);
		comando.esegui();
	}
}
