package ids.interfaccia;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoMemorizzaDipendente implements Comando  {
	public static final String codiceComando="3";
	public static final String descrizioneComando="Memorizza dipendente";

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
		System.out.println("Fornire l'identificativo di supervisore");
		String identificativoSupervisore = Parser.getInstance().read();
		try {
			DipendenteHandler.getInstance().memorizzaDatiDipendente(identificativoSupervisore);
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.MEMORIZZA_DIPENDENTE);
			comando.esegui();
		} catch (Exception e) {
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
