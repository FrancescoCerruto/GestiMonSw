package ids.BarcaCerrutoTraina;

import java.util.List;

public class DisponibilitaHandler {
	private static DisponibilitaHandler disponibilitaHandler = new DisponibilitaHandler();
	
	private DisponibilitaHandler() {}
	
	public static DisponibilitaHandler getInstance() {
		return disponibilitaHandler;
	}
	
	public List<String> indicaDisponibilitaSettimanale(String codiceFiscale) throws Exception {
		if (!GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			throw new Exception();
		}
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(codiceFiscale);
		return GestiMonSw.getInstance().getTipoDisponibilita();
	}
	
	public String disponibilitaNulla() {
		return GestiMonSw.getInstance().creaDisponibilita("nulla");
	}
	
	public String disponibilitaTotale() {
		return GestiMonSw.getInstance().creaDisponibilita("totale");
	}
	
	public String disponibilitaParziale() {
		return GestiMonSw.getInstance().creaDisponibilita("parziale");
	}
	
	public String indicaTurno(int idTurno) {
		return GestiMonSw.getInstance().addTurnoDisponibilita(idTurno);
	}
}
