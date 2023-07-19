package ids.interfaccia;

class ElencoComandi {

	public static final int HOME_PAGE = 0;
	public static final int NUOVA_PIANIFICAZIONE = 1;
	public static final int NUOVA_PIANIFICAZIONE_MANUALE = 2;
	public static final int NUOVA_PIANIFICAZIONE_MANUALE_DIPENDENTE = 3;
	public static final int NUOVA_PIANIFICAZIONE_MANUALE_TERMINA_TURNO = 4;
	public static final int MEMORIZZA_DIPENDENTE = 5;
	public static final int INSERISCI_DIPENDENTE = 6;
	public static final int INSERISCI_CARRIERA = 7;
	public static final int RIMUOVI_DIPENDENTE = 8;
	public static final int MODIFICA_PIANIFICAZIONE = 9;
	public static final int NUOVA_PIANIFICAZIONE_AUTOMATICA = 10;
	public static final int OPZIONI_DISPONIBILITA = 11;
	public static final int INDICA_TURNO = 12;
	public static final int RICHIEDI_CAMBIO_TURNO = 13;
	public static final int RICHIEDI_FERIE = 14;
	public static final int RICHIEDI_PERMESSO = 15;
	public static final int PUBBLICA_MANSIONI = 16;
	public static final int SCEGLI_MANSIONE = 17;
	public static final int MODIFICA_CARRIERA = 18;
	public static final int VISUALIZZA_CARRIERA = 19;

	/* MENU' PRINCIPALE : NUOVA PIANIFICAZIONE, MEMORIZZA E RIMUOVI DIPENDNETE, RICHIEDI CAMBIO TURNO, INDICA DISPONIBILITA SETTIMANALE E ESCI */
	private static final String comandiHomePage[][] = {
			{ComandoNuovaPianificazione.codiceComando,ComandoNuovaPianificazione.descrizioneComando},
			{ComandoModificaPianificazione.codiceComando,ComandoModificaPianificazione.descrizioneComando},
			{ComandoMemorizzaDipendente.codiceComando,ComandoMemorizzaDipendente.descrizioneComando},
			{ComandoRimuoviDipendente.codiceComando,ComandoRimuoviDipendente.descrizioneComando},
			{ComandoRichiediCambioTurno.codiceComando,ComandoRichiediCambioTurno.descrizioneComando},
			{ComandoIndicaDisponibilitaSettimanale.codiceComando,ComandoIndicaDisponibilitaSettimanale.descrizioneComando},
			{ComandoRichiestaFerie.codiceComando,ComandoRichiestaFerie.descrizioneComando},
			{ComandoRichiestaPermessi.codiceComando,ComandoRichiestaPermessi.descrizioneComando},
			{ComandoPianificaMansioni.codiceComando,ComandoPianificaMansioni.descrizioneComando},
			{ComandoScegliMansione.codiceComando,ComandoScegliMansione.descrizioneComando},
			{ComandoModificaCarriera.codiceComando,ComandoModificaCarriera.descrizioneComando},
			{ComandoVisualizzaCarriera.codiceComando,ComandoVisualizzaCarriera.descrizioneComando},
			{ComandoEsci.codiceComando,ComandoEsci.descrizioneComando}
	};

	/* USE CASE 3 : SELEZIONE TIPO PIANIFICAZIONE */
	private static final String comandiNuovaPianificazione[][] = {
			{ComandoPianificazioneManuale.codiceComando, ComandoPianificazioneManuale.descrizioneComando},
			{ComandoPianificazioneAutomatica.codiceComando, ComandoPianificazioneAutomatica.descrizioneComando}
	};

	/* USE CASE 3 : PIANIFICAZIONE MANUALE HOME : CREAZIONE TURNO E CONFERMA PIANIFICAZIONE*/
	private static final String comandiNuovaPianificazioneManuale[][] = {
			{ComandoConfermaPianificazione.codiceComando,ComandoConfermaPianificazione.descrizioneComando},
			{ComandoCreaTurno.codiceComando,ComandoCreaTurno.descrizioneComando}
	};

