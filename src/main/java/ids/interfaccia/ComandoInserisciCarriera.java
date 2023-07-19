package ids.interfaccia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.DipendenteHandler;

public class ComandoInserisciCarriera implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Inserisci carriera";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.INSERISCI_CARRIERA));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci data assunzione (AAAA/MM/DD) dipendente");
		String dataAssunzione = Parser.getInstance().read();
		System.out.println("Inserisci data scadenza contratto (AAAA/MM/DD) dipendente");
		String dataScadenzaContratto = Parser.getInstance().read();
		System.out.println("Inserisci stipendio dipendente");
		String stipendio = Parser.getInstance().read();
		
		String regexString = "\\d+";
		Pattern patternString = Pattern.compile(regexString);
		
		while (!patternString.matcher(stipendio).matches()) {
			System.out.println("Stipendio inserito non valido. Inserisci stipendio dipendente");
			stipendio = Parser.getInstance().read();
		}
		
		String regexData = 	"^\\d{4}/\\d{2}/\\d{2}$";
		Pattern patternData = Pattern.compile(regexData);
		
		while (!patternData.matcher(dataAssunzione).matches()) {
			System.out.println("Data di assunzione inserita non valido. Inserisci data di assunzione dipendente (AAAA/MM/DD)");
			dataAssunzione = Parser.getInstance().read();
		}
		while (!patternData.matcher(dataScadenzaContratto).matches()) {
			System.out.println("Data scadenza contratto inserita non valido. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
			dataScadenzaContratto = Parser.getInstance().read();
		}
		
		//ho dei valori plausibili per date e stipendio
		int stipendioValue = Integer.parseInt(stipendio);
		LocalDate dataAssunzioneDate = LocalDate.parse(dataAssunzione, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		LocalDate dataScadenzaContrattoDate = LocalDate.parse(dataScadenzaContratto, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		
		//controlli su data assunzione
		//non posso memorizzare assunzioni future
		while(dataAssunzioneDate.compareTo(LocalDate.now()) > 0) {
			System.out.println("Non puoi memorizzare assunzioni future. Inserisci data di assunzione dipendente (AAAA/MM/DD)");
			dataAssunzione = Parser.getInstance().read();
			while (!patternData.matcher(dataAssunzione).matches()) {
				System.out.println("Data assunzione inserita non valida. Inserisci data assunzione dipendente (AAAA/MM/DD)");
				dataAssunzione = Parser.getInstance().read();
			}
			dataAssunzioneDate = LocalDate.parse(dataAssunzione, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		}
		
		//controlli su data scadenza contratto
		//non posso licenziare ancora prima di assumere
		while (dataScadenzaContrattoDate.compareTo(dataAssunzioneDate) < 0) {
			System.out.println("Data scadenza contratto prima di data assunzione. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
			dataScadenzaContratto = Parser.getInstance().read();
			while (!patternData.matcher(dataScadenzaContratto).matches()) {
				System.out.println("Data scadenza contratto inserita non valido. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
				dataScadenzaContratto = Parser.getInstance().read();
			}
			dataScadenzaContrattoDate = LocalDate.parse(dataScadenzaContratto, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		}
		//non posso licenziare prima che sia passato 1 anno
		int diffYear = dataScadenzaContrattoDate.getYear() - dataAssunzioneDate.getYear();
		if ((dataAssunzioneDate.getMonthValue() > dataScadenzaContrattoDate.getMonthValue()) ||
			((dataAssunzioneDate.getMonthValue() == dataScadenzaContrattoDate.getMonthValue()) && (dataAssunzioneDate.getDayOfMonth() > dataScadenzaContrattoDate.getDayOfMonth()))) {
			diffYear--;
		}
		
		while (diffYear < 1) {
			System.out.println("Durata minima contrattuale non rispettata. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
			dataScadenzaContratto = Parser.getInstance().read();
			while (!patternData.matcher(dataScadenzaContratto).matches()) {
				System.out.println("Data scadenza contratto inserita non valido. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
				dataScadenzaContratto = Parser.getInstance().read();
			}
			dataScadenzaContrattoDate = LocalDate.parse(dataScadenzaContratto, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			
			//non posso licenziare ancora prima di assumere
			while (dataScadenzaContrattoDate.compareTo(dataAssunzioneDate) <= 0) {
				System.out.println("Data scadenza contratto prima di data assunzione. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
				dataScadenzaContratto = Parser.getInstance().read();
				while (!patternData.matcher(dataScadenzaContratto).matches()) {
					System.out.println("Data scadenza contratto inserita non valido. Inserisci data di scadenza contratto dipendente (AAAA/MM/DD)");
					dataScadenzaContratto = Parser.getInstance().read();
				}
				dataScadenzaContrattoDate = LocalDate.parse(dataScadenzaContratto, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			}
			//scadenza contrattuale
			diffYear = dataScadenzaContrattoDate.getYear() - dataAssunzioneDate.getYear();
			if ((dataAssunzioneDate.getMonthValue() > dataScadenzaContrattoDate.getMonthValue()) ||
				((dataAssunzioneDate.getMonthValue() == dataScadenzaContrattoDate.getMonthValue()) && (dataAssunzioneDate.getDayOfMonth() > dataScadenzaContrattoDate.getDayOfMonth()))) {
				diffYear--;
			}
		}
		
		DipendenteHandler.getInstance().inserisciDatiCarriera(dataAssunzioneDate, dataScadenzaContrattoDate, stipendioValue);
		
		stampaComandi();
		Comando comando = Parser.getInstance().getComando(ElencoComandi.INSERISCI_CARRIERA);
		comando.esegui();
	}
}
