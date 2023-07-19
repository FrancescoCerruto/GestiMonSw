package ids.interfaccia;

import ids.BarcaCerrutoTraina.RichiesteHandler;

public class ComandoRichiestaPermessi implements Comando  {
	public static final String codiceComando="8";
	public static final String descrizioneComando="Richiesta permessi";

	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci il codice fiscale");
		String codiceFiscale = Parser.getInstance().read();
		String result = RichiesteHandler.getInstance().richiestaPermessi(codiceFiscale);
		if (result.equals("")) {
			System.out.println("Non esiste ancora una turnazione settimanale - ritorno alla home page");
		} else {
			if (result.equals("Non puoi richiedere permessi")) {
				System.out.println("Non puoi richiedere permessi");					
			} else {
				if (result.equals("Non sei registrato")) {
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
					System.out.println(result);
					new ComandoSelezionaTurno().esegui();
				}
			}
		}
	}
}
