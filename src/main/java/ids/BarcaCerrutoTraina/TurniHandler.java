package ids.BarcaCerrutoTraina;

import java.time.LocalDate;
import java.util.Map;

public class TurniHandler {
	private static TurniHandler turniHandler = new TurniHandler();
	
	private TurniHandler() {}
	
	public static TurniHandler getInstance() {
		return turniHandler;
	}
	
	public void creaTurno(LocalDate data, int oraInizio) throws Exception {
		TurnazioneModifier.getInstance().inserisciTurno(data, oraInizio);
	}
	
	public String scegliDipendente(String codiceFiscale) throws Exception {
		if (GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			return TurnazioneModifier.getInstance().valorizzaTurno(codiceFiscale);			
		}
		throw new Exception();
	}
	
	public String scegliTurni(int idTurno1, int idTurno2) throws Exception {
		TurnazioneModifier.getInstance().scambiaTurni(idTurno1, idTurno2);
		return GestiMonSw.getInstance().getDescrizionePianificazione();
	}
	
	public void selezionaTurni(int idTurno1, int idTurno2) throws IllegalArgumentException, Exception {
		if (TurnazioneModifier.getInstance().tl1Beforetl2TwoDays(idTurno1, idTurno2)) {
			if (GestiMonSw.getInstance().checkEsistenzaTurnoCambio(idTurno2)) {
				if (GestiMonSw.getInstance().checkEsistenzaTurnoRichiestoCambio(idTurno1)) {
					TurnazioneModifier.getInstance().scambiaTurni(idTurno1, idTurno2);
					GestiMonSw.getInstance().decrementaCambiDipendente();												
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public Map<Integer, String> richiestaCambioTurno(String codiceFiscale) throws Exception {
		if (!GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			throw new Exception();
		}
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(codiceFiscale);
		return GestiMonSw.getInstance().checkDisponibilitaCambio(codiceFiscale);
	}
}
