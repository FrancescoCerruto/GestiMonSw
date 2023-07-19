package ids.BarcaCerrutoTraina;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoricoCarriere {
	Map<String, Map<LocalDate, List<String>>> storico = new HashMap<>();
	
	public StoricoCarriere() {
		storico.clear();
	}
	
	public void aggiornaStorico(String codiceFiscale, String motivazione) {
		if (!storico.containsKey(codiceFiscale)) {
			storico.put(codiceFiscale, (Map<LocalDate, List<String>>) new HashMap<LocalDate, List<String>>());
			storico.get(codiceFiscale).put(LocalDate.now(), new ArrayList<>());
			storico.get(codiceFiscale).get(LocalDate.now()).add(motivazione);
		} else {
			if (!storico.get(codiceFiscale).containsKey(LocalDate.now())) {
				storico.get(codiceFiscale).put(LocalDate.now(), new ArrayList<>());
				storico.get(codiceFiscale).get(LocalDate.now()).add(motivazione);
			} else {
				storico.get(codiceFiscale).get(LocalDate.now()).add(motivazione);
			}
		}
	}

	public Map<LocalDate, List<String>> getStorico(String codiceFiscale) {
		return storico.get(codiceFiscale);
	}
	
	//metodi test
	public Map<String, Map<LocalDate, List<String>>> getMap() {
		return storico;
	}
}
