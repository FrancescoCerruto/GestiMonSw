package ids.BarcaCerrutoTraina;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestiMonSwTest {
	Dipendente tmpD;
	Dipendente tmpD2;
	Dipendente tmpD3;
	Carriera tmpC;
	Carriera tmpC2;
	
	@BeforeEach
	void initGestiMonSw() {
		GestiMonSw.getInstance().clearAll();
		
		tmpD = new Dipendente("pippo", "franco", "dd", "ff", LocalDate.now());
		tmpD2 = new Dipendente("pippo", "franco", "cc", "ff", LocalDate.now());
		tmpD3 = new Dipendente("pippo", "franco", "ff", "ff", LocalDate.now());
		
		tmpC = new Carriera(LocalDate.now(), LocalDate.now(), 1, new Dipendente(tmpD.getDati().get(0), tmpD.getDati().get(1), tmpD.getDati().get(2), tmpD.getDati().get(3), LocalDate.parse(tmpD.getDati().get(4))));
		tmpC2 = new Carriera(LocalDate.now(), LocalDate.now(), 10, new Dipendente(tmpD.getDati().get(0), tmpD.getDati().get(1), tmpD.getDati().get(2), tmpD.getDati().get(3), LocalDate.parse(tmpD.getDati().get(4))));
		
		//tmpTl = new TurnoLavorativo(Date.valueOf(LocalDate.now()), 1);
		
		//tmpTs = new TurnazioneSettimanale();
	}
	

	@Test
	void testCheckEsistenzaDipendenteEmptyList() {
		assertFalse(GestiMonSw.getInstance().checkEsistenzaDipendente("ss"));
	}

	@Test
	void testCheckEsistenzaDipendenteNonMemorizzato() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		assertFalse(GestiMonSw.getInstance().checkEsistenzaDipendente("ss"));
	}

	@Test
	void testCheckEsistenzaDipendenteMemorizzato() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		assertTrue(GestiMonSw.getInstance().checkEsistenzaDipendente(tmpD.getCodiceFiscale()));
	}

	@Test
	void testCreaTurnazioneSettimanaleDipendentiEmpty() {
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
		} catch (Exception e) {
			assertNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
		}
	}
	
	@Test
	void testCreaTurnazioneSettimanaleUnoDipendenti() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
		} catch (Exception e) {
			assertNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
		}
	}

	@Test
	void testCreaTurnazioneSettimanaleDueDipendenti() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		
		//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
		} catch (Exception e) {
		}
	}

	@Test
	void testMemorizzaTurnazioneSettimanaleTurnazioneValida() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		
		//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		} catch (Exception e) {
		}
	}

	@Test
	void testMemorizzaTurnazioneSettimanaleTurnazioneNonValida() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		
		//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());

		try {
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			
			//inserisco un solo turno
			TurnazioneModifier.getInstance().inserisciTurno(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0), TurnazioneModifier.getInstance().getOraInizioTurniLavorativi().get(0));
			
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		} catch (Exception e) {
			//mi aspetto un dipendente totalmente disponibile e turni non utilizzati
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).getMonteOre(TurnazioneModifier.getInstance().getDateTurniLavorativi().get(0)) == 40);
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().size() > 0);
		}
	}

	@Test
	void testCreaDipendenteGiaEsistente() {
		try {
			//inserisco il primo dipendente (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
			assertTrue(tmpD.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
			//provo ad inserire di nuovo lo stesso dipendente (mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
		} catch (Exception e) {
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
		}
	}
	
	@Test
	void testCreaDipendenteNuovo() {
		try {
			//inserisco il primo dipendente (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
			assertTrue(tmpD.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
			//provo ad inserire il secondo dipendente (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "cc", "ff", LocalDate.now());
			assertTrue(tmpD2.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
		} catch (Exception e) {
		}
	}

	@Test
	void testCreaCarriera() {
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().creaCarriera(LocalDate.now(), LocalDate.now(), 1);
		assertTrue(tmpC.equals(GestiMonSw.getInstance().getCarrieraTmp()));
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 0);
	}

	@Test
	void testMemorizzaDatiDipendente() {
		//memorizzo il primo dipendente - mi aspetto una lista con dimensione 1 e che il primo dipendente in lista sia tmpD
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
	}

	@Test
	void testMemorizzaDatiCarriera() {
		GestiMonSw.getInstance().setCarrieraTmp(tmpC);
		GestiMonSw.getInstance().memorizzaDatiCarriera();
		
		assertNotNull(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()));
		assertTrue(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()).equals(tmpC));
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 1);
	}

	@Test
	void testRimuoviDipendenteEmptyList() {
		//provo a rimuovere dipendente da lista vuota - mi aspetto eccezione e nessuna modifica sulla lista
		List<Dipendente> beforeExecution1 = GestiMonSw.getInstance().getListaDipendenti();
		try {
			GestiMonSw.getInstance().rimuoviDipendente("ff");
		} catch (Exception e) {
			List<Dipendente> afterExecution1 = GestiMonSw.getInstance().getListaDipendenti();			
			assertTrue(afterExecution1.isEmpty());
			assertTrue(afterExecution1.equals(beforeExecution1));
		}
	}

	@Test
	void testRimuoviDipendenteNonMemorizzato() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		List<Dipendente> beforeExecution2 = GestiMonSw.getInstance().getListaDipendenti();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		try {
			GestiMonSw.getInstance().rimuoviDipendente(tmpD.getCodiceFiscale() + "E");
		} catch(Exception e) {
			List<Dipendente> afterExecution2 = GestiMonSw.getInstance().getListaDipendenti();
			assertTrue(afterExecution2.equals(beforeExecution2));			
		}
	}

	@Test
	void testRimuoviDipendenteMemorizzato() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere il dipendente appena inserito - non mi aspetto eccezione, mi aspetto modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		List<Dipendente> beforeExecution2 = GestiMonSw.getInstance().getListaDipendenti();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		try {
			GestiMonSw.getInstance().rimuoviDipendente(tmpD.getCodiceFiscale());
			List<Dipendente> afterExecution2 = GestiMonSw.getInstance().getListaDipendenti();
			assertTrue(afterExecution2.equals(beforeExecution2));			
		} catch(Exception e) {
		}
	}

	@Test
	void testCheckDisponibilitaCambioDipendenteNonMemorizzato() {
		//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
		try {
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//cambi non disponibili
			GestiMonSw.getInstance().checkDisponibilitaCambio(tmpD.getCodiceFiscale());
		} catch (Exception e) {
			assertNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()));
		}
	}
	
	@Test
	void testCheckDisponibilitaCambioDipendenteMemorizzatoNumeroUgualeA0() {
		//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
		try {
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//azzero i cambi
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).azzeraCambi();
			
			//cambi non disponibili
			assertNull(GestiMonSw.getInstance().checkDisponibilitaCambio(tmpD.getCodiceFiscale()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testCheckDisponibilitaCambioDipendenteMemorizzatoNumeroUgualeA1TurnazioneNonEsistente() {
		//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
		try {
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroCambi();
			
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//cambi disponibili
			Map<Integer, String> result = GestiMonSw.getInstance().checkDisponibilitaCambio(tmpD.getCodiceFiscale());
			assertTrue(result.get(1).equals(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testCheckDisponibilitaCambioDipendenteMemorizzatoNumeroUgualeA1TurnazioneEsistenteDisponibilitaIndicata() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		
			//setto l'id utente (tmpD2)
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD2.getCodiceFiscale());
			
			//creo una disponibilita parziale (tmpD2)
			GestiMonSw.getInstance().creaDisponibilita("parziale");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getDisponibilita());
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getListaCambi());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getListaCambi().size() == 0);
			
			//indico il mio primo turno come turno di disponibilita
			GestiMonSw.getInstance().addTurnoDisponibilita(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getListaTurni().get(0).getId());
			
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getListaCambi().size() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getListaTurni().get(0).equals(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getListaCambi().get(0)));
			
			//setto l'id utente (tmpD)
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//richiedo il cambio
			Map<Integer, String> result = GestiMonSw.getInstance().checkDisponibilitaCambio(tmpD.getCodiceFiscale());
			assertFalse(result.get(1).equals(""));
			assertFalse(result.get(2).equals(""));
		} catch (Exception e) {
		}
	}

	@Test
	void testCheckDisponibilitaCambioDipendenteMemorizzatoNumeroUgualeA1TurnazioneEsistenteDisponibilitaNonIndicata() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		
			//setto l'id utente (tmpD)
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//richiedo il cambio
			Map<Integer, String> result = GestiMonSw.getInstance().checkDisponibilitaCambio(tmpD.getCodiceFiscale());
			assertFalse(result.get(1).equals(""));
			assertTrue(result.get(2).equals(""));
		} catch (Exception e) {
		}
	}
	
	@Test
	void testCreaDisponibilitaDipendenteNonMemorizzato() {
		try {
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita nulla (sarebbe lo stesso risultato con gli altri tipi di disponibilita)
			GestiMonSw.getInstance().creaDisponibilita("nulla");
		} catch (Exception e) {
			assertNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()));
		}
	}

	@Test
	void testCreaDisponibilitaNullaDipendenteMemorizzato() {
		try {
			//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
			assertTrue(tmpD.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
			
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita nulla
			GestiMonSw.getInstance().creaDisponibilita("nulla");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita());
			assertNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita().getTurni());
		} catch (Exception e) {
		}
	}

	@Test
	void testCreaDisponibilitaTotaleDipendenteMemorizzato() {
		try {
			//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
			assertTrue(tmpD.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
			
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita totale
			GestiMonSw.getInstance().creaDisponibilita("totale");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().equals(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni()));
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().size() == GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().size());
		} catch (Exception e) {
		}
	}

	@Test
	void testCreaDisponibilitaParzialeDipendenteMemorizzato() {
		try {
			//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
			assertTrue(tmpD.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
			
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita parziale
			GestiMonSw.getInstance().creaDisponibilita("parziale");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita());
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().size() == 0);
		} catch (Exception e) {
		}
	}

	@Test
	void testAddTurnoDisponibilitaDipendenteNonMemorizzato() {
		try {
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//cerco di indicare un turno (id qualsiasi)
			GestiMonSw.getInstance().addTurnoDisponibilita(1);
		} catch (Exception e) {
			assertNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()));
		}
	}
	
	@Test
	void testAddTurnoDisponibilitaDipendenteMemorizzatoTurnazioneInesistente() {
		try {
			//inserisco il dipendente di riferimento (non mi aspetto un'eccezione)
			GestiMonSw.getInstance().creaDipendente("pippo", "franco", "dd", "ff", LocalDate.now());
			assertTrue(tmpD.equals(GestiMonSw.getInstance().getDipendenteTmp()));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 0);
			
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita parziale
			GestiMonSw.getInstance().creaDisponibilita("parziale");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().equals(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni()));
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().size() == 0);
			
			String response = GestiMonSw.getInstance().addTurnoDisponibilita(1);
			assertTrue(response.equals("Turno non trovato"));
		} catch (Exception e) {
		}
	}

	@Test
	void testAddTurnoDisponibilitaDipendenteMemorizzatoTurnazioneEsistenteIdentificativoValido() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita parziale
			GestiMonSw.getInstance().creaDisponibilita("parziale");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita());
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().size() == 0);
			
			//indico il mio primo turno come turno di disponibilita
			GestiMonSw.getInstance().addTurnoDisponibilita(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).getId());
			
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().size() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).equals(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().get(0)));
			
		} catch (Exception e) {
		}
	}
	
	@Test
	void testAddTurnoDisponibilitaDipendenteMemorizzatoTurnazioneEsistenteIdentificativoNonValido() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
		
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//creo una disponibilita parziale
			GestiMonSw.getInstance().creaDisponibilita("parziale");
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getDisponibilita());
			assertNotNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaCambi().size() == 0);
			
			//indico un turno con id insesistente (-1) come turno di disponibilita
			String response = GestiMonSw.getInstance().addTurnoDisponibilita(-1);
			
			assertTrue(response.equals("Turno non trovato"));
		} catch (Exception e) {
		}
	}
	
	@Test
	void testAddGiornoFerie0Ferie() {
		//memorizzo un nuovo dipendente
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		//utente utilizzato
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		
		//chiedo un giorno di ferie 
		Map<Integer, List<LocalDate>> beforeExecution = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getGiorniFerie();
		try {
			GestiMonSw.getInstance().addGiornoFerie(LocalDate.now());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			Map<Integer, List<LocalDate>> afterExecution = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getGiorniFerie();
			assertTrue(beforeExecution.equals(afterExecution));
		}
	}
	
	@Test
	void testAddGiornoFerieOkFerieGiornoPassato() {
		//memorizzo un nuovo dipendente
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		//utente utilizzato
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroFerie(1);
		
		//chiedo un giorno di ferie 
		int numeroFerieBefore = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie();
		try {
			GestiMonSw.getInstance().addGiornoFerie(LocalDate.now());
		} catch (IllegalArgumentException e) {
			assertTrue(numeroFerieBefore == GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie());
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddGiornoFerieOkFerieDomenicaSettimanaOk() {
		//memorizzo un nuovo dipendente
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		//utente utilizzato
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroFerie(1);
		
		//chiedo un giorno di ferie 
		int numeroFerieBefore = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie();
		try {
			GestiMonSw.getInstance().addGiornoFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
		} catch (IllegalArgumentException e) {
			assertTrue(numeroFerieBefore == GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie());
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddGiornoFerieOkFerieGiornoOk() {
		//memorizzo un nuovo dipendente
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		//utente utilizzato
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroFerie(1);
		
		//chiedo un giorno di ferie 
		int numeroFerieBefore = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie();
		try {
			GestiMonSw.getInstance().addGiornoFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie() == numeroFerieBefore - 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaSettimanaFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))).contains(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))));
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getMonteOre(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))) == 30);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddGiornoFerieOkFerieFullWeek() {
		//memorizzo un nuovo dipendente
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		//utente utilizzato
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroFerie(5);
		
		//chiedo 4 giorni di ferie 
		int numeroFerieBefore = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie();
		try {
			for (int i = 0; i < 4; i++) {
				GestiMonSw.getInstance().addGiornoFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.MONDAY.getValue() + i))));
				assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie() == numeroFerieBefore - 1);
				numeroFerieBefore = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie();
				assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaSettimanaFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))).contains(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.MONDAY.getValue() + i)))));				
			}
			GestiMonSw.getInstance().addGiornoFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(DayOfWeek.MONDAY.getValue() + 4))));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroFerie() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaSettimanaFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))).size() == 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//deve esistere una turnazione prima
	@Test
	void testAddTurnoPermessoIdInesistente() {
		int size = 0;
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//verifico inesistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 0);
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			
			//verifico esistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
		
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			//chiedo un permesso
			size = GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size();
			GestiMonSw.getInstance().addTurnoPermesso(-1);
		} catch (Exception e) {
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == size);
		}
	}
	
	//deve esistere una turnazione prima
	@Test
	void testAddTurnoPermessoIdEsistenteGiaEspresso() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//verifico inesistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 0);
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			
			//verifico esistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
		
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroPermessi();
			
			//chiedo il primo permesso
			GestiMonSw.getInstance().addTurnoPermesso(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).getId());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		
			//richiedo lo stesso permesso
			GestiMonSw.getInstance().addTurnoPermesso(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).getId());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		} catch (Exception e) {
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		}
	}

	//deve esistere una turnazione prima
	@Test
	void testAddTurnoPermessoIdEsistenteConsecutivo() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//verifico inesistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 0);
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			
			//verifico esistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
		
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroPermessi();
			
			//chiedo il primo permesso
			GestiMonSw.getInstance().addTurnoPermesso(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).getId());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		
			//chiedo il turno dopo
			GestiMonSw.getInstance().addTurnoPermesso(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(1).getId());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		} catch (Exception e) {
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		}
	}

	@Test
	void testAddTurnoPermessoIdEsistentePrecedente() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//verifico inesistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 0);
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			
			//verifico esistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
		
			//setto l'id utente
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroPermessi();
			
			//chiedo il primo permesso
			GestiMonSw.getInstance().addTurnoPermesso(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(1).getId());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		
			//chiedo il turno dopo
			GestiMonSw.getInstance().addTurnoPermesso(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().get(0).getId());
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		} catch (Exception e) {
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaPermessi().size() == 1);
		}
	}
	
	//deve esistere una turnazione
	@Test
	void testCheckPresenzaDipendenti() {
		try {
			//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
			GestiMonSw.getInstance().setDipendenteTmp(tmpD);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			
			//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
			assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
			assertNull(GestiMonSw.getInstance().getDipendenteTmp());
			
			//memorizzo il terzo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
			GestiMonSw.getInstance().setDipendenteTmp(tmpD3);
			GestiMonSw.getInstance().memorizzaDatiDipendente();
			
			
			//verifico inesistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 0);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getNumeroPermessi() == 0);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD3.getCodiceFiscale()).getNumeroPermessi() == 0);
			
			//creo una turnazione automatica
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			assertNotNull(GestiMonSw.getInstance().getTurnazioneSettimanale());
			assertTrue(GestiMonSw.getInstance().getTurnoScoperto().isEmpty());
			assertTrue(GestiMonSw.getInstance().getDipendentiDisponibili().isEmpty());
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			
			//verifico esistenza permesso
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).getNumeroPermessi() == 1);
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD3.getCodiceFiscale()).getNumeroPermessi() == 1);
			
			//faccio chiedere ad entrambi il turno di mattina di martedi (compresenza per turnazione automatica)
			//i primi 20 turni sono tutti del primo dipendente (random)
			//i secondi 20 turni sono tutti del secondo dipendente (random)
			//gli utlimi 20 turni sono tutti del terzo dipendente
			//per ogni dipendente i primi 15 sono i turni completi (lun, merc, ven) (mar, gio, sab)
			//i restanti 5 sono le mezze giornate (mar mattina, gio pomeriggio) (mer mattina, ven pomeriggio)

			//chiedo il primo permesso
			GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(15).getDipendente().inserisciPermesso(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(15).getId());
			
			//chiedo il secondo permesso
			GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(20).getDipendente().inserisciPermesso(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(15).getId());
			
			//chiedo il terzo permesso in compresenza
			assertFalse(GestiMonSw.getInstance().checkPresenzaDipendenti(GestiMonSw.getInstance().getTurnazioneSettimanale().getTurniLavorativi().get(55).getId()));
		} catch (Exception e) {
		}
	}
	
	@Test
	void testCreaRegistroMansioniPrimoTentativo() {
		assertNull(GestiMonSw.getInstance().getRegistroMansioni());
		GestiMonSw.getInstance().creaRegistroMansioni();
		assertNotNull(GestiMonSw.getInstance().getRegistroMansioni());
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 0);
	}

	//automaticamente ho testato anche addMansione
	@Test
	void testCreaRegistroMansioniSecondoTentativo() {
		assertNull(GestiMonSw.getInstance().getRegistroMansioni());
		GestiMonSw.getInstance().creaRegistroMansioni();
		assertNotNull(GestiMonSw.getInstance().getRegistroMansioni());
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 0);
		
		//aggiungo delle mansioni
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pippo", 1, 1));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pluto", 1, 2));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("paperino", 1, 3));
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 3);
		
		//provo a ricreare
		GestiMonSw.getInstance().creaRegistroMansioni();
		assertNotNull(GestiMonSw.getInstance().getRegistroMansioni());
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 3);		
	}
	
	@Test
	void testAddMansioneToDipendenteNonMemorizzato() {
		//inserisco delle mansioni
		assertNull(GestiMonSw.getInstance().getRegistroMansioni());
		GestiMonSw.getInstance().creaRegistroMansioni();
		assertNotNull(GestiMonSw.getInstance().getRegistroMansioni());
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 0);
		
		//aggiungo delle mansioni
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pippo", 1, 1));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pluto", 1, 2));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("paperino", 1, 3));
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 3);
		
		//assegno una mansione ad un dipendente non memorizzato
		try {
			int result = GestiMonSw.getInstance().addMansioneToDipendente(-1);			
		} catch (NullPointerException e) {
			assertNull(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()));
		}
	}
	
	@Test
	void testAddMansioneToDipendenteMemorizzatoIdNonValido() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getCaricoLavorativo() == 100);
	
		//inserisco delle mansioni
		assertNull(GestiMonSw.getInstance().getRegistroMansioni());
		GestiMonSw.getInstance().creaRegistroMansioni();
		assertNotNull(GestiMonSw.getInstance().getRegistroMansioni());
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 0);
		
		//aggiungo delle mansioni
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pippo", 1, 1));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pluto", 1, 2));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("paperino", 1, 3));
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 3);
		
		//assegno una mansione id non valido
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		
		int result = GestiMonSw.getInstance().addMansioneToDipendente(-1);
		assertTrue(result == Integer.MIN_VALUE);
	}
	
	@Test
	void testAddMansioneToDipendenteMemorizzatoIdValido() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getCaricoLavorativo() == 100);
	
		//inserisco delle mansioni
		assertNull(GestiMonSw.getInstance().getRegistroMansioni());
		GestiMonSw.getInstance().creaRegistroMansioni();
		assertNotNull(GestiMonSw.getInstance().getRegistroMansioni());
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 0);
		
		//aggiungo delle mansioni
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pippo", 1, 1));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("pluto", 1, 2));
		GestiMonSw.getInstance().getRegistroMansioni().memorizzaMansione(new Mansione("paperino", 1, 3));
		assertTrue(GestiMonSw.getInstance().getRegistroMansioni().getMansioni().size() == 3);
		
		//assegno una mansione id non valido
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		
		int result = GestiMonSw.getInstance().addMansioneToDipendente(1);
		assertTrue(result == GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getCaricoLavorativo());
	}

	@Test
	void testAddNotaDipendenteNonMemorizzato() {
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 0);
		
		try {
			GestiMonSw.getInstance().addNota(tmpD.getCodiceFiscale(), 1, 3);			
		} catch (NullPointerException e) {
			assertNull(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()));
		}
	}
	
	@Test
	void testAddNotaDipendenteMemorizzatoNoModificaStipendio() {
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 0);
		
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().setCarrieraTmp(tmpC);
		GestiMonSw.getInstance().memorizzaDatiCarriera();
		
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 1);
		assertTrue(GestiMonSw.getInstance().getCarriere().get(tmpD.getCodiceFiscale()).equals(tmpC));
		assertNull(GestiMonSw.getInstance().getCarrieraTmp());
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 1);
		
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		
		//assegno una mansione id non valido
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		
		GestiMonSw.getInstance().addNota(tmpD.getCodiceFiscale(), 1, 3);
		
		assertTrue(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()).getStipendio() == tmpC.getStipendio());
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getStorico(tmpD.getCodiceFiscale()).get(LocalDate.now()).size() == 2);
	}

	@Test
	void testAddNotaDipendenteMemorizzatoModificaStipendioAumentoNonRilevabile() {
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 0);
		
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().setCarrieraTmp(tmpC);
		GestiMonSw.getInstance().memorizzaDatiCarriera();
		
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 1);
		assertTrue(GestiMonSw.getInstance().getCarriere().get(tmpD.getCodiceFiscale()).equals(tmpC));
		assertNull(GestiMonSw.getInstance().getCarrieraTmp());
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 1);
		
		GestiMonSw.getInstance().addNota(tmpD.getCodiceFiscale(), 1, 4);
		
		assertTrue(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()).getStipendio() == (tmpC.getStipendio() + tmpC.getStipendio() * 20 / 100));
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getStorico(tmpD.getCodiceFiscale()).get(LocalDate.now()).size() == 2);
	}

	@Test
	void testAddNotaDipendenteMemorizzatoModificaStipendioAumentoRilevabile() {
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 0);
		
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().setCarrieraTmp(tmpC2);
		GestiMonSw.getInstance().memorizzaDatiCarriera();
		
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 1);
		assertTrue(GestiMonSw.getInstance().getCarriere().get(tmpD.getCodiceFiscale()).equals(tmpC2));
		assertNull(GestiMonSw.getInstance().getCarrieraTmp());
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 1);
		
		GestiMonSw.getInstance().addNota(tmpD.getCodiceFiscale(), 1, 4);
		
		assertTrue(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()).getStipendio() == (tmpC2.getStipendio() + tmpC2.getStipendio() * 20 / 100));
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getStorico(tmpD.getCodiceFiscale()).get(LocalDate.now()).size() == 3);
	}

	@Test
	void testAddNotaDipendenteMemorizzatoModificaStipendioDiminuzioneNonRilevabile() {
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 0);
		
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().setCarrieraTmp(tmpC);
		GestiMonSw.getInstance().memorizzaDatiCarriera();
		
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 1);
		assertTrue(GestiMonSw.getInstance().getCarriere().get(tmpD.getCodiceFiscale()).equals(tmpC));
		assertNull(GestiMonSw.getInstance().getCarrieraTmp());
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 1);
		
		GestiMonSw.getInstance().addNota(tmpD.getCodiceFiscale(), 2, 4);
		
		assertTrue(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()).getStipendio() == (tmpC.getStipendio() - tmpC.getStipendio() * 10 / 100));
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getStorico(tmpD.getCodiceFiscale()).get(LocalDate.now()).size() == 2);
	}

	@Test
	void testAddNotaDipendenteMemorizzatoModificaStipendioDiminuzioneRilevabile() {
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 0);
		
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		GestiMonSw.getInstance().setCarrieraTmp(tmpC2);
		GestiMonSw.getInstance().memorizzaDatiCarriera();
		
		assertTrue(GestiMonSw.getInstance().getCarriere().size() == 1);
		assertTrue(GestiMonSw.getInstance().getCarriere().get(tmpD.getCodiceFiscale()).equals(tmpC2));
		assertNull(GestiMonSw.getInstance().getCarrieraTmp());
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getMap().size() == 1);
		
		GestiMonSw.getInstance().addNota(tmpD.getCodiceFiscale(), 2, 4);
		
		assertTrue(GestiMonSw.getInstance().getCarrieraDipendente(tmpD.getCodiceFiscale()).getStipendio() == (tmpC2.getStipendio() - tmpC2.getStipendio() * 10 / 100));
		assertTrue(GestiMonSw.getInstance().getStoricoCarriere().getStorico(tmpD.getCodiceFiscale()).get(LocalDate.now()).size() == 3);
	}
	
	//deve esistere una turnazione
	@Test
	void testCheckGiornoFerieDipendentiStessaSettimana() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		
		//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		//memorizzo il terzo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD3);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		//Utente 1 utilizza il sistema - richiedo il primo lunedi disponibile
		GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
		GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroFerie(1);
		assertTrue(GestiMonSw.getInstance().checkGiornoFerieDipendenti(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))));
		try {
			GestiMonSw.getInstance().addGiornoFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
			
			//Utente 2 utilizza il sistema - richiedo il martedi successivo
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD2.getCodiceFiscale());
			GestiMonSw.getInstance().getDipendente(tmpD2.getCodiceFiscale()).setNumeroFerie(1);
			assertFalse(GestiMonSw.getInstance().checkGiornoFerieDipendenti(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY))));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testPianificazioneAutomaticaConFerie() {
		//inserisco un nuovo dipendente (memorizzato) (lista non vuota) - provo a rimuovere un dipendente non memorizzato - mi aspetto eccezione e nessuna modifica sulla lista
		GestiMonSw.getInstance().setDipendenteTmp(tmpD);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 1);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		
		//memorizzo il secondo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD2);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().size() == 2);
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(0).equals(tmpD));
		assertTrue(GestiMonSw.getInstance().getListaDipendenti().get(1).equals(tmpD2));
		assertNull(GestiMonSw.getInstance().getDipendenteTmp());
		
		//memorizzo il terzo dipendente - mi aspetto una lista con dimensione 2 e che il secondo dipendente in lista sia tmpD2 (il primo inviariato)
		GestiMonSw.getInstance().setDipendenteTmp(tmpD3);
		GestiMonSw.getInstance().memorizzaDatiDipendente();
		
		try {
			//avvio creazione turnazione
			GestiMonSw.getInstance().creaTurnazioneSettimanale();
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getMonteOre(TurnazioneModifier.getInstance().getDataLastTurno()) == 40);
			}
			
			//Utente 1 utilizza il sistema - richiedo il prossimo lunedi (stesso di pianificazione)
			GestiMonSw.getInstance().setIdentificativoUtenteDipendente(tmpD.getCodiceFiscale());
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).setNumeroFerie(1);
			GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).addFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaSettimanaFerie(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))).contains(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))));
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getMonteOre(TurnazioneModifier.getInstance().getDataLastTurno()) == 30);
			
			TurnazioneModifier.getInstance().inserisciTurniAutomatici();
			GestiMonSw.getInstance().memorizzaTurnazioneSettimanale();
			//tutti i dipendenti lavorano per 40 ore
			for (Dipendente d : GestiMonSw.getInstance().getListaDipendenti()) {
				assertTrue(d.getMonteOre(TurnazioneModifier.getInstance().getDataLastTurno()) == 0);
			}
			assertTrue(GestiMonSw.getInstance().getDipendente(tmpD.getCodiceFiscale()).getListaTurni().size() == 15);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
