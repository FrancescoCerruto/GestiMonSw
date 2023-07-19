package ids.interfaccia;

public class ComandoNonRiconosciuto implements Comando {
	public static final String codiceComando="-1";
	public static final String descrizioneComando="Comando non riconosciuto";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Comando non riconosciuto - ritorno alla homepage");
	}
}
