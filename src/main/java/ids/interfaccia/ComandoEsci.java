package ids.interfaccia;

public class ComandoEsci implements Comando  {
	public static final String codiceComando="0";
	public static final String descrizioneComando="Esci";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		//termina l'applicazione
	}
}
