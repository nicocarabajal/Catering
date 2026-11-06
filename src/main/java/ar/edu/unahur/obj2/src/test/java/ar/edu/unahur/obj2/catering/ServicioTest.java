package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTest {

    private static final double DELTA = 0.001;

    @Test
    void crearServicioConPrecioNegativoLanzaExcepcion() {
        assertThrows(PrecioNegativoException.class, () -> new Servicio("DJ y sonido", -1));
    }

    @Test
    void crearServicioConPrecioCeroNoLanzaExcepcion() {
        assertDoesNotThrow(() -> new Servicio("Cortesía", 0));
    }

    @Test
    void consultarPrecioDevuelveElValorConElQueSeCreo() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertEquals(60_000, mesaDulce.precio(), DELTA);
    }

    @Test
    void consultarDescripcionDevuelveSuNombre() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertEquals("Mesa dulce", mesaDulce.descripcion());
    }

    @Test
    void cantidadServiciosDevuelveUnoParaServicioSuelto() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertEquals(1, mesaDulce.cantidadServicios());
    }

    @Test
    void servicioIncluyeSuPropioNombreDevuelveTrue() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertTrue(mesaDulce.incluye("Mesa dulce"));
    }

    @Test
    void servicioIncluyeOtroNombreDevuelveFalse() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertFalse(mesaDulce.incluye("DJ y sonido"));
    }

    @Test
    void actualizarPrecioModificaElValorCorrectamente() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        mesaDulce.actualizarPrecio(70_000);

        assertEquals(70_000, mesaDulce.precio(), DELTA);
    }

    @Test
    void actualizarPrecioConValorNegativoLanzaExcepcion() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertThrows(PrecioNegativoException.class, () -> mesaDulce.actualizarPrecio(-5));
    }
}