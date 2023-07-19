package ids.BarcaCerrutoTraina;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DipendenteHandler {
	private static DipendenteHandler dipendenteHandler = new DipendenteHandler();

	private DipendenteHandler() {}

	public static DipendenteHandler getInstance() {
		return dipendenteHandler;
	}
	
	public void inserisciDatiDipendente(String nome, String cognome, String codiceFiscale, String luogoNascita, LocalDate dataNascita) throws Exception {
		GestiMonSw.getInstance().creaDipendente(nome, cognome, codiceFiscale, luogoNascita, dataNascita);
	}
	

	public void inserisciDatiCarriera(LocalDate dataAssunzione, LocalDate dataScadenzaContratto, int stipendio) {
		GestiMonSw.getInstance().creaCarriera(dataAssunzione, dataScadenzaContratto, stipendio);
	}
	
	public void confermaInserimento() {
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().memorizzaDatiCarriera();
	}
	
	public Map<String, List<String>> rimuoviDipendente(String idSupervisore) throws Exception {
		String idAutorizzato = GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato();
		if (idAutorizzato.equals(idSupervisore)) {
			return GestiMonSw.getInstance().getDatiDipendenti();
		}
		throw new Exception();
	}
	
	public void cancellaDipendente(String codiceFiscale) throws Exception {
		GestiMonSw.getInstance().rimuoviDipendente(codiceFiscale);
	}

	public Map<String, List<String>> recuperaDipendenti() {
		return GestiMonSw.getInstance().getDatiDipendenti();
	}

	public Map<String, List<String>> recuperaDipendentiTurnazione() {
		return GestiMonSw.getInstance().getDatiDipendentiLavorativi();
	}
	
	public void memorizzaDatiDipendente(String idSupervisore) throws Exception {
		String idAutorizzato = GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato();
		if (!idAutorizzato.equals(idSupervisore)) {
			throw new Exception();
		}
	}
	
	public Map<String, List<String>> modificaCarriera(String idSupervisore) {
		if (GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato().equals(idSupervisore)) {
			return GestiMonSw.getInstance().getDatiDipendenti();
		} else {
			return null;
		}
	}
	
	public void scegliCarriera(String codiceFiscale, int tipoNota, int numero) throws NullPointerException {
		GestiMonSw.getInstance().addNota(codiceFiscale, tipoNota, numero);
	}
	
	public Map<String, List<String>> visualizzaCarriera(String idSupervisore) {
		if (GestiMonSw.getInstance().getIdentificativoSupervisoreAutorizzato().equals(idSupervisore)) {
			return GestiMonSw.getInstance().getDatiDipendenti();
		} else {
			return null;
		}
	}
	
	public List<String> vediCarriera(String codiceFiscale) {
		return GestiMonSw.getInstance().mostraCarriera(codiceFiscale);
	}
}
