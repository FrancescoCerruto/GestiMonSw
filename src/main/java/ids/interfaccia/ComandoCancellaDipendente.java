package ids.interfaccia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoCancellaDipendente implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Cancella dipendente";
	
	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.RIMUOVI_DIPENDENTE));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci codice fiscale");
		String codiceFiscale = Parser.getInstance().read();
		try {
			DipendenteHandler.getInstance().cancellaDipendente(codiceFiscale);
		} catch (Exception e) {
			System.out.println("Il dipendente richiesto non esiste");
		}
		
		Map<String, List<String>> dipendenti = DipendenteHandler.getInstance().recuperaDipendenti();
		List<String> keyList = new ArrayList<>(dipendenti.keySet());
		for (String s : keyList) {
			List<String> datiDipendente = new ArrayList<>(dipendenti.get(s));
			for (String d : datiDipendente) {				
				System.out.print(d + " ");
			}
			System.out.println();
		}
		stampaComandi();
		Comando comando = Parser.getInstance().getComando(ElencoComandi.RIMUOVI_DIPENDENTE);
		comando.esegui();
	}
}
