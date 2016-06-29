package ar.edu.dds.tpa.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

public class Poligono {
	private List<Posicion> superficie;
	
	public Poligono() {
		this.superficie = new ArrayList<Posicion>();
	}
	
	public Poligono(List<Posicion> superficie) {
		this.superficie = superficie;
	}

	public void agregarCoordenada(Posicion unaCoordenada) {
		superficie.add(unaCoordenada);
	}

	// TODO Usar operaciones sobre listas, pero esto estaba en la libreria de
	// geodds que nos dieron!!
	public boolean incluyeA(Posicion unaCoordenada) {
		int j = this.superficie.size() - 1;
		boolean estaIncluido = false;

		for (int i = 0; i < this.superficie.size(); i++) {
			double verticeIY = superficie.get(i).getLatitud();
			double verticeIX = superficie.get(i).getLongitud();
			double verticeJY = superficie.get(j).getLatitud();
			double verticeJX = superficie.get(j).getLongitud();

			if ((verticeIY < unaCoordenada.getLatitud() && verticeJY >= unaCoordenada.getLatitud()
					|| verticeJY < unaCoordenada.getLatitud() && verticeIY >= unaCoordenada.getLatitud())
					&& (verticeIX <= unaCoordenada.getLongitud() || verticeJX <= unaCoordenada.getLongitud())) {
				if (verticeIX + (unaCoordenada.getLatitud() - verticeIY) / (verticeJY - verticeIY)
						* (verticeJX - verticeIX) < unaCoordenada.getLongitud()) {
					estaIncluido = !estaIncluido;
				}
			}
			j = i;
		}
		return estaIncluido;
	}
}
