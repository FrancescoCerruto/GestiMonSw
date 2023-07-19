package ids.interfaccia;

import java.util.List;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoVediCarriera implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Vedi carriera";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci codice fiscale dipendente");
		String codiceFiscale = Parser.getInstance().read();
		
		List<String> result = DipendenteHandler.getInstance().vediCarriera(codiceFiscale);
		if (result == null) {
			System.out.println("Codice fiscale non memorizzato");
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
			for (String s : result) {
				System.out.println(s);
			}
		}
	}
}
