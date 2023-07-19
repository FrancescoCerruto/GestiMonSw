package ids.interfaccia;

import ids.BarcaCerrutoTraina.RichiesteHandler;

public class ComandoRichiestaFerie implements Comando  {
	public static final String codiceComando="7";
	public static final String descrizioneComando="Richiesta ferie";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.RICHIEDI_FERIE));
	}

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci il codice fiscale");
		String codiceFiscale = Parser.getInstance().read();
		try {
			if (RichiesteHandler.getInstance().richiestaFerie(codiceFiscale)) {
				stampaComandi();
				Comando comando = Parser.getInstance().getComando(ElencoComandi.RICHIEDI_FERIE);
				comando.esegui();
			} else {
				System.out.println("Non puoi richiedere un giorno di ferie");
			}
		} catch (Exception e) {
			System.out.println("Codice fiscale fornito non memorizzato");
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
