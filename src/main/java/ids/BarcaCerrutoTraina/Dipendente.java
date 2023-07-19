package ids.BarcaCerrutoTraina;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class Dipendente {
	public static final int caricoLavorativo = 100;

	private String nome;

	private String cognome;

	private String codiceFiscale;
	
	private String luogoNascita;
	
	private LocalDate dataNascita;
	
	private Map<Integer, Integer> monteOreSettimana = new HashMap<>();

	private int numeroCambiTurno = 0;
	
	private int numeroFerie = 0;
	
	private int numeroPermessi = 0;
	
	private int caricoLavorativoAssegnato = caricoLavorativo;
	
	private Map<Integer, List<LocalDate>> giorniFerie = new HashMap<>();
	
	private List<TurnoLavorativo> turniLavorativi = new ArrayList<>();
	
	private List<TurnoLavorativo> listaPermessi = new ArrayList<>();

	private Disponibilita disponibilita = null;
	
	public Dipendente(String nome, String cognome, String codiceFiscale, String luogoNascita, LocalDate dataNascita) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.luogoNascita = luogoNascita;
		this.dataNascita = dataNascita;
		turniLavorativi.clear();
	}

	public List<String> getDati() {
		return List.of(nome, cognome, codiceFiscale, luogoNascita, dataNascita.toString());
	}

	public void addTurno(TurnoLavorativo turnoLavorativo) {
		turniLavorativi.add(turnoLavorativo);
		LocalDate giornoTurno = turnoLavorativo.getData();
		
		if (monteOreSettimana.containsKey(giornoTurno.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()))) {
			int monteOreAttuale = monteOreSettimana.get(giornoTurno.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
			monteOreSettimana.put(giornoTurno.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), monteOreAttuale - 2);			
		}
	}
	
	public void removeTurno(TurnoLavorativo turnoLavorativo) {
		Optional<TurnoLavorativo> tmp = turniLavorativi.stream()
				.filter(tl -> tl.getId() == turnoLavorativo.getId())
				.findAny();
		if (tmp.isPresent()) {
			turniLavorativi.remove(tmp.get());
		}
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void resetDatiPianificazione(LocalDate dataTurno) {
		monteOreSettimana.put(dataTurno.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), 40);
		turniLavorativi = new ArrayList<>();
		turniLavorativi.clear();
		disponibilita = null;
		caricoLavorativoAssegnato = caricoLavorativo;
	}
	
	public void assegnaContributoLavorativo() {
		numeroCambiTurno = 1;
		numeroFerie++;
		numeroPermessi++;
	}

	public int getMonteOre(LocalDate dataTurno) {
		return monteOreSettimana.get(dataTurno.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
	}
	
	public void addDisponibilita(String tipo) {
		disponibilita = new Disponibilita(tipo);
		if (tipo.equals("totale")) {
			for (TurnoLavorativo tl : turniLavorativi) {
				disponibilita.addTurnoDisponibile(tl);
			}
		}
	}
	
	public String addTurnoDisponibilita(int idTurno) {
		Boolean found = false;
		for (TurnoLavorativo tl : turniLavorativi) {
			if (tl.getId()== idTurno) {
				found = true;
				List<TurnoLavorativo> listaCambi = disponibilita.getTurni();
				for (TurnoLavorativo tlc : listaCambi) {
					if (tlc.getId() == tl.getId()) {
						return "Disponibilita già espressa";
					}
				}
				disponibilita.addTurnoDisponibile(tl);
			}
		}
		if (found) {
			String descrizione = "";
			for (TurnoLavorativo tl : turniLavorativi) {
				descrizione += tl.getDescrizioneTurno();
			}
			return descrizione;
		} else {
			return "Turno non trovato";
		}
	}
	
	public int getNumeroCambiDisponibili() {
		return numeroCambiTurno;
	}

	public List<TurnoLavorativo> getListaCambi() {
		if (disponibilita == null) {
			return null;
		} else {
			return disponibilita.getTurni();			
		}
	}

	public void decrementaCambiSettimanali() {
		numeroCambiTurno = 0;
	}

	public List<TurnoLavorativo> getListaTurni() {
		return turniLavorativi;
	}

	public int getNumeroFerie() {
		return numeroFerie;
	}
	
	public int getNumeroPermessi() {
		return numeroPermessi;
	}
	
	public Boolean checkValiditaGiorno(LocalDate giornoFerie) {
		List<LocalDate> giorniRichiesti = giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
		if (giorniRichiesti != null) {
			if (giorniRichiesti.size() >= 4) {
				return false;
			} else {
				if (giornoFerie.getDayOfWeek() == DayOfWeek.SUNDAY) {
					return false;
				} else {
					return true;					
				}
			}
		} else {
			return true;
		}		
	}

	public void decrementaNumeroFerie() {
		numeroFerie--;
	}

	public void addFerie(LocalDate giornoFerie) {
		if (giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())) != null) {
			giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())).add(giornoFerie);	
			if (monteOreSettimana.containsKey(monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())))) {
				int monteOreAttuali = monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), monteOreAttuali - 10);
			} else {
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), 30);
			}
		} else {
			giorniFerie.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), new ArrayList<>());
			giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())).add(giornoFerie);
			if (monteOreSettimana.containsKey(monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())))) {
				int monteOreAttuali = monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), monteOreAttuali - 10);
			} else {
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), 30);
			}
		}
	}

	public Boolean verificaValiditaPermesso(int idTurno) {
		TurnoLavorativo tl = getTurnoById(idTurno);
		if (tl != null) {
			return checkValiditaTurno(idTurno);
		} else {
			return false;
		}
	}

	public Boolean checkValiditaTurno(int idTurno) {
		Boolean ok = true;
		for (TurnoLavorativo tl : listaPermessi) {
			if ((idTurno == tl.getId() + 1) || (idTurno == tl.getId() - 1) || (idTurno == tl.getId())) {
				ok = false;
			}
		}
		return ok;
	}	

	public void decrementaNumeroPermessi() {
		numeroPermessi--;
	}

	public void inserisciPermesso(int idTurno) {
		listaPermessi.add(getTurnoById(idTurno));
	}
	
	public TurnoLavorativo getTurnoById(int idTurno) {
		Optional<TurnoLavorativo> tmp = turniLavorativi.stream()
			.filter(tl -> tl.getId() == idTurno)
			.findAny();
		if (tmp.isPresent()) {
			return tmp.get();
		}
		return null;
	}	

	public List<LocalDate> getListaSettimanaFerie(LocalDate day) {
		return giorniFerie.get(day.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
	}

	public boolean checkDataPermessoDipendente(LocalDate dataTurno, int oraInizio) {
		for (TurnoLavorativo tl : listaPermessi) {
			if (tl.isPermesso(dataTurno, oraInizio)) {
				return true;
			}
		}
		return false;
	}
	
	public int getCaricoLavorativo() {
		return caricoLavorativoAssegnato;
	}

	public void decrementaCaricoLavorativo(int punteggio) {
		caricoLavorativoAssegnato -= punteggio;
	}

	public void setCaricoLavorativo(int caricoLavorativoAssegnato) {
		this.caricoLavorativoAssegnato= caricoLavorativoAssegnato;
	}
	
	//metodi test
	@Override
	public boolean equals(Object d) {
		if (this == d) {
			return true;
		}
		if (d == null || d.getClass() != getClass()) {
			return false;
		}
		Dipendente tmp = (Dipendente) d;
		if (this.nome.equals(tmp.nome) && 
				this.cognome.equals(tmp.cognome) &&
				this.codiceFiscale.equals(tmp.codiceFiscale) &&
				this.luogoNascita.equals(tmp.luogoNascita) &&
				this.dataNascita.equals(tmp.dataNascita)) {
			return true;
		}
		return false;
	}
	
	public Disponibilita getDisponibilita() {
		return disponibilita;
	}
	
	public void azzeraCambi() {
		numeroCambiTurno = 0;
	}
	
	public void setNumeroFerie(int numero) {
		numeroFerie = numero;
	}
	
	public Map<Integer, List<LocalDate>> getGiorniFerie() {
		return giorniFerie;
	}
	
	public void setNumeroCambi() {
		numeroCambiTurno = 1;
	}
	
	public List<TurnoLavorativo> getListaPermessi() {
		return listaPermessi;
	}
	
	public void setNumeroPermessi() {
		numeroPermessi = 2;
	}
	
	public void addGiornoFerie(LocalDate giornoFerie) {
		if (giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())) != null) {
			giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())).add(giornoFerie);	
			if (monteOreSettimana.containsKey(monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())))) {
				int monteOreAttuali = monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), monteOreAttuali - 10);
			} else {
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), 30);
			}
		} else {
			giorniFerie.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), new ArrayList<>());
			giorniFerie.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())).add(giornoFerie);
			if (monteOreSettimana.containsKey(monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())))) {
				int monteOreAttuali = monteOreSettimana.get(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), monteOreAttuali - 10);
			} else {
				monteOreSettimana.put(giornoFerie.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), 30);
			}
		}
	}
}
