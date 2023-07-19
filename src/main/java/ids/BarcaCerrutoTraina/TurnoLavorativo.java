package ids.BarcaCerrutoTraina;

import java.time.LocalDate;
import java.util.List;

public class TurnoLavorativo {
	private int id;
	
	private LocalDate giorno;
	
	private int oraInizio;

	private Dipendente dipendente = null;
	
	public TurnoLavorativo(LocalDate giorno, int oraInizio, int id) {
		this.giorno = giorno;
		this.oraInizio = oraInizio;
		this.id = id;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}
	
	public String getDescrizioneTurno() {
		String descrizione = "Id del turno: " + id + "\n";
		descrizione += "Giorno del turno: " + giorno.getDayOfMonth() + "/" + giorno.getMonthValue() + "/" + giorno.getYear();
		descrizione += "\nOrario di inizio: " + oraInizio;
		descrizione += "\nOrario di fine: " + (oraInizio + 2) + ":00";
		List<String> dati = dipendente.getDati();
		descrizione += "\nDipendente: " + dati.get(0) + " " + dati.get(1);
		return descrizione;
	}

	public int getId() {
		return id;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}
	
	public LocalDate getData() {
		return giorno;
	}

	public boolean isPermesso(LocalDate dataTurno, int oraInizio) {
		if (giorno.equals(dataTurno) && this.oraInizio == oraInizio) {
			return true;
		}
		return false;
	}
	
	//metodi di test
	@Override
	public boolean equals(Object tl) {
		if (this == tl) {
			return true;
		}
		if (tl == null || tl.getClass() != getClass()) {
			return false;
		}
		TurnoLavorativo tmp = (TurnoLavorativo) tl;
		if (this.giorno.equals(tmp.giorno) && 
				this.oraInizio == tmp.oraInizio &&
				this.id == tmp.id &&
				this.dipendente.equals(tmp.getDipendente())) {
			return true;
		}
		return false;
	}

	public int getOraInizio() {
		return oraInizio;
	}

	public void setData(LocalDate plusDays) {
		giorno = plusDays;
	}
}
