package ids.interfaccia;

import ids.BarcaCerrutoTraina.PianificazioneHandler;

public class ComandoModificaPianificazione implements Comando  {
	public static final String codiceComando="2";
	public static final String descrizioneComando="Modifica pianificazione";
	
	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.MODIFICA_PIANIFICAZIONE));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserire identificativo supervisore");
		String identificativoSupervisore = Parser.getInstance().read();
		String response = PianificazioneHandler.getInstance().modificaPianificazione(identificativoSupervisore);
		System.out.println(response);
		if (response.equals("")) {
			System.out.println("Non sei autorizzato ad eseguire questa operazione");
			System.out.println("0)Esci");
			System.out.println("1)Ritenta");
			String responseError = Parser.getInstance().read();
			if (responseError.equals("0")) {
				new ComandoEsci().esegui();
			} else {
				if (responseError.equals("1")) {
					esegui();
				} else {
					new ComandoNonRiconosciuto().esegui();
				}
			}
		} else {
			if (!response.equals("Non esiste ancora una pianificazione settimanale")) {
				stampaComandi();
				Comando comando = Parser.getInstance().getComando(ElencoComandi.MODIFICA_PIANIFICAZIONE);
				comando.esegui();
			}
		}		
	}
}