	/* USE CASE 3 : PIANIFICAZIONE MANUALE SET DIPENDENTE : RECUPERO DIPENDENTE*/
	private static final String comandiNuovaPianificazioneManualeDipendente[][] = {
			{ComandoRecuperaDipendentiTurnazione.codiceComando,ComandoRecuperaDipendentiTurnazione.descrizioneComando}
	};
	
	/* USE CASE 3 : PIANIFICAZIONE MANUALE SET DIPENDENTE : SCEGLI DIPENDENTE*/
	private static final String comandiNuovaPianificazioneManualeTurno[][] = {
			{ComandoScegliDipendente.codiceComando,ComandoScegliDipendente.descrizioneComando}
	};
	
	/* USE CASE 1: MEMORIZZA DIPENDENTE : INSERISCI E TERMINA */
	private static final String comandiMemorizzaDipendente[][] = {
			{ComandoInserisciDipendente.codiceComando,ComandoInserisciDipendente.descrizioneComando},
			{ComandoTerminaInserimento.codiceComando,ComandoTerminaInserimento.descrizioneComando}
	};
	
	/* USE CASE 1: INSERISCI DIPENDENTE : INSERISCI CARRIERA */
	private static final String comandiInserisciDipendente[][] = {
			{ComandoInserisciCarriera.codiceComando,ComandoInserisciCarriera.descrizioneComando}
	};
	
	/* USE CASE 1 : INSERISCI CARRIERA : CONFERMA */
	private static final String comandiInserisciCarriera[][] = {
			{ComandoConfermaInserimento.codiceComando,ComandoConfermaInserimento.descrizioneComando}
	};
	
	/* USE CASE 2 : RIMUOVI DIPENDENTE */
	private static final String comandiRimuoviDipendente[][] = {
			{ComandoCancellaDipendente.codiceComando,ComandoCancellaDipendente.descrizioneComando},
			{ComandoTerminaRimozione.codiceComando,ComandoTerminaRimozione.descrizioneComando}
	};
	
	/* USE CASE 4 : MODIFICA PIANIFICAZIONE */
	private static final String comandiModificaPianificazione[][] = {
			{ComandoScegliTurni.codiceComando,ComandoScegliTurni.descrizioneComando},
			{ComandoEsci.codiceComando,ComandoEsci.descrizioneComando}
	};
	
	/* USE CASE 3 : PIANIFICAZIONE AUTOMATICA */
	private static final String comandiNuovaPianificazioneAutomatica[][] = {
			{ComandoModificaPianificazione.codiceComando,ComandoModificaPianificazione.descrizioneComando},
			{ComandoEsci.codiceComando,ComandoEsci.descrizioneComando}
	};
	
	/* USE CASE 6 : INDICA DISPONIBILITA SETTIMANALE */
	private static final String comandiIndicaDisponibilitaSettimanale[][] = {
			{ComandoDisponibilitaNulla.codiceComando,ComandoDisponibilitaNulla.descrizioneComando},
			{ComandoDisponibilitaTotale.codiceComando,ComandoDisponibilitaTotale.descrizioneComando},
			{ComandoDisponibilitaParziale.codiceComando,ComandoDisponibilitaParziale.descrizioneComando}
	};
	
	/* USE CASE 6 : INDICAZIONE DEL TURNO */
	private static final String comandiIndicaTurno[][] = {
			{ComandoIndicaTurno.codiceComando,ComandoIndicaTurno.descrizioneComando},
			{ComandoEsci.codiceComando,ComandoEsci.descrizioneComando}
	};
	
	/* USE CASE 5 : RICHIESTA CAMBIO TURNO */
	private static final String comandiCambioTurno[][] = {
			{ComandoScegliTurni.codiceComando,ComandoScegliTurni.descrizioneComando}
	};
	
	/* USE CASE 7 : RICHIESTA FERIE */
	private static final String comandiRichiestaFerie[][] = {
			{ComandoIndicaGiornoFerie.codiceComando,ComandoIndicaGiornoFerie.descrizioneComando},
			{ComandoEsci.codiceComando, ComandoEsci.descrizioneComando}
	};

	/* USE CASE 8 : RICHIESTA PERMESSO */
	private static final String comandiRichiestaPermesso[][] = {
			{ComandoSelezionaTurno.codiceComando,ComandoSelezionaTurno.descrizioneComando}
	};

