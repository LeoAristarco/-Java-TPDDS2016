package ar.edu.dds.tpa.criterio;

import ar.edu.dds.tpa.model.usuario.Terminal;

public class SinFiltrado implements Criterio {

	public boolean filtrarTerminales(Terminal terminal) {
		return true;
	}
}