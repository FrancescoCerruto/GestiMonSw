package ids.interfaccia;

import ids.BarcaCerrutoTraina.MansioniHandler;

public class ComandoScegliMansione implements Comando {
	public static final String codiceComando="10";
	public static final String descrizioneComando="Scegli mansione";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci il codice fiscale");
		String codiceFiscale = Parser.getInstance().read();
		String result = MansioniHandler.getInstance().scegliMansione(codiceFiscale);
		if (result.equals("Non sei autorizzato ad eseguire questa operazione")) {
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
		} else {
			if (result.equals("Non ci sono ancora mansioni caricate")) {
				System.out.println("Non ci sono ancora mansioni caricate - ritorno alla home page");
			} else {
				System.out.println(result);
				new ComandoSelezionaMansione().esegui();
			}
		}
	}
}
