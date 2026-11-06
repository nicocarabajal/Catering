package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Invoker del Command: aplica modificaciones y lleva el historial para poder
 * revertirlas en orden inverso. No conoce los tipos concretos de modificación.
 */
public class GestorCarta {

    private final Deque<Modificacion> historial = new ArrayDeque<>();

    /** Aplica de inmediato. Si falla, la excepción se propaga y NO se registra. */
    public void aplicar(Modificacion modificacion) {
        modificacion.aplicar();
        historial.push(modificacion);
    }

    /**
     * Aplica un lote todo o nada: si alguna falla, deshace en orden inverso las
     * ya aplicadas, no registra ninguna y propaga la excepción de la que falló.
     */
    public void aplicarLote(List<Modificacion> lote) {
        List<Modificacion> aplicadas = new ArrayList<>();
        try {
            for (Modificacion modificacion : lote) {
                modificacion.aplicar();
                aplicadas.add(modificacion);
            }
        } catch (RuntimeException falla) {
            deshacerEnOrdenInverso(aplicadas);
            throw falla;
        }
        aplicadas.forEach(historial::push);
    }

    public void revertirUltima() {
        if (historial.isEmpty()) {
            throw new NadaParaRevertirException();
        }
        historial.peek().revertir();
        historial.pop();
    }

    public int cantidadAplicadas() {
        return historial.size();
    }

    private void deshacerEnOrdenInverso(List<Modificacion> aplicadas) {
        for (int i = aplicadas.size() - 1; i >= 0; i--) {
            aplicadas.get(i).revertir();
        }
    }
}
