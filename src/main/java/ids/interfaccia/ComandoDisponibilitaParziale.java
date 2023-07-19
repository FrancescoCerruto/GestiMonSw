package ids.interfaccia;

import ids.BarcaCerrutoTraina.GestiMonSw;

public class ComandoDisponibilitaParziale implements Comando  {
	public static final String codiceComando="3";
	public static final String descrizioneComando="Disponibilità parziale";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		String response = GestiMonSw.getInstance().creaDisponibilita("parziale");
		if (response.equals("")) {
			System.out.println("Non è stata ancora creata una pianificazione settimanale - ritorno alla home page");
		} else {
			System.out.println(response);
			new ComandoIndicaTurno().esegui();
		}
	}
}
