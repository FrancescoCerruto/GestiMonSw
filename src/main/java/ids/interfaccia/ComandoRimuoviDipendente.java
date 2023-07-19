package ids.interfaccia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoRimuoviDipendente implements Comando  {
	public static final String codiceComando="4";
	public static final String descrizioneComando="Rimuovi dipendente";

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
		System.out.println("Fornire l'identificativo di supervisore");
		String identificativoSupervisore = Parser.getInstance().read();
		try {
			Map<String, List<String>> dipendenti = DipendenteHandler.getInstance().rimuoviDipendente(identificativoSupervisore);
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
		} catch (Exception e) {
			System.out.println("Non sei autorizzato ad eseguire questa operazione");
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
