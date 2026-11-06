package ar.edu.unahur.obj2.catering.contrato;

import ar.edu.unahur.obj2.catering.Propuesta;

/**
 * Registro de lo contratado por un cliente. NO es una propuesta del catálogo
 * (no se cotiza ni se combina): al crearlo "saca una foto" del importe y la
 * descripción vigentes, y los cambios posteriores de la carta no lo afectan.
 */
public class Contrato {

    private final String cliente;
    private final double importe;
    private final String descripcion;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        this.importe = propuesta.precio();
        this.descripcion = propuesta.descripcion();
    }

    public String cliente() {
        return cliente;
    }

    public double importe() {
        return importe;
    }

    public String descripcion() {
        return descripcion;
    }
}
