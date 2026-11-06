package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.carta.ActualizarPrecio;
import ar.edu.unahur.obj2.catering.carta.AgregarPropuesta;
import ar.edu.unahur.obj2.catering.carta.GestorCarta;
import ar.edu.unahur.obj2.catering.carta.Modificacion;
import ar.edu.unahur.obj2.catering.carta.QuitarPropuesta;
import ar.edu.unahur.obj2.catering.catalogo.Catalogo;
import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.excepciones.PropuestaNoIncluidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorCartaTest {

    private static final double DELTA = 0.001;

    private Catalogo catalogo;
    private GestorCarta gestor;
    private Servicio fotografo;

    @BeforeEach
    void iniciar() {
        catalogo = new Catalogo();
        gestor = new GestorCarta();
        fotografo = new Servicio("Fotógrafo", 100_000);
    }

    // ---------- Helpers de armado (Arrange) ----------

    private Modificacion agregarFotografo() {
        return new AgregarPropuesta(catalogo.menuParrillero(), fotografo);
    }

    private Modificacion actualizarParrillada(double precio) {
        return new ActualizarPrecio(catalogo.parrilladaCompleta(), precio);
    }

    private Modificacion quitarMesaDulce() {
        return new QuitarPropuesta(catalogo.menuParrillero(), catalogo.mesaDulce());
    }

    private Modificacion quitarBarraDeTragos() {
        return new QuitarPropuesta(catalogo.menuParrillero(), catalogo.barraDeTragos());
    }

    private void aplicarSecuenciaInmediata() {
        gestor.aplicar(agregarFotografo());
        gestor.aplicar(actualizarParrillada(260_000));
        gestor.aplicar(quitarMesaDulce());
    }

    private List<Modificacion> loteDeTresModificaciones() {
        return List.of(agregarFotografo(), actualizarParrillada(260_000), quitarMesaDulce());
    }

    private void revertirVeces(int veces) {
        for (int i = 0; i < veces; i++) {
            gestor.revertirUltima();
        }
    }

    private void aplicarLoteConFallaIgnorandoLaExcepcion() {
        gestor.aplicar(agregarFotografo());
        try {
            gestor.aplicarLote(List.of(actualizarParrillada(260_000), quitarBarraDeTragos()));
        } catch (PropuestaNoIncluidaException esperada) {
            // la falla es parte del escenario; lo que se verifica es el estado posterior
        }
    }

    // ---------- Cada modificación aplicada ----------

    @Test
    void dadaUnaModificacionAgregar_alAplicarla_elMenuPasaAIncluirLaPropuesta() {
        gestor.aplicar(agregarFotografo());

        assertTrue(catalogo.menuParrillero().incluye("Fotógrafo"));
    }

    @Test
    void dadaUnaModificacionActualizarPrecio_alAplicarla_elServicioTomaElNuevoPrecio() {
        gestor.aplicar(actualizarParrillada(260_000));

        assertEquals(260_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    @Test
    void dadaUnaModificacionQuitar_alAplicarla_elMenuDejaDeIncluirLaPropuesta() {
        gestor.aplicar(quitarMesaDulce());

        assertFalse(catalogo.menuParrillero().incluye("Mesa dulce"));
    }

    // ---------- Cada modificación revertida ----------

    @Test
    void dadaUnaModificacionAgregarAplicada_alRevertirla_elMenuDejaDeIncluirLaPropuesta() {
        gestor.aplicar(agregarFotografo());

        gestor.revertirUltima();

        assertFalse(catalogo.menuParrillero().incluye("Fotógrafo"));
    }

    @Test
    void dadaUnaActualizacionDePrecio_alRevertirla_devuelveElPrecioAnterior() {
        gestor.aplicar(actualizarParrillada(260_000));

        gestor.revertirUltima();

        assertEquals(240_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    @Test
    void dadaUnaModificacionQuitarAplicada_alRevertirla_elMenuVuelveAIncluirLaPropuesta() {
        gestor.aplicar(quitarMesaDulce());

        gestor.revertirUltima();

        assertTrue(catalogo.menuParrillero().incluye("Mesa dulce"));
    }

    // ---------- Secuencia inmediata, paso a paso ----------

    @Test
    void dadoElCatalogoInicial_alAgregarFotografo_elMenuParrilleroCuesta432000() {
        gestor.aplicar(agregarFotografo());

        assertEquals(432_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnFotografoAgregado_alActualizarLaParrillada_elMenuParrilleroCuesta450000() {
        gestor.aplicar(agregarFotografo());
        gestor.aplicar(actualizarParrillada(260_000));

        assertEquals(450_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaUnaParrilladaActualizada_alQuitarLaMesaDulce_elMenuParrilleroCuesta396000() {
        aplicarSecuenciaInmediata();

        assertEquals(396_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaLaSecuenciaInmediata_alRevertirUnaVez_elMenuParrilleroCuesta450000() {
        aplicarSecuenciaInmediata();

        revertirVeces(1);

        assertEquals(450_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaLaSecuenciaInmediata_alRevertirDosVeces_elMenuParrilleroCuesta432000() {
        aplicarSecuenciaInmediata();

        revertirVeces(2);

        assertEquals(432_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaLaSecuenciaInmediata_alRevertirTresVeces_elMenuParrilleroVuelveA342000() {
        aplicarSecuenciaInmediata();

        revertirVeces(3);

        assertEquals(342_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaLaSecuenciaInmediataTotalmenteRevertida_alRevertirUnaVezMas_lanzaNadaParaRevertirException() {
        aplicarSecuenciaInmediata();
        revertirVeces(3);

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    // ---------- Dos actualizaciones del mismo servicio ----------

    @Test
    void dadasDosActualizacionesCreadasAntesDeAplicarlas_alAplicarAmbas_prevaleceLaUltima() {
        Modificacion primera = actualizarParrillada(260_000);
        Modificacion segunda = actualizarParrillada(300_000);

        gestor.aplicar(primera);
        gestor.aplicar(segunda);

        assertEquals(300_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    @Test
    void dadasDosActualizacionesAplicadas_alRevertirUnaVez_devuelveElPrecioDeLaPrimera() {
        Modificacion primera = actualizarParrillada(260_000);
        Modificacion segunda = actualizarParrillada(300_000);
        gestor.aplicar(primera);
        gestor.aplicar(segunda);

        gestor.revertirUltima();

        assertEquals(260_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    @Test
    void dadasDosActualizacionesAplicadas_alRevertirDosVeces_devuelveElPrecioOriginal() {
        Modificacion primera = actualizarParrillada(260_000);
        Modificacion segunda = actualizarParrillada(300_000);
        gestor.aplicar(primera);
        gestor.aplicar(segunda);

        revertirVeces(2);

        assertEquals(240_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    // ---------- Errores en modificaciones inmediatas ----------

    @Test
    void dadaUnaPropuestaQueNoEsComponenteDelMenu_alQuitarla_lanzaPropuestaNoIncluidaException() {
        assertThrows(PropuestaNoIncluidaException.class,
                () -> gestor.aplicar(quitarBarraDeTragos()));
    }

    @Test
    void dadaUnaQuitadaInvalida_alFallar_elMenuNoSeAltera() {
        try {
            gestor.aplicar(quitarBarraDeTragos());
        } catch (PropuestaNoIncluidaException esperada) {
            // se verifica el estado posterior
        }

        assertEquals(342_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadaUnaQuitadaInvalida_alFallar_noSeRegistraEnElHistorial() {
        try {
            gestor.aplicar(quitarBarraDeTragos());
        } catch (PropuestaNoIncluidaException esperada) {
            // se verifica el estado posterior
        }

        assertEquals(0, gestor.cantidadAplicadas());
    }

    @Test
    void dadaUnaActualizacionConPrecioNegativo_alAplicarla_lanzaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class,
                () -> gestor.aplicar(actualizarParrillada(-1)));
    }

    @Test
    void dadaUnaActualizacionConPrecioNegativo_alFallar_elPrecioNoCambia() {
        try {
            gestor.aplicar(actualizarParrillada(-1));
        } catch (PrecioNegativoException esperada) {
            // se verifica el estado posterior
        }

        assertEquals(240_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    @Test
    void dadaUnaActualizacionConPrecioNegativo_alFallar_noSeRegistraEnElHistorial() {
        try {
            gestor.aplicar(actualizarParrillada(-1));
        } catch (PrecioNegativoException esperada) {
            // se verifica el estado posterior
        }

        assertEquals(0, gestor.cantidadAplicadas());
    }

    @Test
    void dadoUnHistorialVacio_alRevertir_lanzaNadaParaRevertirException() {
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    // ---------- Lote exitoso ----------

    @Test
    void dadoUnLoteValido_alAplicarlo_elMenuParrilleroCuesta396000() {
        gestor.aplicarLote(loteDeTresModificaciones());

        assertEquals(396_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnLoteValido_alAplicarlo_cadaModificacionQuedaEnElHistorial() {
        gestor.aplicarLote(loteDeTresModificaciones());

        assertEquals(3, gestor.cantidadAplicadas());
    }

    @Test
    void dadoUnLoteAplicado_alRevertirUnaVez_revierteSoloLaUltimaDelLote() {
        gestor.aplicarLote(loteDeTresModificaciones());

        revertirVeces(1);

        assertEquals(450_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnLoteAplicado_alRevertirTresVeces_elMenuParrilleroVuelveA342000() {
        gestor.aplicarLote(loteDeTresModificaciones());

        revertirVeces(3);

        assertEquals(342_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnLoteTotalmenteRevertido_alRevertirUnaVezMas_lanzaNadaParaRevertirException() {
        gestor.aplicarLote(loteDeTresModificaciones());
        revertirVeces(3);

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    // ---------- Lote con falla (todo o nada) ----------

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_propagaLaExcepcionDeLaQueFallo() {
        gestor.aplicar(agregarFotografo());

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicarLote(
                List.of(actualizarParrillada(260_000), quitarBarraDeTragos())));
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_dejaElMenuComoEstabaAntesDelLote() {
        aplicarLoteConFallaIgnorandoLaExcepcion();

        assertEquals(432_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_deshaceLasModificacionesYaAplicadasDelLote() {
        aplicarLoteConFallaIgnorandoLaExcepcion();

        assertEquals(240_000, catalogo.parrilladaCompleta().precio(), DELTA);
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_ningunaDelLoteQuedaEnElHistorial() {
        aplicarLoteConFallaIgnorandoLaExcepcion();

        assertEquals(1, gestor.cantidadAplicadas());
    }

    @Test
    void dadoUnLoteFallido_alRevertir_soloSeRevierteLoAplicadoAntesDelLote() {
        aplicarLoteConFallaIgnorandoLaExcepcion();

        revertirVeces(1);

        assertEquals(342_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnLoteFallido_alRevertirDosVeces_lanzaNadaParaRevertirException() {
        aplicarLoteConFallaIgnorandoLaExcepcion();
        revertirVeces(1);

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadoUnLoteDondeFallaLaUltimaActualizacionDePrecio_alAplicarlo_propagaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class, () -> gestor.aplicarLote(
                List.of(agregarFotografo(), actualizarParrillada(-1))));
    }

    @Test
    void dadoUnLoteDondeFallaLaUltimaActualizacionDePrecio_alAplicarlo_deshaceElAgregadoPrevio() {
        try {
            gestor.aplicarLote(List.of(agregarFotografo(), actualizarParrillada(-1)));
        } catch (PrecioNegativoException esperada) {
            // se verifica el estado posterior
        }

        assertFalse(catalogo.menuParrillero().incluye("Fotógrafo"));
    }
}
