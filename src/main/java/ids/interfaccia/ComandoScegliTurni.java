package ids.interfaccia;

import java.util.regex.Pattern;

import ids.BarcaCerrutoTraina.TurniHandler;

public class ComandoScegliTurni implements Comando  {
	public static final String codiceComando="1";
	public static final String descrizioneComando="Scegli turni";
	
	private void stampaComandi(int console) {
		System.out.println(ElencoComandi.elencoTuttiComandi(console));
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}
	
	public String getDescrizioneComando() {
		return descrizioneComando;
	}
	
	public void esegui() {
		System.out.println("Inserisci id del turno 1");
		String id1 = Parser.getInstance().read();
		String regexString = "\\d+";
		Pattern patternString = Pattern.compile(regexString);
		
		while (!patternString.matcher(id1).matches()) {
			System.out.println("Id inserito non valido. Inserisci id del turno 1");
			id1 = Parser.getInstance().read();
		}
		
		System.out.println("Inserisci id del turno 2");
		String id2 = Parser.getInstance().read();
		
		while (!patternString.matcher(id2).matches()) {
			System.out.println("Id inserito non valido. Inserisci id del turno 2");
			id2 = Parser.getInstance().read();
		}
		
		try {
			String descrizione = TurniHandler.getInstance().scegliTurni(Integer.parseInt(id1), Integer.parseInt(id2));
			System.out.println(descrizione);
			stampaComandi(ElencoComandi.MODIFICA_PIANIFICAZIONE);
			Comando comando = Parser.getInstance().getComando(ElencoComandi.MODIFICA_PIANIFICAZIONE);
			comando.esegui();
		} catch (NullPointerException e) {
			System.out.println("Non esiste una pianificazione da modificare");
			stampaComandi(ElencoComandi.HOME_PAGE);
			Comando comando = Parser.getInstance().getComando(ElencoComandi.HOME_PAGE);
			comando.esegui();
		} catch (Exception e) {
			System.out.println("Non esistono turni con gli identificativi forniti - torno alla home page");
		}
	}
}
