package ids.interfaccia;

import ids.BarcaCerrutoTraina.GestiMonSw;

public class ComandoDisponibilitaNulla implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Disponibilità nulla";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println(GestiMonSw.getInstance().creaDisponibilita("nulla"));
	}
}
