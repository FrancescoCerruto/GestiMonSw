package ids.BarcaCerrutoTraina;

import java.util.List;

public class Mansione {
	private int id;
	
	private String descrizione;

	private int punteggio;
	
	Mansione(String descrizione, int punteggio, int id) {
		this.descrizione = descrizione;
		this.punteggio = punteggio;
		this.id = id;
	}

	public List<String> getDati() {
		return List.of(Integer.toString(id), ", ", descrizione, ", ", Integer.toString(punteggio), "\n");
	}

	public int getId() {
		return id;
	}

	public int getPunteggio() {
		return punteggio;
	}

}
