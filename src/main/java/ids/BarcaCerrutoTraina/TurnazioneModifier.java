package ids.BarcaCerrutoTraina;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TurnazioneModifier {
	private static TurnazioneModifier turnazioneModifier = new TurnazioneModifier();
	private List<TurnoLavorativo> turniLavorativi = new ArrayList<>();
	private List<LocalDate> giorniTurniLavorativi = new ArrayList<>();
	private List<Integer> oraInizioTurniLavorativi = new ArrayList<>();
	
	private TurnazioneModifier() {}
	
	public static TurnazioneModifier getInstance() {
		return turnazioneModifier;
	}
	
	public void initializeDateTurniLavorativi() {
		giorniTurniLavorativi = new ArrayList<>();
		oraInizioTurniLavorativi = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			//fisso i turni di mattina
			for (int j = 0; j < 3; j++) {
				//ora di inizio
				oraInizioTurniLavorativi.add(8 + (j * 2));
				//costruisco attributo date di turno lavorativo
				//giorno della settimana di esecuzione software
				LocalDate giorno = LocalDate.now();
				//comincio a pianificare dal lunedi - vado avanti a partire dal lunedi successivo
				giorno = giorno.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.MONDAY.getValue() + i)));					
				giorniTurniLavorativi.add(giorno);
			}
			//fisso i turni di pomeriggio
			for (int j = 0; j < 2; j++) {
				//ora di inizio
				oraInizioTurniLavorativi.add(15 + (j * 2));
				//costruisco attributo date di turno lavorativo
				//giorno della settimana di esecuzione software
				LocalDate giorno = LocalDate.now();
				//comincio a pianificare dal lunedi - vado avanti a partire dal lunedi successivo
				giorno = giorno.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.MONDAY.getValue() + i)));					
				giorniTurniLavorativi.add(giorno);
			}
		}
		turniLavorativi = new ArrayList<>();
		turniLavorativi.clear();
	}
	
	public void inserisciTurniAutomatici() {
		//recupero la lista dei dipendenti che possono lavorare in questa settimana per ogni giorno
		//la lista dentro la mappa contiene le persone che possono lavorare in queseto specifico giorno lavorativo
		Map<LocalDate, List<Dipendente>> dipendentiLavorativiGiorno = new HashMap<>();
		for (LocalDate d : giorniTurniLavorativi) {
			dipendentiLavorativiGiorno.put(d, GestiMonSw.getInstance().getListaDipendentiLavorativi(d));
		}
		
		//ci sono dipendenti che devono lavorare tutti i giorni (si almeno 2)? --> uso l'algoritmo vecchio
		List<Dipendente> full = new ArrayList<>();
		for (LocalDate d : giorniTurniLavorativi) {
			for (Dipendente dip : dipendentiLavorativiGiorno.get(d)) {
				if (dip.getMonteOre(d) == 40) {
					if (!full.contains(dip)) {
						full.add(dip);
					}
				}
			}
		}
		
		for (Dipendente d : full) {
			int indexDipendente = full.indexOf(d);
			if ((indexDipendente % 2) == 0) {
				//3 giorni interi: lunedi (date [0, 4]) mercoledi (date [10, 14]) venerdi (date [20, 24])
				for (int j = 0; j < 3; j ++) {	//itero sui giorni da assegnare						
					//itero sui turni del giorno
					for (int k = 0; k < 5; k++) {	//itero sulla fascia oraria
						TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((j * 10) + k), oraInizioTurniLavorativi.get((j * 10) + k), turniLavorativi.size() + 1);
						tl.setDipendente(full.get(indexDipendente));
						full.get(indexDipendente).addTurno(tl);
						turniLavorativi.add(tl);
					}
				}
				//martedi mattina
				for (int k = 0; k < 3; k++) {
					TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get(5 + k), oraInizioTurniLavorativi.get(5 + k), turniLavorativi.size() + 1);
					tl.setDipendente(full.get(indexDipendente));
					full.get(indexDipendente).addTurno(tl);
					turniLavorativi.add(tl);
				}
				//venerdi pomeriggio
				for (int k = 0; k < 2; k++) {
					TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get(18 + k), oraInizioTurniLavorativi.get(18 + k), turniLavorativi.size() + 1);
					tl.setDipendente(full.get(indexDipendente));
					full.get(indexDipendente).addTurno(tl);
					turniLavorativi.add(tl);
				}
			} else {
				//3 giorni interi: martedi (date [5, 9]) giovedi (date [15, 19]) sabato (date [25, 29])
				for (int j = 0; j < 3; j ++) {	//itero sui giorni da assegnare
					for (int k = 5; k < 10; k++) {	//itero sulla fascia oraria
						TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((j * 10) + k), oraInizioTurniLavorativi.get((j * 10) + k), turniLavorativi.size() + 1);
						tl.setDipendente(full.get(indexDipendente));
						full.get(indexDipendente).addTurno(tl);
						turniLavorativi.add(tl);
					}
				}
				//mercoledi mattina
				for (int k = 0; k < 3; k++) {
					TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get(10 + k), oraInizioTurniLavorativi.get(10 + k), turniLavorativi.size() + 1);
					tl.setDipendente(full.get(indexDipendente));
					full.get(indexDipendente).addTurno(tl);
					turniLavorativi.add(tl);
				}
				//venerdi pomeriggio
				for (int k = 0; k < 2; k++) {
					TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get(23 + k), oraInizioTurniLavorativi.get(23 + k), turniLavorativi.size() + 1);
					tl.setDipendente(full.get(indexDipendente));
					full.get(indexDipendente).addTurno(tl);
					turniLavorativi.add(tl);
				}
			}
		}
		
		//rimuovo i dipendenti dalla mappa che hanno esaurito il monte ore settimanale
		for (LocalDate d : giorniTurniLavorativi) {
			List<Dipendente> tmp = new ArrayList<>();
			for (Dipendente dip : dipendentiLavorativiGiorno.get(d)) {
				if (dip.getMonteOre(d) == 0) {
					if (!tmp.contains(dip)) {
						tmp.add(dip);
					}
				}
			}
			for (Dipendente dip : tmp) {
				dipendentiLavorativiGiorno.get(d).remove(dip);
			}
		}
		
		//in mappa ho solo persone che lavorano parzialmente questa settimana --> gli do n giorni lavorativi
		//recupero la lista di tutti i dipendenti parziali con 3 giorni
		List<Dipendente> partial30Ore = new ArrayList<>();
		for (LocalDate d : giorniTurniLavorativi) {
			for (Dipendente dip : dipendentiLavorativiGiorno.get(d)) {
				if (dip.getMonteOre(d) == 30) {
					if (!partial30Ore.contains(dip)) {
						partial30Ore.add(dip);	
					}
				}
			}
		}
		
		//vecchio algoritmo di turnazione (tolto i turni di mattina e pomeriggio)
		for (Dipendente d : partial30Ore) {
			int indexDipendente = partial30Ore.indexOf(d);
			if ((indexDipendente % 2) == 0) {
				//3 giorni interi: lunedi (date [0, 4]) mercoledi (date [10, 14]) venerdi (date [20, 24])
				for (int j = 0; j < 3; j ++) {	//itero sui giorni da assegnare						
					//itero sui turni del giorno
					for (int k = 0; k < 5; k++) {	//itero sulla fascia oraria
						TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((j * 10) + k), oraInizioTurniLavorativi.get((j * 10) + k), turniLavorativi.size() + 1);
						tl.setDipendente(partial30Ore.get(indexDipendente));
						partial30Ore.get(indexDipendente).addTurno(tl);
						turniLavorativi.add(tl);
					}
				}
			} else {
				//3 giorni interi: martedi (date [5, 9]) giovedi (date [15, 19]) sabato (date [25, 29])
				for (int j = 0; j < 3; j ++) {	//itero sui giorni da assegnare
					for (int k = 5; k < 10; k++) {	//itero sulla fascia oraria
						TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((j * 10) + k), oraInizioTurniLavorativi.get((j * 10) + k), turniLavorativi.size() + 1);
						tl.setDipendente(partial30Ore.get(indexDipendente));
						partial30Ore.get(indexDipendente).addTurno(tl);
						turniLavorativi.add(tl);
					}
				}
			}
		}
		
		//recupero la lista di tutti i dipendenti parziali con 2 giorni
		List<Dipendente> partial20Ore = new ArrayList<>();
		for (LocalDate d : giorniTurniLavorativi) {
			for (Dipendente dip : dipendentiLavorativiGiorno.get(d)) {
				if (dip.getMonteOre(d) == 20) {
					if (!partial20Ore.contains(dip)) {
						partial20Ore.add(dip);				
					}
				}
			}
		}
		
		//vecchio algoritmo di turnazione (tolto 1 giorno intero e i turni di mattina e pomeriggio)
		for (Dipendente d : partial20Ore) {
			int indexDipendente = partial20Ore.indexOf(d);
			if ((indexDipendente % 2) == 0) {
				//3 giorni interi: lunedi (date [0, 4]) mercoledi (date [10, 14])
				for (int j = 0; j < 2; j ++) {	//itero sui giorni da assegnare						
					//itero sui turni del giorno
					for (int k = 0; k < 5; k++) {	//itero sulla fascia oraria
						TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((j * 10) + k), oraInizioTurniLavorativi.get((j * 10) + k), turniLavorativi.size() + 1);
						tl.setDipendente(partial20Ore.get(indexDipendente));
						partial20Ore.get(indexDipendente).addTurno(tl);
						turniLavorativi.add(tl);
					}
				}
			} else {
				//3 giorni interi: martedi (date [5, 9]) giovedi (date [15, 19])
				for (int j = 0; j < 2; j ++) {	//itero sui giorni da assegnare
					for (int k = 5; k < 10; k++) {	//itero sulla fascia oraria
						TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((j * 10) + k), oraInizioTurniLavorativi.get((j * 10) + k), turniLavorativi.size() + 1);
						tl.setDipendente(partial20Ore.get(indexDipendente));
						partial20Ore.get(indexDipendente).addTurno(tl);
						turniLavorativi.add(tl);
					}
				}
			}
		}
		
		//recupero la lista di tutti i dipendenti parziali con 1 giorno
		List<Dipendente> partial10Ore = new ArrayList<>();
		for (LocalDate d : giorniTurniLavorativi) {
			for (Dipendente dip : dipendentiLavorativiGiorno.get(d)) {
				if (dip.getMonteOre(d) == 10) {
					if (!partial10Ore.contains(dip)) {
						partial10Ore.add(dip);	
					}
				}
			}
		}

		//vecchio algoritmo di turnazione (tolti 2 giorni interi e i turni di mattina e pomeriggio)
		for (Dipendente d : partial10Ore) {
			int indexDipendente = partial10Ore.indexOf(d);
			if ((indexDipendente % 2) == 0) {
				//1 giorno intero: venerdi
				for (int k = 0; k < 5; k++) {	//itero sulla fascia oraria
					TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((2 * 10) + k), oraInizioTurniLavorativi.get((2 * 10) + k), turniLavorativi.size() + 1);
					tl.setDipendente(partial10Ore.get(indexDipendente));
					partial10Ore.get(indexDipendente).addTurno(tl);
					turniLavorativi.add(tl);
				}
			} else {
				//1 giorno intero: sabato
				for (int k = 5; k < 10; k++) {	//itero sulla fascia oraria
					TurnoLavorativo tl = new TurnoLavorativo(giorniTurniLavorativi.get((5 * 10) + k), oraInizioTurniLavorativi.get((5 * 10) + k), turniLavorativi.size() + 1);
					tl.setDipendente(partial10Ore.get(indexDipendente));
					partial10Ore.get(indexDipendente).addTurno(tl);
					turniLavorativi.add(tl);
				}
			}
		}
		
		try {
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inserisciTurno(LocalDate giorno, int oraInizio) throws Exception {
		if (giorniTurniLavorativi.contains(giorno) && oraInizioTurniLavorativi.contains(oraInizio)) {
			turniLavorativi.add(new TurnoLavorativo(giorno, oraInizio, turniLavorativi.size() + 1));
		} else {
			throw new Exception();
		}
	}
	
	public String valorizzaTurno(String codiceFiscale) throws Exception {
		//dipendenti contiene le persone che possono lavorare in queseto specifico giorno lavorativo
		List<Dipendente> dipendenti = GestiMonSw.getInstance().getListaDipendentiLavorativi(turniLavorativi.get(turniLavorativi.size() - 1).getData());
		for (Dipendente d : dipendenti) {
			if (d.getCodiceFiscale().equals(codiceFiscale)) {
				if (d.getMonteOre(turniLavorativi.get(turniLavorativi.size() - 1).getData()) > 0) {
					turniLavorativi.get(turniLavorativi.size() - 1).setDipendente(d);
					d.addTurno(turniLavorativi.get(turniLavorativi.size() - 1));
				} else {
					throw new Exception();
				}
			}
		}
		String descrizione = "";
		for (TurnoLavorativo tl : turniLavorativi) {
			descrizione += tl.getDescrizioneTurno();
		}
		return descrizione;
	}
	
	public void scambiaTurni(int idTurno1, int idTurno2) throws Exception, NullPointerException {
		TurnazioneSettimanale turnazioneSettimanale = GestiMonSw.getInstance().getTurnazioneSettimanale();
		turnazioneSettimanale.swapTurni(idTurno1, idTurno2);
	}

	public List<TurnoLavorativo> getListaTurni() {
		return turniLavorativi;
	}
	
	public LocalDate getDataLastTurno() {
		if (turniLavorativi.size() > 0) {
			return turniLavorativi.get(turniLavorativi.size() - 1).getData();			
		} else {
			LocalDate tmpDate = LocalDate.now();
			tmpDate = tmpDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
			tmpDate = tmpDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
			return tmpDate;
		}
	}

	public List<Integer> getOraInizioTurniLavorativi() {
		return oraInizioTurniLavorativi;
	}
	
	public Boolean tl1Beforetl2TwoDays(int idTurno1, int idTurno2) throws Exception {
		TurnazioneSettimanale turnazioneSettimanale = GestiMonSw.getInstance().getTurnazioneSettimanale();
		if (turnazioneSettimanale == null) {
			throw new NullPointerException();
		}
		TurnoLavorativo tl1 = turnazioneSettimanale.getTurnoById(idTurno1);
		TurnoLavorativo tl2 = turnazioneSettimanale.getTurnoById(idTurno2);
		return (tl1.getData().plusDays(2).isBefore(tl2.getData()) || tl1.getData().plusDays(2).isEqual(tl2.getData()));
	}
	
	//metodi di test
	public void clearListaTurni() {
		turniLavorativi.clear();
	}
	
	public List<LocalDate> getDateTurniLavorativi() {
		return giorniTurniLavorativi;
	}
	
	public void addTurno(TurnoLavorativo tl) {
		turniLavorativi.add(tl);
	}
	
	public Boolean tl1Beforetl2TwoDaysTesting(TurnoLavorativo tl1, TurnoLavorativo tl2) {
		return (tl1.getData().plusDays(2).isBefore(tl2.getData()) || tl1.getData().plusDays(2).isEqual(tl2.getData()));
	}
}
