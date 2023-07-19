package ids.interfaccia;

import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoScegliCarriera implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Scegli carriera";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci codice fiscale dipendente");
		String codiceFiscale = Parser.getInstance().read();
		
		System.out.println("Inserisci il tipo di nota (1) merito; 2) demerito)");
		String tipo = Parser.getInstance().read();
		
		while (!tipo.equals("1") && !tipo.equals("2")) {
			System.out.println("Tipo richiesto non riconosciuto. Inserisci il tipo di nota (1: merito; 2) demerito)");
			tipo = Parser.getInstance().read();
		}

		System.out.println("Inserisci il numero di note)");
		String numero = Parser.getInstance().read();
		
		String numeroRegex = "\\d+";
		Pattern numeroPattern = Pattern.compile(numeroRegex);
		
		while (!numeroPattern.matcher(numero).matches()) {
			System.out.println("Numero non valido. Inserisci il numero di note)");
			numero = Parser.getInstance().read();
		}
		
		int numeroInt = Integer.parseInt(numero);
		
		int tipoNotaInt = Integer.parseInt(tipo);
		
		try {
			DipendenteHandler.getInstance().scegliCarriera(codiceFiscale, tipoNotaInt, numeroInt);
			System.out.println("Carriera modificata");
		} catch (NullPointerException e) {
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
