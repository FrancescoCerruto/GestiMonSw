package ids.BarcaCerrutoTraina;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistroMansioni {

	private List<Mansione> mansioni = new ArrayList<>();
	
	public RegistroMansioni() {
		mansioni.clear();
	}

	public void memorizzaMansione(Mansione mansione) {
		mansioni.add(mansione);
	}

	public String getListaMansioni() {
		String descrizione = "";
		for (Mansione m : mansioni) {
			for (String s : m.getDati()) {
				descrizione += s;
				
			}
		}
		return descrizione;
	}

	public int getPunteggio(int idMansione) {
		Optional<Mansione> tmp = mansioni.stream()
				.filter(m -> m.getId() == idMansione)
				.findAny();
		if (tmp.isPresent()) {
			return tmp.get().getPunteggio();
		}
		return -1;
	}

	public void rimuoviMansioneDisponibile(int idMansione) {
		int index = -1;
		for (Mansione m : mansioni) {
			if (m.getId() == idMansione) {
				index = mansioni.indexOf(m);
			}
		}
		mansioni.remove(index);
	}

	public int getLastId() {
		if (mansioni.size() == 0) {
			return 0;
		} else {
			return mansioni.get(mansioni.size() - 1).getId();			
		}
	}
	
	//metodi di test
	public List<Mansione> getMansioni() {
		return mansioni;
	}
}
