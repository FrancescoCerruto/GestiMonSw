package ids.BarcaCerrutoTraina;

import java.io.FileNotFoundException;

public class MansioniHandler {

	private static MansioniHandler mansioniHandler = new MansioniHandler();

	private MansioniHandler() {}

	public static MansioniHandler getInstance() {
		return mansioniHandler;
	}

	public void pianificaMansioni(String idSupervisore) throws Exception {
		if (!GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato().equals(idSupervisore)) {
			throw new Exception();
		}
	}

	public void caricaFile(String path) throws FileNotFoundException, IllegalArgumentException {
		if (MansioniCreator.getInstance().verificaEsistenzaFile(path)) {
			if (MansioniCreator.getInstance().verificaValiditaFile(path)) {
				MansioniCreator.getInstance().creaMansioni(path);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new FileNotFoundException();
		}
	}
	
	public String scegliMansione(String codiceFiscale) {
		if (GestiMonSw.getInstance().checkEsistenzaDipendente(codiceFiscale)) {
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(codiceFiscale);
			return GestiMonSw.getInstance().getListaMansioni();
		} else {
			return "Non sei autorizzato ad eseguire questa operazione";
		}
	}
	
	public int selezionaMansione(int idMansione) {
		return GestiMonSw.getInstance().addMansioneToDipendente(idMansione);
	}

	public String recuperaMansioni() {
		return GestiMonSw.getInstance().getListaMansioni();
	}
}
