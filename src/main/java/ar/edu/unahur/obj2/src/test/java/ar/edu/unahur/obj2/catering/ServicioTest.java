package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTest {

    private static final double DELTA = 0.001;

    @Test
    void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class, () -> new Servicio("DJ y sonido", -1));
    }

    @Test
    void dadoUnServicioConPrecioCero_alCrearlo_noLanzaExcepcion() {
        assertDoesNotThrow(() -> new Servicio("Cortesía", 0));
    }

    @Test
    void dadoUnServicio_alConsultarSuPrecio_devuelveElPrecioConElQueSeCreo() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertEquals(60_000, mesaDulce.precio(), DELTA);
    }

    @Test
    void dadoUnServicio_alConsultarSuDescripcion_devuelveSuNombre() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertEquals("Mesa dulce", mesaDulce.descripcion());
    }

    @Test
    void dadoUnServicioSuelto_alContarServicios_devuelveUno() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertEquals(1, mesaDulce.cantidadServicios());
    }

    @Test
    void dadoUnServicio_alConsultarSiIncluyeSuPropioNombre_esVerdadero() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertTrue(mesaDulce.incluye("Mesa dulce"));
    }

    @Test
    void dadoUnServicio_alConsultarSiIncluyeOtroNombre_esFalso() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertFalse(mesaDulce.incluye("DJ y sonido"));
    }

    @Test
    void dadoUnServicio_alActualizarSuPrecio_tomaElNuevoPrecio() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        mesaDulce.actualizarPrecio(70_000);

        assertEquals(70_000, mesaDulce.precio(), DELTA);
    }

    @Test
    void dadoUnServicio_alActualizarSuPrecioConUnValorNegativo_lanzaPrecioNegativoException() {
        Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);

        assertThrows(PrecioNegativoException.class, () -> mesaDulce.actualizarPrecio(-5));
    }
}
