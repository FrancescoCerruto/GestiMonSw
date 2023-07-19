package ids.BarcaCerrutoTraina;

import java.util.ArrayList;
import java.util.List;

public class TurnazioneSettimanale {

	private List<TurnoLavorativo> turniLavorativi = new ArrayList<>();
	
	public TurnazioneSettimanale() {
		turniLavorativi.clear();
	}

	public void addTurno(TurnoLavorativo turnoLavorativo) {
		turniLavorativi.add(turnoLavorativo);
	}
	
	public String getDescrizione() {
		String descrizione = "";
		for (TurnoLavorativo tl : turniLavorativi) {
			descrizione+=tl.getDescrizioneTurno();
			descrizione+="\n";
		}
		return descrizione;
	}
	
	public void swapTurni(int idTurno1, int idTurno2) throws Exception {
		TurnoLavorativo tl1 = getTurnoById(idTurno1);
		TurnoLavorativo tl2 = getTurnoById(idTurno2);
		Dipendente dtl1 = tl1.getDipendente();
		Dipendente dtl2 = tl2.getDipendente();

		dtl1.removeTurno(tl1);
		dtl2.removeTurno(tl2);
		tl1.setDipendente(dtl2);
		tl2.setDipendente(dtl1);
		dtl1.addTurno(tl2);
		dtl2.addTurno(tl1);
	}
	
	public TurnoLavorativo getTurnoById(int id) throws Exception {
		for (TurnoLavorativo tl : turniLavorativi) {
			if (tl.getId() == id) {
				return tl;
			}
		}
		throw new Exception();
	}
	
	//metodi di test
	public List<TurnoLavorativo> getTurniLavorativi() {
		return turniLavorativi;
	}
}