package ids.interfaccia;

public class ComandoTerminaRimozione implements Comando  {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Termina rimozione";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		
	}
}
