package ids.BarcaCerrutoTraina;

import java.util.ArrayList;
import java.util.List;

public class Disponibilita {
	private String tipo;
	private List<TurnoLavorativo> turniDisponibili = null;
	
	public Disponibilita(String tipo) {
		this.tipo = tipo;
		if (!tipo.equals("nulla")) {
			turniDisponibili = new ArrayList<>();
			turniDisponibili.clear();
		}
	}
	
	public void addTurnoDisponibile(TurnoLavorativo tl) {
		turniDisponibili.add(tl);
	}

	public List<TurnoLavorativo> getTurni() {
		return turniDisponibili;
	}
}
