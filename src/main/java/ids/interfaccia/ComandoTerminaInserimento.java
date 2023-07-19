package ids.interfaccia;

public class ComandoTerminaInserimento implements Comando  {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Termina inserimento";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
	}
}
