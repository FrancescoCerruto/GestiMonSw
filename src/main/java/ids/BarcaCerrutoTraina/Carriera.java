package ids.BarcaCerrutoTraina;

import java.time.LocalDate;
import java.util.List;

public class Carriera {
	private LocalDate dataAssunzione;

	private LocalDate dataScadenzaContratto;

	private int stipendioBase;
	
	private int stipendioAttuale;
	
	private int noteMerito = 0;
	
	private int noteDemerito = 0;
	
	private Dipendente dipendente;

	public Carriera(LocalDate dataAssunzione, LocalDate dataScadenzaContratto, int stipendio, Dipendente dipendente) {
		this.dataAssunzione = dataAssunzione;
		this.dataScadenzaContratto = dataScadenzaContratto;
		this.stipendioBase = stipendio;
		this.stipendioAttuale = stipendio;
		this.dipendente = dipendente;
	}

	public LocalDate getDataAssunzione() {
		return dataAssunzione;
	}

	public LocalDate getDataScadenzaContratto() {
		return dataScadenzaContratto;
	}

	public int getStipendio() {
		return stipendioAttuale;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void addNota(int tipoNota, int numero) {
		if (tipoNota == 1) {
			noteMerito += numero;
		} else {
			if (tipoNota == 2) {
				noteDemerito += numero;
			}
		}
		aggiornaStipendio();
	}
	
	public void aggiornaStipendio() {
		int noteMeritoTmp = noteMerito;
		int noteDemeritoTmp = noteDemerito; 
		stipendioAttuale = stipendioBase;
		while (noteMeritoTmp >= 4) {
			stipendioAttuale = stipendioAttuale + (stipendioAttuale * 20 / 100);
			noteMeritoTmp -= 4;
		}
		while (noteDemeritoTmp >= 4) {
			stipendioAttuale = stipendioAttuale - (stipendioAttuale * 10 / 100);
			if (stipendioAttuale < 0) {
				stipendioAttuale = 0;
			}
			noteDemeritoTmp -= 4;
		}
	}

	public List<String> getDati() {
		return List.of(dataAssunzione.toString(), dataScadenzaContratto.toString(), Integer.toString(stipendioAttuale), Integer.toString(noteMerito), Integer.toString(noteDemerito));
	}
	
	//metodi test
	@Override
	public boolean equals(Object c) {
		if (this == c) {
			return true;
		}
		if (c == null || c.getClass() != getClass()) {
			return false;
		}
		Carriera tmp = (Carriera) c;
		if (this.dataAssunzione.equals(tmp.dataAssunzione) && 
				this.dataScadenzaContratto.equals(tmp.dataScadenzaContratto) &&
				this.stipendioAttuale == tmp.stipendioAttuale &&
				this.dipendente.getDati().get(0).equals(tmp.getDipendente().getDati().get(0)) &&
				this.dipendente.getDati().get(1).equals(tmp.getDipendente().getDati().get(1)) &&
				this.dipendente.getDati().get(2).equals(tmp.getDipendente().getDati().get(2)) &&
				this.dipendente.getDati().get(3).equals(tmp.getDipendente().getDati().get(3)) &&
				this.dipendente.getDati().get(4).equals(tmp.getDipendente().getDati().get(4))
				) {
			return true;
		}
		return false;
	}
}
