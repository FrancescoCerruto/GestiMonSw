package ids.BarcaCerrutoTraina;

import java.time.LocalDate;

public class RichiesteHandler {

	private static RichiesteHandler richiesteHandler = new RichiesteHandler();

	private RichiesteHandler() {}

	public static RichiesteHandler getInstance() {
		return richiesteHandler;
	}

	public Boolean richiestaFerie(String codiceFiscale) throws Exception {
		if (GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(codiceFiscale);
			return GestiMonSw.getInstance().checkDisponibilitaFerie();
		} else {
			throw new Exception();
		}
	}

	public void indicaGiornoFerie(LocalDate giornoFerie) throws IllegalArgumentException, IndexOutOfBoundsException, Exception {
		if (GestiMonSw.getInstance().checkGiornoFerieDipendenti(giornoFerie)) {			
			GestiMonSw.getInstance().addGiornoFerie(giornoFerie);
		} else {
			throw new Exception();
		}
	}

	public String richiestaPermessi(String codiceFiscale) {
		if (GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(codiceFiscale);
			if (GestiMonSw.getInstance().checkDisponibilitaPermessi()) {
				return GestiMonSw.getInstance().getTurniDipendente();
			} else {
				return "Non puoi richiedere permessi";
			}
		}
		return "Non sei registrato";
	}

	public void selezionaTurno(int idTurno) throws NullPointerException, IllegalArgumentException, Exception {
		if (GestiMonSw.getInstance().checkEsistenzaTurno(idTurno)) {
			if (GestiMonSw.getInstance().checkPresenzaDipendenti(idTurno)) {
				GestiMonSw.getInstance().addTurnoPermesso(idTurno);				
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new NullPointerException();			
		}
	}

}
