package ids.interfaccia;

import ids.BarcaCerrutoTraina.MansioniHandler;

public class ComandoPianificaMansioni implements Comando {
	public static final String codiceComando="9";
	public static final String descrizioneComando="Pianifica mansioni";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserire identificativo supervisore");
		String idSupervisore = Parser.getInstance().read();
		try {
			MansioniHandler.getInstance().pianificaMansioni(idSupervisore);
			new ComandoCaricaFile().esegui();
		} catch (Exception e) {
			System.out.println("ComandoPianificaMansioni Non sei autorizzato ad eseguire questa operazione");
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
