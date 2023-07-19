package ids.interfaccia;

public class GestiMonConsole {
	public void start() {
		stampaBenvenuto();
		Comando comando = Parser.getInstance().getComando(ElencoComandi.HOME_PAGE);
		
		while (!comando.getCodiceComando().equals("0")) {
			comando.esegui();
			System.out.println();
			stampaBenvenuto();
			comando = Parser.getInstance().getComando(ElencoComandi.HOME_PAGE);
		}
		comando.esegui(); // sicuramente è il comando esci
		System.out.println("   BYE...");
	}

    private void stampaBenvenuto() {
        System.out.println("GESTIMON SOFTWARE");
		System.out.println(ElencoComandi.elencoTuttiComandi(ElencoComandi.HOME_PAGE));
	}
}