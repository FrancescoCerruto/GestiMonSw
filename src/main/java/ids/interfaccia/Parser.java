package ids.interfaccia;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Parser {

    private ElencoComandi comandi;
	private static Parser singleton;
	
    public Parser() {
        comandi = new ElencoComandi();
    }

	public static Parser getInstance() {
		if (singleton==null)
			singleton=new Parser();
		return singleton;
	}

    public String read() {
        String inputLine = "";

        System.out.print(" > ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        }
        catch(java.io.IOException exc) {
            System.out.println ("Errore durante la lettura: " + exc.getMessage());
        }
		return inputLine;
    }
		
    public Comando getComando(int console) {
        String parola = read();
		Comando comando = null;
		
		if(comandi.comandoValido(parola,console)) {
			switch (console) {
			case ElencoComandi.HOME_PAGE:
				if (parola.equals("1")) {
					comando = new ComandoNuovaPianificazione();					
				} else {
					if (parola.equals("2")) {
						comando = new ComandoModificaPianificazione();
					} else {
						if (parola.equals("3")) {
							comando = new ComandoMemorizzaDipendente();
						} else {
							if (parola.equals("4")) {
								comando = new ComandoRimuoviDipendente();						
							} else {
								if (parola.equals("5")) {
									comando = new ComandoRichiediCambioTurno();
								} else {
									if (parola.equals("6")) {
										comando = new ComandoIndicaDisponibilitaSettimanale();
									} else {
										if (parola.equals("7")) {
											comando = new ComandoRichiestaFerie();
										} else {
											if (parola.equals("8")) {
												comando = new ComandoRichiestaPermessi();
											} else {
												if (parola.equals("9")) {
													comando = new ComandoPianificaMansioni();
												} else {
													if (parola.equals("10")) {
														comando = new ComandoScegliMansione();
													} else {
														if (parola.equals("11")) {
															comando = new ComandoModificaCarriera();
														} else {
															if (parola.equals("12")) {
																comando = new ComandoVisualizzaCarriera();
															} else {
																if (parola.equals("0")) {
																	comando = new ComandoEsci();
																}
															}
														}
													}
												}
											}
										}
									}
								}
								
							}
						}
					}
				}
				break;
			case ElencoComandi.NUOVA_PIANIFICAZIONE:
				if (parola.equals("1")) {
					comando = new ComandoPianificazioneManuale();					
				} else {
					if (parola.equals("2")) {
						comando = new ComandoPianificazioneAutomatica();						
					} else {
						if (parola.equals("0")) {
							comando = new ComandoEsci();							
						}
					}
				}
				break;
			case ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE:
				if (parola.equals("1")) {
					comando = new ComandoConfermaPianificazione();
				} else {					
					if (parola.equals("2")) {
						comando = new ComandoCreaTurno();
					}
				}
				break;
			case ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE_DIPENDENTE:
				if (parola.equals("1")) {
					comando = new ComandoRecuperaDipendentiTurnazione();					
				} else {
					if (parola.equals("2")) {					
						comando = new ComandoScegliDipendente();
					}					
				}
				break;
			case ElencoComandi.NUOVA_PIANIFICAZIONE_MANUALE_TERMINA_TURNO:
				if (parola.equals("1")) {
					comando = new ComandoScegliDipendente();					
				}
				break;
			case ElencoComandi.MEMORIZZA_DIPENDENTE:
				if (parola.equals("1")) {
					comando = new ComandoInserisciDipendente();					
				} else {
					if (parola.equals("2")) {
						comando = new ComandoTerminaInserimento();						
					}
				}
				break;
			case ElencoComandi.INSERISCI_DIPENDENTE:
				if (parola.equals("1")) {
					comando = new ComandoInserisciCarriera();					
				}
				break;
			case ElencoComandi.INSERISCI_CARRIERA:
				if (parola.equals("1")) {
					comando = new ComandoConfermaInserimento();					
				}
				break;
			case ElencoComandi.RIMUOVI_DIPENDENTE:
				if (parola.equals("1")) {
					comando = new ComandoCancellaDipendente();					
				} else {
					if (parola.equals("2")) {
						comando = new ComandoTerminaRimozione();					
					}
				}
				break;
			case ElencoComandi.MODIFICA_PIANIFICAZIONE:
				if (parola.equals("1")) {
					comando = new ComandoScegliTurni();					
				} else {
					if (parola.equals("0")) {
						comando = new ComandoEsci();					
					}
				}
				break;
			case ElencoComandi.NUOVA_PIANIFICAZIONE_AUTOMATICA:
				if (parola.equals("2")) {
					comando = new ComandoModificaPianificazione();					
				} else {
					if (parola.equals("0")) {
						comando = new ComandoEsci();					
					}
				}
				break;
			case ElencoComandi.OPZIONI_DISPONIBILITA:
				if (parola.equals("1")) {
					comando = new ComandoDisponibilitaNulla();
				} else {
					if (parola.equals("2")) {
						comando = new ComandoDisponibilitaTotale();
					} else {
						if (parola.equals("3")) {
							comando = new ComandoDisponibilitaParziale();
						}
					}
				}
				break;
			case ElencoComandi.INDICA_TURNO:
				if (parola.equals("1")) {
					comando = new ComandoIndicaTurno();
				} else {
					if (parola.equals("0")) {
						comando = new ComandoEsci();
					}
				}
				break;
			case ElencoComandi.RICHIEDI_CAMBIO_TURNO:
				if (parola.equals("1")) {
					comando = new ComandoSelezionaTurni();
				}
				break;
			case ElencoComandi.RICHIEDI_FERIE:
				if (parola.equals("1")) {
					comando = new ComandoIndicaGiornoFerie();
				} else {
					if (parola.equals("0") ) {
						comando = new ComandoEsci();
					}
				}
				break;
			case ElencoComandi.RICHIEDI_PERMESSO:
				if (parola.equals("1")) {
					comando = new ComandoSelezionaTurno();
				}
				break;
			case ElencoComandi.PUBBLICA_MANSIONI:
				if (parola.equals("1")) {
					comando = new ComandoCaricaFile();
				}
				break;
			case ElencoComandi.SCEGLI_MANSIONE:
				if (parola.equals("1")) {
					comando = new ComandoSelezionaMansione();
				}
				break;
			case ElencoComandi.MODIFICA_CARRIERA:
				if (parola.equals("1")) {
					comando = new ComandoScegliCarriera();
				}
				break;
			case ElencoComandi.VISUALIZZA_CARRIERA:
				if (parola.equals("1")) {
					comando = new ComandoVediCarriera();
				}
				break;
			}
		}
		if (comando == null) {
			comando = new ComandoNonRiconosciuto();
		}
       return comando;
    }
}
