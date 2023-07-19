package ids.interfaccia;

import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.MansioniHandler;

public class ComandoSelezionaMansione implements Comando {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Seleziona mansione";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserire id mansione");
		String idMansione = Parser.getInstance().read();
		
		String idRegex = "\\d+";
		Pattern idPattern = Pattern.compile(idRegex);
		
		while (!idPattern.matcher(idMansione).matches()) {
			System.out.println("Id mansioni inserito non valido. Inserire id mansione");
			idMansione = Parser.getInstance().read();
		}
		
		int caricoRimasto = MansioniHandler.getInstance().selezionaMansione(Integer.parseInt(idMansione));
		if (caricoRimasto == Integer.MIN_VALUE) {
			System.out.println("Mansione inesistente");
			esegui();
		} else {
			System.out.println(caricoRimasto);
			System.out.println(MansioniHandler.getInstance().recuperaMansioni());
			if (caricoRimasto > 0) {
				esegui();
			}
		}
	}
}
