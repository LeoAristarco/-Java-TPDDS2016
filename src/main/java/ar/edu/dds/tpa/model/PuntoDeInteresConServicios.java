package ar.edu.dds.tpa.model;

import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;

import ar.edu.dds.tpa.geolocalizacion.Posicion;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PuntoDeInteresConServicios extends PuntoDeInteres {

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "puntoDeInteres_id")
	private Set<Servicio> servicios;

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public PuntoDeInteresConServicios() {
		servicios = new HashSet<Servicio>();
	}

	public PuntoDeInteresConServicios(String nombre, Posicion coordenadas,String direccion) {
		super(nombre, coordenadas,direccion);
		servicios = new HashSet<Servicio>();
	}

	public void agregarServicio(Servicio unServicio) {
		servicios.add(unServicio);
		super.agregarPalabraClave(unServicio.getNombre());
	}

	@Override
	public boolean estaDisponibleEn(LocalDateTime unDiaYHorario) {
		return servicios.stream().anyMatch(servicio -> servicio.atiendeEn(unDiaYHorario));
	}

	public boolean estaDisponibleEn(LocalDateTime unDiaYHorario, String nombreDelServicio) {
		return servicios.stream().filter(servicio -> servicio.getNombre().equalsIgnoreCase(nombreDelServicio))
				.anyMatch(servicio -> servicio.atiendeEn(unDiaYHorario));
	}
}