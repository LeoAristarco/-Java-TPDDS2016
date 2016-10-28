package ar.edu.dds.tpa.persistencia;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.dds.tpa.adapter.EnviadorDeMail;
import ar.edu.dds.tpa.geolocalizacion.Posicion;
import ar.edu.dds.tpa.model.Administrador;
import ar.edu.dds.tpa.model.Comuna;
import ar.edu.dds.tpa.model.Usuario;
import ar.edu.dds.tpa.observer.NotificadorDeBusquedaLenta;
import ar.edu.dds.tpa.service.MailServiceImpostor;

public class PersistenciaDeUsuariosTest implements Persistible {

	private static Comuna flores;
	private static Comuna caballito;
	private static Comuna villaLugano;

	private static boolean seInstancio = false;;

	@Before
	public void inicializar() {
		if (!seInstancio) {
			flores = new Comuna(7, "Flores");
			caballito = new Comuna(9, "Caballito");
			villaLugano = new Comuna(14, "Villa Lugano");
			seInstancio = true;
		}
	}

	@Test
	public void persistenciaDeNombreDeUsuario() {
		Usuario usuarioDePrueba1 = new Usuario("Terminal Subte Linea E Virreyes", new Posicion(2.42300, 2.04212),
				flores);
		repositorio.persistir(usuarioDePrueba1);
		Assert.assertEquals(usuarioDePrueba1.getNombre(),
				repositorio.buscarPorID(Usuario.class, usuarioDePrueba1.getId()).getNombre());
	}

	@Test
	public void persistenciaDeComunaDeUsuario() {
		Usuario usuarioDePrueba2 = new Usuario("Terminal Eva Peron y Varela", new Posicion(1.9353, 9.3493), flores);
		repositorio.persistir(usuarioDePrueba2);
		Assert.assertEquals(flores.getNombre(),
				repositorio.buscarPorID(Usuario.class, usuarioDePrueba2.getId()).getComuna().getNombre());
	}

	@Test
	public void persistenciaDeUsuarios() {
		Usuario usuarioDePrueba3 = new Usuario("Terminal Acoyte y Rivadavia", new Posicion(15.15, 9.3848), caballito);
		Usuario usuarioDePrueba4 = new Usuario("Terminal Central Olivera", new Posicion(8.3, 8.7), villaLugano);
		repositorio.persistir(usuarioDePrueba3);
		repositorio.persistir(usuarioDePrueba4);
		Assert.assertTrue(repositorio.traerTodos(Usuario.class).stream()
				.anyMatch(unUsuario -> unUsuario.getNombre().equals("Terminal Acoyte y Rivadavia")));
	}

	@Test
	public void persistenciaDeUsuarioConNotificador() {
		Administrador administrador = new Administrador("admin@admin.com");
		NotificadorDeBusquedaLenta notificadorDeBusquedaLenta = new NotificadorDeBusquedaLenta(10.0,
				new EnviadorDeMail(new MailServiceImpostor()), administrador);
		repositorio.persistir(administrador);

		Usuario usuarioDePrueba5 = new Usuario("Terminal Parque Rivadavia", new Posicion(40.458742, 19.23887),
				caballito);

		usuarioDePrueba5.agregarObservadorDeBusqueda(notificadorDeBusquedaLenta);
		repositorio.persistir(usuarioDePrueba5);

		Usuario usuario = repositorio.buscarPorID(Usuario.class, usuarioDePrueba5.getId());
		Assert.assertEquals(usuario.getObservadoresDeBusqueda().size(),
				usuarioDePrueba5.getObservadoresDeBusqueda().size());
	}

	@AfterClass
	public static void guardarYCerrarSesion() {
		repositorio.cerrarSesion();
	}
}