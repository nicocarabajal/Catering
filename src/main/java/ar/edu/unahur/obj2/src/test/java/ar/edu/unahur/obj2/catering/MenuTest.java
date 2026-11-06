package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.catalogo.Catalogo;
import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    private static final double DELTA = 0.001;

    private Catalogo catalogo;

    @BeforeEach
    void iniciarCatalogo() {
        catalogo = new Catalogo();
    }

    // ---- Etapa 1, parte A: menús con servicios ----

    @Test
    void dadoUnMenuConDescuentoNegativo_alCrearlo_lanzaDescuentoInvalidoException() {
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Menú roto", -1));
    }

    @Test
    void dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaDescuentoInvalidoException() {
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Menú roto", 101));
    }

    @Test
    void dadoUnMenuConDescuentoCero_alCrearlo_noLanzaExcepcion() {
        assertDoesNotThrow(() -> new Menu("Sin descuento", 0));
    }

    @Test
    void dadoUnMenuConDescuentoCien_alCalcularPrecio_esCero() {
        Menu gratis = new Menu("Todo gratis", 100, new Servicio("Mesa dulce", 60_000));

        assertEquals(0, gratis.precio(), DELTA);
    }

    @Test
    void dadoElMenuParrillero_alCalcularPrecio_aplicaElDescuentoSobreLaSuma() {
        assertEquals(342_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoElPackFiesta_alCalcularPrecio_aplicaElDescuentoSobreLaSuma() {
        assertEquals(256_500, catalogo.packFiesta().precio(), DELTA);
    }

    @Test
    void dadoElMenuParrillero_alConsultarSuDescripcion_devuelveSuNombre() {
        assertEquals("Menú Parrillero", catalogo.menuParrillero().descripcion());
    }

    @Test
    void dadoElMenuParrillero_alContarServicios_devuelveTres() {
        assertEquals(3, catalogo.menuParrillero().cantidadServicios());
    }

    @Test
    void dadoElMenuParrillero_alConsultarSiIncluyeParrilladaCompleta_esVerdadero() {
        assertTrue(catalogo.menuParrillero().incluye("Parrillada completa"));
    }

    @Test
    void dadoElMenuParrillero_alConsultarSiIncluyeDjYSonido_esFalso() {
        assertFalse(catalogo.menuParrillero().incluye("DJ y sonido"));
    }

    // ---- Etapa 1, parte B: menús dentro de menús ----

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alCalcularPrecio_usaElPrecioYaDescontadoDeLosInternos() {
        assertEquals(518_800, catalogo.eventoCompleto().precio(), DELTA);
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alContarServicios_sumaLosDeTodosLosNiveles() {
        assertEquals(6, catalogo.eventoCompleto().cantidadServicios());
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alConsultarUnServicioDeUnMenuInterno_esVerdadero() {
        assertTrue(catalogo.eventoCompleto().incluye("DJ y sonido"));
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alConsultarUnServicioQueNoTiene_esFalso() {
        assertFalse(catalogo.eventoCompleto().incluye("Fotógrafo"));
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alConsultarUnServicioDirecto_esVerdadero() {
        assertTrue(catalogo.eventoCompleto().incluye("Brindis de bienvenida"));
    }
}
