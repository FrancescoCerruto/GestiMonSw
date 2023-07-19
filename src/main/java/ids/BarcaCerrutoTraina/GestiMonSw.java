package ids.BarcaCerrutoTraina;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GestiMonSw {
	
	private static GestiMonSw gestiMonSw = new GestiMonSw();

	private List<String> tipiPianificazione = List.of("Pianificazione manuale", "Pianificazione automatica");

	private List<String> tipiDisponibilita = List.of("nulla", "totale", "parziale");

	private List<Dipendente> dipendenti = new ArrayList<>();
	
	private Map<String, Carriera> carriere = new HashMap<>();

	private TurnazioneSettimanale turnazioneSettimanale = null;
	
	private Dipendente d;
	
	private Carriera c;
	
	private String identificativoSupervisoreAutorizzato = "root";
	
	private String identificativoUtenteDipendente;
	
	private RegistroMansioni registroMansioni;
	
	private StoricoCarriere storicoCarriere;
	
	//variabili di test
	List<TurnoLavorativo> turnoScoperto = new ArrayList<>();
	List<Dipendente> dipendentiDisponibili = new ArrayList<>();
	
	private GestiMonSw() {
		dipendenti.add(new Dipendente("Aldo", "Barca", "BRCLDA99T13I754B", "Siracusa", LocalDate.of(1999,  12, 13)));
		dipendenti.add(new Dipendente("Francesco", "Cerruto", "CRRFNC00S11F943E", "Noto", LocalDate.of(2000, 11, 11)));
		dipendenti.add(new Dipendente("Giovanni", "Traina", "TRNGNN99E14C351X", "Catania", LocalDate.of(1999, 5, 14)));
		
		storicoCarriere = new StoricoCarriere();
		
		c = new Carriera(LocalDate.now(), LocalDate.now().plusYears(1), 1000, new Dipendente(dipendenti.get(0).getDati().get(0), dipendenti.get(0).getDati().get(1), dipendenti.get(0).getDati().get(2), dipendenti.get(0).getDati().get(3), LocalDate.parse(dipendenti.get(0).getDati().get(4))));
		memorizzaDatiCarriera();

		c = new Carriera(LocalDate.now(), LocalDate.now().plusYears(1), 1000, new Dipendente(dipendenti.get(1).getDati().get(0), dipendenti.get(1).getDati().get(1), dipendenti.get(1).getDati().get(2), dipendenti.get(1).getDati().get(3), LocalDate.parse(dipendenti.get(1).getDati().get(4))));
		memorizzaDatiCarriera();

		c = new Carriera(LocalDate.now(), LocalDate.now().plusYears(1), 1000, new Dipendente(dipendenti.get(2).getDati().get(0), dipendenti.get(2).getDati().get(1), dipendenti.get(2).getDati().get(2), dipendenti.get(2).getDati().get(3), LocalDate.parse(dipendenti.get(2).getDati().get(4))));
		memorizzaDatiCarriera();
	}
	
	public static GestiMonSw getInstance() {
		return gestiMonSw;
	}
	
	public Boolean checkEsistenzaDipendente(String codiceFiscale) {
		Optional<Dipendente> d = dipendenti.stream()
				.filter(dTmp -> dTmp.getCodiceFiscale().equals(codiceFiscale))
				.findAny();
		if (d.isPresent()) {
			return true;
		}
		return false;
	}
	
	public void creaTurnazioneSettimanale() throws Exception {
		TurnazioneModifier.getInstance().initializeDateTurniLavorativi();
		turnazioneSettimanale = null;
		if (dipendenti.size() < 2) {
			throw new Exception();
		}
		for (Dipendente d : dipendenti) {
			d.resetDatiPianificazione(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0));
		}
		turnazioneSettimanale = new TurnazioneSettimanale();
		turnoScoperto = new ArrayList<>();
		turnoScoperto.clear();
		dipendentiDisponibili = new ArrayList<>();
		dipendentiDisponibili.clear();
	}

	public void memorizzaTurnazioneSettimanale() throws Exception{
		List<TurnoLavorativo> turniLavorativi = TurnazioneModifier.getInstance().getListaTurni();
		
		List<LocalDate> dateTurniLavorativi = TurnazioneModifier.getInstance().getDateTurniLavorativi();
		List<Integer> oraInizioTurniLavorativi = TurnazioneModifier.getInstance().getOraInizioTurniLavorativi();
		
		Map<LocalDate, Map<Integer, Integer>> turnoSelezionato = new HashMap<>();
		for (LocalDate d : dateTurniLavorativi) {
			turnoSelezionato.put(d, (Map<Integer,Integer>)new HashMap<Integer, Integer>());				
			for (Integer i : oraInizioTurniLavorativi) {
				turnoSelezionato.get(d).put(i, 0);
			}
		}
		
		//segno tutti i giorni e i turni valorizzati
		for (TurnoLavorativo tl : turniLavorativi) {
			if (dateTurniLavorativi.contains(tl.getData())) {
				Map<Integer, Integer> tmp = turnoSelezionato.get(tl.getData());
				tmp.put(tl.getOraInizio(), 1);
				turnoSelezionato.put(tl.getData(), tmp);
			}
		}
		
		//check su turni non selezionati
		for (LocalDate d : turnoSelezionato.keySet()) {
			for (Integer i : turnoSelezionato.get(d).keySet())
			if (turnoSelezionato.get(d).get(i) == 0) {
				turnoScoperto.add(new TurnoLavorativo(d, i, TurnazioneModifier.getInstance().getListaTurni().size() + turnoScoperto.size() + 1));
			}
		}
		
		//check su dipendenti ancora disponibili
		for (Dipendente d : dipendenti) {
			LocalDate dateTurno = d.getListaTurni().get(0).getData();
			if (d.getMonteOre(dateTurno) > 0) {
				dipendentiDisponibili.add(d);
			}
		}
		
		if (turnoScoperto.isEmpty() && dipendentiDisponibili.isEmpty()) {
			for (Dipendente d : dipendenti) {
				d.assegnaContributoLavorativo();
			}
			turnazioneSettimanale = new TurnazioneSettimanale();
			for (TurnoLavorativo tl : turniLavorativi) {
				turnazioneSettimanale.addTurno(tl);				
			}				
		} else {
			throw new Exception();
		}
	}
	
	public String getDescrizionePianificazione() {
		if (turnazioneSettimanale != null) {
			return turnazioneSettimanale.getDescrizione();				
		} else {
			return "Non esiste ancora una pianificazione settimanale";				
		}
	}
	
	public List<String> getTipiPianificazione() {
		return tipiPianificazione;
	}
	
	public void creaDipendente(String nome, String cognome, String codiceFiscale, String luogoNascita, LocalDate dataNascita) throws Exception {
		if (!checkEsistenzaDipendente(codiceFiscale)) {
			d = new Dipendente(nome, cognome, codiceFiscale, luogoNascita, dataNascita);			
		}
		throw new Exception();
	}
	
	public void creaCarriera(LocalDate dataAssunzione, LocalDate dataScadenzaContratto, int stipendio) {
		c = new Carriera(dataAssunzione, dataScadenzaContratto, stipendio, new Dipendente(d.getDati().get(0), d.getDati().get(1), d.getDati().get(2), d.getDati().get(3), LocalDate.parse(d.getDati().get(4))));
	}
	
	public void memorizzaDatiDipendente() {
		List<String> dati = d.getDati();
		dipendenti.add(new Dipendente (dati.get(0), dati.get(1), dati.get(2), dati.get(3), LocalDate.parse(dati.get(4))));			
		d = null;
	}
	
	public void memorizzaDatiCarriera() {
		carriere.put(c.getDipendente().getCodiceFiscale(), new Carriera(c.getDataAssunzione(), c.getDataScadenzaContratto(), c.getStipendio(), new Dipendente (c.getDipendente().getDati().get(0), c.getDipendente().getDati().get(1), c.getDipendente().getDati().get(2), c.getDipendente().getDati().get(3), LocalDate.parse(c.getDipendente().getDati().get(4)))));
		List<String> dati = c.getDati();
		String motivazione = "Creazione carriera - ";
		motivazione += "data assunzione " + dati.get(0) + " - ";
		motivazione += "data scadenza contratto " + dati.get(1) + " - ";
		motivazione += "stipendio " + dati.get(2) + " - ";
		storicoCarriere.aggiornaStorico(c.getDipendente().getCodiceFiscale(), motivazione);
		c = null;
	}
	
	public void rimuoviDipendente(String codiceFiscale) throws Exception {
		if (checkEsistenzaDipendente(codiceFiscale)) {
			int index = -1;
			for (Dipendente d : dipendenti) {
				if (d.getCodiceFiscale().equals(codiceFiscale)) {
					index = dipendenti.indexOf(d);
				}
			}
			if (index != -1) {
				dipendenti.remove(index);
			}
		} else {
			throw new Exception();
		}
	}
	
	public String getIdentificativoSupervisoreAutorizzato() {
		return identificativoSupervisoreAutorizzato;
	}

	public Map<String, List<String>> getDatiDipendenti() {
		Map<String, List<String>> datiDipendenti = new HashMap<>();
		for (Dipendente d : dipendenti) {
			datiDipendenti.put(d.getCodiceFiscale(), d.getDati());
		}
		return datiDipendenti;
	}

	public TurnazioneSettimanale getTurnazioneSettimanale() {
		return turnazioneSettimanale;
	}

	public void setIdentificativoUtenteDipendente(String codiceFiscale) {
		identificativoUtenteDipendente = codiceFiscale;
	}

	public Map<Integer, String> checkDisponibilitaCambio(String codiceFiscale) {
		Dipendente dCambio = getDipendente(codiceFiscale);
		if (dCambio.getNumeroCambiDisponibili() == 1) {
			List<TurnoLavorativo> listaTurniD1 = dCambio.getListaTurni();
			String descrizione = "";
			if (listaTurniD1 != null) {
				for (TurnoLavorativo tl : listaTurniD1) {
					descrizione += tl.getDescrizioneTurno();
				}
			}
			String descrizioneCambi = "";
			for (Dipendente d : dipendenti) {
				if (!d.equals(dCambio)) {
					List<TurnoLavorativo> listaTurniD2 = d.getListaCambi();
					if (listaTurniD2 != null) {						
						for (TurnoLavorativo tl : listaTurniD2) {
							descrizioneCambi += tl.getDescrizioneTurno();
						}
					}
				}
			}
			return Map.of(1, descrizione, 2, descrizioneCambi);
		}
		return null;
	}
	
	public Boolean checkEsistenzaTurnoCambio(int idTurno) {
		for (Dipendente d : dipendenti) {
			if (!d.getCodiceFiscale().equals(identificativoUtenteDipendente)) {
				List<TurnoLavorativo> tmp = d.getListaCambi();
				for (TurnoLavorativo tl : tmp) {
					if (tl.getId() == idTurno) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Boolean checkEsistenzaTurnoRichiestoCambio(int idTurno) {
		List<TurnoLavorativo> tmp = getDipendente(identificativoUtenteDipendente).getListaCambi();
		for (TurnoLavorativo tl : tmp) {
			if (tl.getId() == idTurno) {
				return true;
			}
		}
		return false;
	}
	
	public Dipendente getDipendente(String codiceFiscale) {
		for (Dipendente d : dipendenti) {
			if (d.getCodiceFiscale().equals(codiceFiscale)) {
				return d;
			}
		}
		return null;
	}

	public List<String> getTipoDisponibilita() {
		return tipiDisponibilita;
	}

	public String creaDisponibilita(String tipo) {
		getDipendente(identificativoUtenteDipendente).addDisponibilita(tipo);
		if (tipo.equals("nulla") || tipo.equals("totale")) {
			return "Disponibilità creata";			
		} else {
			List<TurnoLavorativo> listaTurni = getDipendente(identificativoUtenteDipendente).getListaTurni();
			String descrizione = "";
			for (TurnoLavorativo tl : listaTurni) {
				descrizione += tl.getDescrizioneTurno();
			}
			return descrizione;
		}
	}

	public String addTurnoDisponibilita(int idTurno) {
		return getDipendente(identificativoUtenteDipendente).addTurnoDisponibilita(idTurno);
	}
	
	public List<Dipendente> getDipendentiDisponibili() {
		return dipendentiDisponibili;
	}

	public void decrementaCambiDipendente() {
		getDipendente(identificativoUtenteDipendente).decrementaCambiSettimanali();
	}

	public Boolean checkDisponibilitaFerie() {
		if (getDipendente(identificativoUtenteDipendente).getNumeroFerie() > 0) {
			return true;
		}
		return false;
	}

	public void addGiornoFerie(LocalDate giornoFerie) throws IllegalArgumentException, IndexOutOfBoundsException, Exception {
		Dipendente tmp = getDipendente(identificativoUtenteDipendente);
		int numeroFerie = tmp.getNumeroFerie();
		Boolean valido = tmp.checkValiditaGiorno(giornoFerie);
		LocalDate lastGiornoTurnazione = TurnazioneModifier.getInstance().getDataLastTurno();
		LocalDate startNextWeek = lastGiornoTurnazione.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
		if (numeroFerie > 0) {
			if (giornoFerie.isAfter(startNextWeek) || giornoFerie.isEqual(startNextWeek)) {
				if (valido) {
					tmp.decrementaNumeroFerie();
					tmp.addFerie(giornoFerie);					
				} else {
					throw new IndexOutOfBoundsException();
				}
			} else {
				throw new IllegalArgumentException();
			}				
		} else {
			throw new Exception();
		}
	}

	public Boolean checkDisponibilitaPermessi() {
		if (getDipendente(identificativoUtenteDipendente).getNumeroPermessi() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void addTurnoPermesso(int idTurno) throws Exception {
		Dipendente tmp = getDipendente(identificativoUtenteDipendente);
		if (tmp.verificaValiditaPermesso(idTurno)) {
			tmp.decrementaNumeroPermessi();
			tmp.inserisciPermesso(idTurno);
		} else {
			throw new Exception();
		}
	}

	public void creaRegistroMansioni() {
		if (registroMansioni == null) {
			registroMansioni = new RegistroMansioni();			
		}
	}

	public void addMansione(Mansione mansione) {
		registroMansioni.memorizzaMansione(mansione);
	}
	
	public String getTurniDipendente() {
		List<TurnoLavorativo> turni = getDipendente(identificativoUtenteDipendente).getListaTurni();
		String descrizione = "";
		for (TurnoLavorativo tl : turni) {
			descrizione += tl.getDescrizioneTurno();
		}
		return descrizione;
	}

	public Map<String, List<String>> getDatiDipendentiLavorativi() {
		Map<String, List<String>> datiDipendenti = new HashMap<>();
		LocalDate d = TurnazioneModifier.getInstance().getDataLastTurno();
		for (Dipendente dip : dipendenti) {
			List<LocalDate> giorni = dip.getListaSettimanaFerie(d);
			if (giorni != null) {
				if (!giorni.contains(d)) {
					datiDipendenti.put(dip.getCodiceFiscale(), dip.getDati());
				}				
			} else {
				datiDipendenti.put(dip.getCodiceFiscale(), dip.getDati());
			}
		}
		return datiDipendenti;
	}

	//ritorna la lista dei dipendenti che possono lavorare nel giorno richiesto
	//nè sono in ferie nè hanno carico lavorativo massimo
	public List<Dipendente> getListaDipendentiLavorativi(LocalDate d) {
		List<Dipendente> dipendentiLavorativi = new ArrayList<>();
		for (Dipendente dip : dipendenti) {
			List<LocalDate> giorni = dip.getListaSettimanaFerie(d);
			if (giorni != null) {
				if (!giorni.contains(d)) {
					if (dip.getMonteOre(d) > 0) {
						dipendentiLavorativi.add(dip);					
					}
				}
			} else {
				dipendentiLavorativi.add(dip);
			}
		}
		return dipendentiLavorativi;
	}
	
	//controlla che ci siano almeno due dipendenti per settimana senza ferie
	public Boolean checkGiornoFerieDipendenti(LocalDate giornoFerie) {
		int contatoreDipendentiFerie = 0;
		for (Dipendente d : dipendenti) {
			List<LocalDate> listaFerie = d.getListaSettimanaFerie(giornoFerie);
			if (listaFerie != null) {
				contatoreDipendentiFerie++;			
			}
		}
		if (dipendenti.size() - contatoreDipendentiFerie > 2) {
			return true;
		} else {			
			return false;
		}
	}

	public boolean checkEsistenzaTurno(int idTurno) {
		if (getDipendente(identificativoUtenteDipendente).getTurnoById(idTurno) != null) {
			return true;
		} else {
			return false;			
		}
	}

	public boolean checkPresenzaDipendenti(int idTurno) {
		int contatoreDipendentiPermesso = 0;
		TurnoLavorativo tl = getDipendente(identificativoUtenteDipendente).getTurnoById(idTurno);
		LocalDate dataTurno = tl.getData();
		int oraInizio = tl.getOraInizio();
		for (Dipendente d : dipendenti) {
			if (d.checkDataPermessoDipendente(dataTurno, oraInizio)) {
				contatoreDipendentiPermesso++;
			}
		}
		if (dipendenti.size() - contatoreDipendentiPermesso >= 1) {
			return true;
		} else {
			return false;
		}
	}

	public int getNumeroDipendenti() {
		return dipendenti.size();
	}

	public String getListaMansioni() {
		if (registroMansioni != null) {
			return registroMansioni.getListaMansioni();
		}
		return "Non ci sono ancora mansioni caricate";
	}

	public int addMansioneToDipendente(int idMansione) {
		Dipendente tmp = getDipendente(identificativoUtenteDipendente);
		int caricoResiduo = tmp.getCaricoLavorativo();
		int punteggio = registroMansioni.getPunteggio(idMansione);
		if (punteggio == -1) {
			return Integer.MIN_VALUE;
		} else {
			tmp.decrementaCaricoLavorativo(punteggio);
			registroMansioni.rimuoviMansioneDisponibile(idMansione);
			tmp.setCaricoLavorativo(caricoResiduo - punteggio);
			return caricoResiduo - punteggio;
		}
	}

	public void addNota(String codiceFiscale, int tipoNota, int numero) throws NullPointerException {
		Carriera tmp = getCarrieraDipendente(codiceFiscale);
		if (tmp == null) {
			throw new NullPointerException();
		} else {
			int stipendioAttuale = tmp.getStipendio();
			tmp.addNota(tipoNota, numero);
			int stipendioAttualeDopoModifica = tmp.getStipendio();
			String motivazione = "";
			if (tipoNota == 1) {
				motivazione = "Note di merito";
			} else {
				if (tipoNota == 2) {
					motivazione = "Note di demerito";
				}
			}
			motivazione += " " + Integer.toString(numero);
			storicoCarriere.aggiornaStorico(codiceFiscale, motivazione);
			if (stipendioAttuale != stipendioAttualeDopoModifica) {
				motivazione = "Modifica stipendio da " + Integer.toString(stipendioAttuale) + " a " + Integer.toString(stipendioAttualeDopoModifica);
				storicoCarriere.aggiornaStorico(codiceFiscale, motivazione);
			}
		}
	}

	public List<String> mostraCarriera(String codiceFiscale) throws NullPointerException {
		Carriera c = getCarrieraDipendente(codiceFiscale);
		List<String> resultQuery = new ArrayList<>();
		if (c == null) {
			return null;
		} else {
			Map<LocalDate, List<String>> storico = storicoCarriere.getStorico(codiceFiscale);
			List<String> dati = c.getDati();
			String datiCarriera = "data assunzione " + dati.get(0) + " - ";
			datiCarriera += "data scadenza contratto " + dati.get(1) + " - ";
			datiCarriera += "stipendio " + dati.get(2) + " - ";
			datiCarriera += "note di merito " + dati.get(3) + " - ";
			datiCarriera += "note di demerito " + dati.get(4) + "\n";
			resultQuery.add(datiCarriera);
			for (LocalDate d : storico.keySet()) {
				for (String s : storico.get(d)) {
					String modifica = "In data " + d.toString() + " - " + s + "\n";
					resultQuery.add(modifica);
				}
			}
		}
		return resultQuery;
	}

	public int getNumeroMansioni() {
		return registroMansioni.getLastId();
	}
	
	//metodi di test
	public Dipendente getDipendenteTmp() {
		return d;
	}

	public void setDipendenteTmp(Dipendente tmpD) {
		d = tmpD;
	}

	public Carriera getCarrieraTmp() {
		return c;
	}

	public Map<String, Carriera> getCarriere() {
		return carriere;
	}

	public void setCarrieraTmp(Carriera tmpC) {
		c = tmpC;
	}

	public Carriera getCarrieraDipendente(String codiceFiscale) {
		return carriere.get(codiceFiscale);
	}

	public void clearAll() {
		d = null;
		c = null;
		registroMansioni = null;
		storicoCarriere = new StoricoCarriere();
		turnazioneSettimanale = null;
		carriere.clear();
		dipendenti.clear();
		dipendentiDisponibili.clear();
		turnoScoperto.clear();
	}
	
	public List<Dipendente> getListaDipendenti() {
		return dipendenti;
	}
	
	public List<TurnoLavorativo> getTurnoScoperto() {
		return turnoScoperto;
	}

	public RegistroMansioni getRegistroMansioni() {
		return registroMansioni;
	}

	public StoricoCarriere getStoricoCarriere() {
		return storicoCarriere;
	}
}