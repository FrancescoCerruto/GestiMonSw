package ids.interfaccia;

import ids.BarcaCerrutoTraina.GestiMonSw;

public class ComandoIndicaDisponibilitaSettimanale implements Comando  {
	public static final String codiceComando="6";
	public static final String descrizioneComando="Indica disponibilità settimanale";
	
	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.OPZIONI_DISPONIBILITA));
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
		if (GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(codiceFiscale);
			System.out.println("Seleziona una delle 3 opzioni di disponibilità");
			stampaComandi();
			Comando comando = Parser.getInstance().getComando(ElencoComandi.OPZIONI_DISPONIBILITA);
			comando.esegui();
		} else {
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
