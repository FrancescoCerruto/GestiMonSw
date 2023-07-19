package ids.interfaccia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoVisualizzaCarriera implements Comando {
	public static final String codiceComando="12";
	public static final String descrizioneComando="Visualizza carriera";
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Fornire l'identificativo di supervisore");
		String identificativoSupervisore = Parser.getInstance().read();
		
		Map<String, List<String>> result = DipendenteHandler.getInstance().visualizzaCarriera(identificativoSupervisore);
		if (result == null) {
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
		} else {
			List<String> keyList = new ArrayList<>(result.keySet());
			for (String s : keyList) {
				List<String> datiDipendente = new ArrayList<>(result.get(s));
				for (String d : datiDipendente) {				
					System.out.print(d + " ");
				}
				System.out.println();
			}
			new ComandoVediCarriera().esegui();
		}
	}
}
