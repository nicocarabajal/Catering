package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.adicional.MozosAdicionales;
import ar.edu.unahur.obj2.catering.adicional.ServicioNocturno;
import ar.edu.unahur.obj2.catering.adicional.VajillaPremium;
import ar.edu.unahur.obj2.catering.catalogo.Catalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdicionalesTest {

    private static final double DELTA = 0.001;

    private Catalogo catalogo;

    @BeforeEach
    void iniciarCatalogo() {
        catalogo = new Catalogo();
    }

    // ---- Cada adicional por separado ----

    @Test
    void dadaUnaMesaDulceConMozosAdicionales_alCalcularPrecio_suma18000() {
        Propuesta propuesta = new MozosAdicionales(catalogo.mesaDulce());

        assertEquals(78_000, propuesta.precio(), DELTA);
    }

    @Test
    void dadaUnaMesaDulceConVajillaPremium_alCalcularPrecio_suma12000() {
        Propuesta propuesta = new VajillaPremium(catalogo.mesaDulce());

        assertEquals(72_000, propuesta.precio(), DELTA);
    }

    @Test
    void dadaUnaMesaDulceConServicioNocturno_alCalcularPrecio_suma20PorCiento() {
        Propuesta propuesta = new ServicioNocturno(catalogo.mesaDulce());

        assertEquals(72_000, propuesta.precio(), DELTA);
    }

    @Test
    void dadaUnaMesaDulceConMozosAdicionales_alConsultarDescripcion_agregaElAdicional() {
        Propuesta propuesta = new MozosAdicionales(catalogo.mesaDulce());

        assertEquals("Mesa dulce + Mozos adicionales", propuesta.descripcion());
    }

    // ---- Combinaciones y orden ----

    @Test
    void dadoUnMenuConMozosYLuegoNocturno_alCalcularPrecio_elNocturnoRecargaElAcumulado() {
        Propuesta propuesta =
                new ServicioNocturno(new MozosAdicionales(catalogo.menuParrillero()));

        assertEquals(432_000, propuesta.precio(), DELTA);
    }

    @Test
    void dadoUnMenuConMozosYLuegoNocturno_alConsultarDescripcion_respetaElOrdenDeContratacion() {
        Propuesta propuesta =
                new ServicioNocturno(new MozosAdicionales(catalogo.menuParrillero()));

        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno",
                propuesta.descripcion());
    }

    @Test
    void dadoUnMenuConNocturnoYLuegoMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal() {
        Propuesta propuesta =
                new MozosAdicionales(new ServicioNocturno(catalogo.menuParrillero()));

        assertEquals(428_400, propuesta.precio(), DELTA);
    }

    @Test
    void dadoUnMenuConNocturnoYLuegoMozos_alConsultarDescripcion_respetaElOrdenDeContratacion() {
        Propuesta propuesta =
                new MozosAdicionales(new ServicioNocturno(catalogo.menuParrillero()));

        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales",
                propuesta.descripcion());
    }

    // ---- Adicionales repetidos ----

    @Test
    void dadoUnBrindisConVajillaPremiumDosVeces_alCalcularPrecio_sumaElAdicionalDosVeces() {
        Propuesta propuesta =
                new VajillaPremium(new VajillaPremium(catalogo.brindisDeBienvenida()));

        assertEquals(74_000, propuesta.precio(), DELTA);
    }

    @Test
    void dadoUnBrindisConVajillaPremiumDosVeces_alConsultarDescripcion_laRepiteDosVeces() {
        Propuesta propuesta =
                new VajillaPremium(new VajillaPremium(catalogo.brindisDeBienvenida()));

        assertEquals("Brindis de bienvenida + Vajilla premium + Vajilla premium",
                propuesta.descripcion());
    }

    @Test
    void dadaUnaMesaDulceConNocturnoDosVeces_alCalcularPrecio_acumulaElRecargo() {
        Propuesta propuesta =
                new ServicioNocturno(new ServicioNocturno(catalogo.mesaDulce()));

        assertEquals(86_400, propuesta.precio(), DELTA);
    }

    @Test
    void dadaUnaMesaDulceConNocturnoDosVeces_alConsultarDescripcion_laRepiteDosVeces() {
        Propuesta propuesta =
                new ServicioNocturno(new ServicioNocturno(catalogo.mesaDulce()));

        assertEquals("Mesa dulce + Servicio nocturno + Servicio nocturno",
                propuesta.descripcion());
    }

    // ---- Adicional sobre un menú anidado ----

    @Test
    void dadoElEventoCompletoConVajillaPremium_alCalcularPrecio_sumaElAdicionalAlPrecioDelMenu() {
        Propuesta propuesta = new VajillaPremium(catalogo.eventoCompleto());

        assertEquals(530_800, propuesta.precio(), DELTA);
    }

    @Test
    void dadoElEventoCompletoConVajillaPremium_alConsultarDescripcion_agregaElAdicional() {
        Propuesta propuesta = new VajillaPremium(catalogo.eventoCompleto());

        assertEquals("Evento Completo + Vajilla premium", propuesta.descripcion());
    }

    // ---- Los adicionales no agregan servicios ----

    @Test
    void dadoUnMenuConAdicionales_alContarServicios_semantieneLaCantidadOriginal() {
        Propuesta propuesta =
                new ServicioNocturno(new MozosAdicionales(catalogo.menuParrillero()));

        assertEquals(3, propuesta.cantidadServicios());
    }

    @Test
    void dadoUnMenuConAdicionales_alConsultarUnServicioQueTenia_siguienteIncluyendolo() {
        Propuesta propuesta = new ServicioNocturno(catalogo.menuParrillero());

        assertTrue(propuesta.incluye("Parrillada completa"));
    }

    @Test
    void dadoUnMenuConAdicionales_alConsultarUnServicioQueNoTenia_siguienteSinIncluirlo() {
        Propuesta propuesta = new ServicioNocturno(catalogo.menuParrillero());

        assertFalse(propuesta.incluye("DJ y sonido"));
    }

    // ---- La propuesta original no se modifica ----

    @Test
    void dadaUnaPropuestaDecorada_alConsultarElPrecioDeLaOriginal_nocambia() {
        new MozosAdicionales(catalogo.menuParrillero());

        assertEquals(342_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaUnaPropuestaDecorada_alConsultarLaDescripcionDeLaOriginal_nocambia() {
        new MozosAdicionales(catalogo.menuParrillero());

        assertEquals("Menú Parrillero", catalogo.menuParrillero().descripcion());
    }
}