	/* USE CASE 9 : PIANIFICA MANSIONI */
	private static final String comandiPianificaMansioni[][] = {
			{ComandoCaricaFile.codiceComando,ComandoCaricaFile.descrizioneComando}
	};
	
	/* USE CASE 10 : SCEGLI MANSIONE */
	private static final String comandiScegliMansione[][] = {
			{ComandoSelezionaMansione.codiceComando,ComandoSelezionaMansione.descrizioneComando}
	};
	
	/* USE CASE 11 : MODIFICA CARRIERA */
	private static final String comandiModificaCarriera[][] = {
			{ComandoScegliCarriera.codiceComando,ComandoScegliCarriera.descrizioneComando}
	};
	
	/* USE CASE 12 : VISUALIZZA CARRIERA */
	private static final String comandiVisualizzaCarriera[][] = {
			{ComandoVediCarriera.codiceComando,ComandoVediCarriera.descrizioneComando}
	};
	
	public static String elencoTuttiComandi(int console){
		int i=0;
		StringBuffer elenco = new StringBuffer();
		String comandi[][]=getComandi(console);


		for (i=0; i<comandi.length-1; i++) {
			elenco.append(comando(i,console));
			elenco.append("\n");
		}
		elenco.append(comando(i,console));
		return elenco.toString();
	}

	private static String comando(int i, int console) {
		String comandi[][]= getComandi(console);
		return " " + comandi[i][0] + ")" + comandi[i][1];
	}

	public static String[][] getComandi(int console){

		String comandi[][]=null;

		switch (console){
		case HOME_PAGE: 
			comandi = comandiHomePage;
			break;
		case NUOVA_PIANIFICAZIONE: 
			comandi = comandiNuovaPianificazione; 
			break;
		case NUOVA_PIANIFICAZIONE_MANUALE: 
			comandi = comandiNuovaPianificazioneManuale;
			break;
		case NUOVA_PIANIFICAZIONE_MANUALE_DIPENDENTE: 
			comandi = comandiNuovaPianificazioneManualeDipendente; 
			break;
		case NUOVA_PIANIFICAZIONE_MANUALE_TERMINA_TURNO:
			comandi = comandiNuovaPianificazioneManualeTurno;
			break;
		case MEMORIZZA_DIPENDENTE:
			comandi = comandiMemorizzaDipendente;
			break;
		case INSERISCI_DIPENDENTE:
			comandi = comandiInserisciDipendente;
			break;
		case INSERISCI_CARRIERA:
			comandi = comandiInserisciCarriera;
			break;
		case RIMUOVI_DIPENDENTE:
			comandi = comandiRimuoviDipendente;
			break;
		case MODIFICA_PIANIFICAZIONE:
			comandi = comandiModificaPianificazione;
			break;
		case NUOVA_PIANIFICAZIONE_AUTOMATICA:
			comandi = comandiNuovaPianificazioneAutomatica;
			break;
		case OPZIONI_DISPONIBILITA:
			comandi = comandiIndicaDisponibilitaSettimanale;
			break;
		case INDICA_TURNO:
			comandi = comandiIndicaTurno;
			break;
		case RICHIEDI_CAMBIO_TURNO:
			comandi = comandiCambioTurno;
			break;
		case RICHIEDI_FERIE:
			comandi = comandiRichiestaFerie;
			break;
		case RICHIEDI_PERMESSO:
			comandi = comandiRichiestaPermesso;
			break;
		case PUBBLICA_MANSIONI:
			comandi = comandiPianificaMansioni;
			break;
		case SCEGLI_MANSIONE:
			comandi = comandiScegliMansione;
			break;
		case MODIFICA_CARRIERA:
			comandi = comandiModificaCarriera;
			break;
		case VISUALIZZA_CARRIERA:
			comandi = comandiVisualizzaCarriera;
			break;
		};
		return comandi;
	}

	public boolean comandoValido(String stringa, int console) {
		String comandi[][]= getComandi(console);
		for(int i = 0; i < comandi.length; i++) {
			if(comandi[i][0].equals(stringa))
				return true;
		}
		return false;
	}
}
