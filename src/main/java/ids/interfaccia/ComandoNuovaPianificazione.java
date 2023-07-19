package ids.interfaccia;

import java.util.List;

import ids.BarcaCerrutoTraina.PianificazioneHandler;

public class ComandoNuovaPianificazione implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Nuova pianificazione";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserire identificativo supervisore");
		String identificativoSupervisore = Parser.getInstance().read();
		//recupero tipi pianificazione supportati
		List<String> tipiPianificazione = PianificazioneHandler.getInstance().pianificaTurnazioneSettimanale(identificativoSupervisore);
		if (tipiPianificazione != null) {
			for (String s : tipiPianificazione) {
				System.out.println((tipiPianificazione.indexOf(s) + 1) + ") " + s);
			}
			Comando comando = Parser.getInstance().getComando(ElencoComandi.NUOVA_PIANIFICAZIONE);
			comando.esegui();
		} else {
			System.out.println("Non sei autorizzato ad eseguire questa operazione");
			System.out.println("0)Esci");
			System.out.println("1)Ritenta");
			String response = Parser.getInstance().read();
			if (response.equals("0")) {
				new ComandoEsci().esegui();
			} else {
				if (response.equals("1")) {
					esegui();
				} else {
					new ComandoNonRiconosciuto().esegui();
				}
			}
		}		
	}
}
