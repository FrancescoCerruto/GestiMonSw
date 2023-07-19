package ids.BarcaCerrutoTraina;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnazioneModifierTest {

	Dipendente tmpD;
	Dipendente tmpD2;
	TurnoLavorativo tmpTl1;
	TurnoLavorativo tmpTl2;
	
	@BeforeEach
	void initGestiMonSw() {
		TurnazioneModifier.getInstance().clearListaTurni();
		GestiMonSw.getInstance().clearAll();
		
		tmpD = new Dipendente("pippo", "franco", "dd", "ff", LocalDate.now());
		tmpD2 = new Dipendente("giulio", "cesare", "cc", "ff", LocalDate.now());
		
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		tmpTl1 = new TurnoLavorativo(LocalDate.now(), 8, 1);
		tmpTl1.setDipendente(tmpD);
		tmpTl2 = new TurnoLavorativo(LocalDate.now(), 8, 2);
		tmpTl2.setDipendente(tmpD2);
	}

	@Test
	void testInitializeDateTurniLavorativi() {
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		
		TurnazioneModifier.getInstance().initializeDateTurniLavorativi();
		assertTrue(TurnazioneModifier.getInstance().getDateTurniLavorativi().size() == 30);
		
		int ore = 8;
		LocalDate tmpDate = LocalDate.now();
		tmpDate = tmpDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.MONDAY.getValue())));					
		
		assertTrue(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0).equals(tmpDate));
		assertTrue(TurnazioneModifier.getInstance().getOraInizioTurniLavorativi().get(0) == ore);
		
		ore = 17;
		tmpDate = LocalDate.now();
		tmpDate = tmpDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.SATURDAY.getValue())));					
		
		assertTrue(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(29).equals(tmpDate));
		assertTrue(TurnazioneModifier.getInstance().getOraInizioTurniLavorativi().get(29) == ore);
	}

	//richiede i test di GestiMonSw - bypasso il controllo sulla lista dei dipendenti
	@Test
	void testInserisciTurniAutomatici() {
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		
		for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
			assertTrue(d.getListaTurni().isEmpty());
		}
		
		//creo i turni automoatici
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			TurnazioneModifier.getInstance().initializeDateTurniLavorativi();
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			
			assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == GestiMonSw.getInstance().getListaDipendenti().size() * 20);
			
			for (int i = 0; i < TurnazioneModifier.getInstance().getListaTurni().size(); i++) {
				assertTrue(TurnazioneModifier.getInstance().getDateTurniLavorativi().contains(TurnazioneModifier.getInstance().getListaTurni().get(i).getData()));
			}
					
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getListaTurni().size() == 20);
				assertTrue(d.getMonteOre(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0)) == 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//richiede i test di GestiMonSw - bypasso il controllo sulla lista dei dipendenti
	@Test
	void testInserisciTurnoDataVecchia() {
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		
		for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
			assertTrue(d.getListaTurni().isEmpty());
		}

		//creo il turno
		TurnazioneModifier.getInstance().initializeDateTurniLavorativi();
		
		//lunedi della settimana precedente
		LocalDate tmpDate = LocalDate.now();
		tmpDate = tmpDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		
		try {
			TurnazioneModifier.getInstance().inserisciTurno(tmpDate, 8);
		} catch (Exception e) {
			assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == 0);
		}
	}

	//richiede i test di GestiMonSw - bypasso il controllo sulla lista dei dipendenti
	@Test
	void testInserisciTurnoDataDomenica() {
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		
		for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
			assertTrue(d.getListaTurni().isEmpty());
		}

		//creo il turno
		TurnazioneModifier.getInstance().initializeDateTurniLavorativi();
		
		//lunedi della settimana precedente
		LocalDate tmpDate = LocalDate.now();
		tmpDate = tmpDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		
		try {
			TurnazioneModifier.getInstance().inserisciTurno(tmpDate, 8);
		} catch (Exception e) {
			assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == 0);
		}
	}

	//richiede i test di GestiMonSw - bypasso il controllo sulla lista dei dipendenti
	@Test
	void testInserisciTurnoDataValido() {
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		
		for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
			assertTrue(d.getListaTurni().isEmpty());
		}

		//creo il turno
		TurnazioneModifier.getInstance().initializeDateTurniLavorativi();
		
		//primo giorno della settimana in pianificazione
		try {
			TurnazioneModifier.getInstance().inserisciTurno(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0), TurnazioneModifier.getInstance().getOraInizioTurniLavorativi().get(0));
			assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == 1);
		} catch (Exception e) {
			fail("Ci sono state eccezioni");
		}
	}

	//richiede i test di GestiMonSw - bypasso il controllo sulla lista dei dipendenti
	@Test
	void testValorizzaTurno() {
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		
		for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
			assertTrue(d.getListaTurni().isEmpty());
		}

		//creo il turno
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			//primo giorno della settimana in pianificazione
			try {
				TurnazioneModifier.getInstance().inserisciTurno(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0), TurnazioneModifier.getInstance().getOraInizioTurniLavorativi().get(0));
				assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == 1);
				TurnazioneModifier.getInstance().valorizzaTurno(tmpD.getCodiceFiscale());
				assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().size() == 1);
				assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).equals(TurnazioneModifier.getInstance().getListaTurni().get(0)));
			} catch (Exception e) {
				e.printStackTrace();
				fail("Ci sono state eccezioni");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Ci sono state eccezioni");
		}
	}
	
	@Test
	void testScambiaTurniTurnazioneNonEsistente() {
		assertNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
		try {
			TurnazioneModifier.getInstance().scambiaTurni(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() + 1, GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() + 2);
		} catch (NullPointerException e) {
			assertNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//richiede una turnazione già memorizzata - faccio quella automatica
	@Test
	void testScambiaTurniTurnazioneEsistenteIdTurniNonValidi() {
		List<TurnoLavorativo> before = null;
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getListaTurni().isEmpty());
			}
			
			//creo i turni automoatici
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			
			assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == GestiMonSw.getInstance().getListaDipendenti().size() * 20);
			
			for (int i = 0; i < TurnazioneModifier.getInstance().getListaTurni().size(); i++) {
				assertTrue(TurnazioneModifier.getInstance().getDateTurniLavorativi().contains(TurnazioneModifier.getInstance().getListaTurni().get(i).getData()));
			}
					
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getListaTurni().size() == 20);
				assertTrue(d.getMonteOre(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0)) == 0);
			}
			
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			
			//cambio turni con identificativi non memorizzati
			before = GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi();
			TurnazioneModifier.getInstance().scambiaTurni(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() + 1, GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() + 2);
		} catch (Exception e) {
			List<TurnoLavorativo> after = GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi();
			assertTrue(after.equals(before));
		}
	}
	
	//richiede una turnazione già memorizzata - faccio quella automatica
	@Test
	void testScambiaTurniTurnazioneEsistenteIdTurniValidi() {
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getListaTurni().isEmpty());
			}
			
			//creo i turni automoatici
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			
			assertTrue(TurnazioneModifier.getInstance().getListaTurni().size() == GestiMonSw.getInstance().getListaDipendenti().size() * 20);
			
			for (int i = 0; i < TurnazioneModifier.getInstance().getListaTurni().size(); i++) {
				assertTrue(TurnazioneModifier.getInstance().getDateTurniLavorativi().contains(TurnazioneModifier.getInstance().getListaTurni().get(i).getData()));
			}
					
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getListaTurni().size() == 20);
				assertTrue(d.getMonteOre(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0)) == 0);
			}
			
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			
			//cambio il primo turno con l'ultimo turno
			TurnoLavorativo tmp1 = new TurnoLavorativo(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(0).getData(), GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(0).getOraInizio(), GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(0).getId());
			tmp1.setDipendente(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(0).getDipendente());
			
			TurnoLavorativo tmp2 = new TurnoLavorativo(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() - 1).getData(), GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() - 1).getOraInizio(), GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() - 1).getId());
			tmp2.setDipendente(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() - 1).getDipendente());
					
			TurnazioneModifier.getInstance().scambiaTurni(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(0).getId(), GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() - 1).getId());
			
			TurnoLavorativo tmpAfter1 = GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(0);
			TurnoLavorativo tmpAfter2 = GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().size() - 1);
			
			assertTrue(tmp1.getDipendente().getListaTurni().size() == 20);
			assertTrue(tmp2.getDipendente().getListaTurni().size() == 20);
			
			assertNull(tmp1.getDipendente().getTurnoById(tmp1.getId()));
			assertNull(tmp2.getDipendente().getTurnoById(tmp2.getId()));
			
			assertTrue(tmpAfter1.getDipendente().equals(tmp2.getDipendente()));
			assertTrue(tmpAfter2.getDipendente().equals(tmp1.getDipendente()));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ci sono state eccezioni");
		}
	}
	
	@Test
	void testTl1Beforetl2TwoDaysSameDay() {
		assertFalse(TurnazioneModifier.getInstance().tl1Beforetl2TwoDaysTesting(tmpTl1, tmpTl2));		
	}
	
	@Test
	void testTl1Beforetl2TwoDaysDifferentDay() {
		tmpTl2.setData(tmpTl2.getData().plusDays(2));
		assertTrue(TurnazioneModifier.getInstance().tl1Beforetl2TwoDaysTesting(tmpTl1, tmpTl2));		
	}
}
