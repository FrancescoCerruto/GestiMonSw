package ids.interfaccia;

import ids.BarcaCerrutoTraina.GestiMonSw;

public class ComandoDisponibilitaTotale implements Comando  {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Disponibilità totale";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println(GestiMonSw.getInstance().creaDisponibilita("totale"));
	}
}
