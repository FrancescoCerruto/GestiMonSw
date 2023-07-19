package ids.interfaccia;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.DipendenteHandler;
import it.okkam.validation.FiscalCodeConf;
import it.okkam.validation.FiscalCodeValidator;

public class ComandoInserisciDipendente implements Comando  {
	String localFile = "CodiciBelfiore.txt";
    int maxComuneNameLength = 100;
    String maleValue = "M";
    int yearStart = 8;
    int yearEnd = 10;
    int monthStart = 3;
    int monthEnd = 5;
    int dayStart = 0;
    int dayEnd = 2;
    String codiciIstatStr;

	private String readLocalFile(String filePath) throws IOException, URISyntaxException {
		return new String(Files.readAllBytes(
	        Paths.get(System.getProperty("user.dir") + "/src/main/resources/" + filePath)));
	}
	
	
	public static final String codiceComando="1";
	public static final String descrizioneComando="Inserisci dipendente";

	private void stampaComandi() {
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.INSERISCI_DIPENDENTE));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci nome dipendente");
		String nome = Parser.getInstance().read();

		String regexString = ".*\\d.*";
		Pattern patternString = Pattern.compile(regexString);
		
		while (patternString.matcher(nome).matches()) {
			System.out.println("Nome inserito non valido. Inserisci nome dipendente");
			nome = Parser.getInstance().read();
		}
		
		System.out.println("Inserisci cognome dipendente");
		String cognome = Parser.getInstance().read();

		while (patternString.matcher(cognome).matches()) {
			System.out.println("Cognome inserito non valido. Inserisci cognome dipendente");
			cognome = Parser.getInstance().read();
		}
		
		
		
		System.out.println("Inserisci luogo di nascita dipendente");
		String luogoNascita = Parser.getInstance().read();

		while (patternString.matcher(luogoNascita).matches()) {
			System.out.println("Luogo di nascita inserito non valido. Inserisci luogo di nascita dipendente");
			luogoNascita = Parser.getInstance().read();
		}
		
		System.out.println("Inserisci sesso del dipendente (M F)");
		String sesso = Parser.getInstance().read();
		
		while((!sesso.equals("M")) && (!sesso.equals("F"))) {
			System.out.println("Identificativo sesso non valido. Inserisci sesso del dipendente (M F)");
			sesso = Parser.getInstance().read();
		}
		
		System.out.println("Inserisci data di nascita dipendente (GG/MM/AAAA)");
		String dataNascita = Parser.getInstance().read();
		
		String regexData = 	"^\\d{2}/\\d{2}/\\d{4}$";
		Pattern patternData = Pattern.compile(regexData);
		
		while (!patternData.matcher(dataNascita).matches()) {
			System.out.println("Data di nascita inserita non valida. Inserisci data di nascita dipendente (DD/MM/AAAA)");
			dataNascita = Parser.getInstance().read();
		}
		
		try {
			codiciIstatStr = readLocalFile(localFile);
			FiscalCodeConf configuration = FiscalCodeValidator.getFiscalCodeConf(codiciIstatStr,
			        maxComuneNameLength, maleValue, yearStart, yearEnd, monthStart, monthEnd, dayStart, dayEnd);
			int status = 0;
			String codiceFiscale = "";
			while (status == 0) {
				System.out.println("Inserisci codice fiscale dipendente");
				codiceFiscale = Parser.getInstance().read();
				
				String[] codiciFiscaliCalcolati = FiscalCodeValidator.calcoloCodiceFiscale(configuration, cognome, nome, dataNascita, luogoNascita, sesso);
				if (codiciFiscaliCalcolati == null) {
					status = -1;
				} else {
					for (int i = 0; i < codiciFiscaliCalcolati.length; i++) {
						if(codiciFiscaliCalcolati[i].toUpperCase().equals(codiceFiscale.toUpperCase())) {
							status = 1;
						}
					}
					if (status == 0) {
						System.out.println("Codice fiscale fornito non corretto");
					}
				}				
			}
			if (status == -1) {
				System.out.println("Dati inseriti non validi - ritorno alla home page");
			} else {
				if (status == 1) {
					try {
						DipendenteHandler.getInstance().inserisciDatiDipendente(nome, cognome, codiceFiscale, luogoNascita, LocalDate.parse(dataNascita, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
						stampaComandi();
						Comando comando = Parser.getInstance().getComando(ElencoComandi.INSERISCI_DIPENDENTE);
						comando.esegui();
					} catch (Exception e) {
						System.out.println("Esiste già un dipendente registrato con questo codice fiscale");
					}
				}
			}
		} catch (IOException | URISyntaxException e1) {
			System.out.println("Non è stato possibile calcolare il codice fiscale - torno alla home page");
		}
	}
}
