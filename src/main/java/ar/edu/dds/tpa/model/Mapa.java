package ar.edu.dds.tpa.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import componentes_externos.BuscadorExterno;
import componentes_externos.BuscadorDeCGPLento;
import componentes_externos.BuscadorDeCGPLento_Impostor;

public class Mapa {
	
	private List<PuntoDeInteres> puntosDeInteres = new ArrayList<>();
	private List<BuscadorExterno> buscadoresExternos = new ArrayList<>();
	private List<PuntoDeInteres> resultadosExternos = new ArrayList<>();
	
	
	public void agregarPuntoInteres(PuntoDeInteres UnPunto) {
		puntosDeInteres.add(UnPunto);
	}
	
	public List<PuntoDeInteres> buscarPorTextoLibre(String unaFrase){

		List<String> palabrasClave = Arrays.asList(unaFrase.split(" "));
		
		List<PuntoDeInteres> resultado = puntosDeInteres.stream().filter(punto -> palabrasClave.stream().anyMatch(palabra -> punto.condicionDeBusqueda(palabra))).collect(Collectors.toList());
		
		buscadoresExternos.stream().forEach(buscador -> buscador.buscar(palabrasClave, this));
		
		return resultado; 
	}


	public void busquedaTerminada(BuscadorExterno buscador) {
		// TODO Agregar a los resultados
		resultadosExternos.addAll(buscador.getPuntosDeInteres());
	}
	
	public void agregarBuscadorExterno(BuscadorExterno buscador){
		buscadoresExternos.add(buscador);
	}
	
	public List<PuntoDeInteres> getResultadosExternos() {
		return resultadosExternos;
	}
}


	

