package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.adicional.MozosAdicionales;
import ar.edu.unahur.obj2.catering.catalogo.Catalogo;
import ar.edu.unahur.obj2.catering.contrato.Contrato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContratoTest {

    private static final double DELTA = 0.001;

    private Catalogo catalogo;

    @BeforeEach
    void iniciarCatalogo() {
        catalogo = new Catalogo();
    }

    @Test
    void dadoUnContratoSobreUnMenuConAdicionales_alConsultarElImporte_devuelveElPrecioDeLaPropuesta() {
        Contrato contrato =
                new Contrato("Lucía", new MozosAdicionales(catalogo.menuParrillero()));

        assertEquals(360_000, contrato.importe(), DELTA);
    }

    @Test
    void dadoUnContratoSobreUnMenuConAdicionales_alConsultarLaDescripcion_devuelveLaDeLaPropuesta() {
        Contrato contrato =
                new Contrato("Lucía", new MozosAdicionales(catalogo.menuParrillero()));

        assertEquals("Menú Parrillero + Mozos adicionales", contrato.descripcion());
    }

    @Test
    void dadoUnContrato_alConsultarElCliente_devuelveElNombreDelCliente() {
        Contrato contrato = new Contrato("Lucía", catalogo.menuParrillero());

        assertEquals("Lucía", contrato.cliente());
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elMenuCambiaDePrecio() {
        new Contrato("Lucía", catalogo.menuParrillero());

        catalogo.parrilladaCompleta().actualizarPrecio(260_000);

        assertEquals(360_000, catalogo.menuParrillero().precio(), DELTA);
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia() {
        Contrato contrato = new Contrato("Lucía", catalogo.menuParrillero());

        catalogo.parrilladaCompleta().actualizarPrecio(260_000);

        assertEquals(342_000, contrato.importe(), DELTA);
    }
}
