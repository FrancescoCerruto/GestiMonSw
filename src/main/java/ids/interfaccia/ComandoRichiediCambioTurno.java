package ids.interfaccia;

import java.util.Map;

import ids.BarcaCerrutoTraina.TurniHandler;

public class ComandoRichiediCambioTurno implements Comando  {
	public static final String codiceComando="5";
	public static final String descrizioneComando="Richiedi cambio turno";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserire il codice fiscale");
		String codiceFiscale = Parser.getInstance().read();
		try {
			Map<Integer, String> listaTurni = TurniHandler.getInstance().richiestaCambioTurno(codiceFiscale);
			if (listaTurni != null) {
				if (!listaTurni.get(1).equals("")) {
					System.out.println("/*****************I TUOI TURNI****************/");
					System.out.println(listaTurni.get(1));
					System.out.println("/*****************I TURNI CON CUI PUOI CAMBIARE****************/");
					System.out.println(listaTurni.get(2));
					if (listaTurni.get(2).equals("")) {
						System.out.println("/*********************** NESSUN DIPENDENTE HA ANCORA ESPRESSO LA DISPONIBILITA - ritorno alla home page ********************/");
					} else {
						new ComandoSelezionaTurni().esegui();
					}
				} else {
					System.out.println("Non è stata ancora creata una turnazione settimanale - ritorno alla home page");
				}
			} else {
				System.out.println("Hai esaurito il numero di cambi a disposizione");
			}
		} catch (Exception e) {
			System.out.println("Il codice fiscale fornito non è memorizzato");
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
