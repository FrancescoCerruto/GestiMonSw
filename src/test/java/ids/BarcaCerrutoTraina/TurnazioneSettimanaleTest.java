package ids.BarcaCerrutoTraina;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnazioneSettimanaleTest {
	
	TurnazioneSettimanale tmp;
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
		
		TurnazioneModifier.getInstance().addTurno(tmpTl1);
		TurnazioneModifier.getInstance().addTurno(tmpTl2);
		
		tmp = new TurnazioneSettimanale();
		tmp.addTurno(tmpTl1);
		tmp.addTurno(tmpTl2);
	}

	@Test
	void testSwapTurniIdValidi() {
		TurnoLavorativo test1 = new TurnoLavorativo(tmpTl1.getData(), tmpTl1.getOraInizio(), tmpTl1.getId());
		test1.setDipendente(tmpD2);
		TurnoLavorativo test2 = new TurnoLavorativo(tmpTl2.getData(), tmpTl2.getOraInizio(), tmpTl2.getId());
		test2.setDipendente(tmpD);
		try {
			
			tmp.swapTurni(tmpTl1.getId(), tmpTl2.getId());
		} catch (Exception e) {
			//non ci sono nuovi turni
			assertTrue(tmp.getTurniLavorativi().size() == 2);
			//uguaglianza turni su turnazione
			try {
				assertFalse(tmp.getTurnoById(tmpTl1.getId()).equals(test1));
				assertFalse(tmp.getTurnoById(tmpTl1.getId()).getDipendente().equals(tmpD2));
				assertFalse(tmp.getTurnoById(tmpTl2.getId()).equals(test2));
				assertFalse(tmp.getTurnoById(tmpTl2.getId()).getDipendente().equals(tmpD));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	void testSwapTurniIdNonValidi() {
		List<TurnoLavorativo> before = tmp.getTurniLavorativi();
		try {
			tmp.swapTurni(tmpTl1.getId() + 5, tmpTl2.getId() + 7);
		} catch (Exception e) {
			List<TurnoLavorativo> after = tmp.getTurniLavorativi();
			assertTrue(after.equals(before));
		}
	}
}
