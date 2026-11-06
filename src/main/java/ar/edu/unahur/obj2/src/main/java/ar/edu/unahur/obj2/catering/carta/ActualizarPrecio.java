package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.Servicio;

public class ActualizarPrecio implements Modificacion {

    private final Servicio servicio;
    private final double nuevoPrecio;
    private double precioAnterior;

    public ActualizarPrecio(Servicio servicio, double nuevoPrecio) {
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    /**
     * El precio anterior se captura al APLICAR (no al crear la modificación):
     * si se crean dos actualizaciones del mismo servicio antes de aplicarlas,
     * la segunda debe recordar el precio que dejó la primera.
     */
    @Override
    public void aplicar() {
        double anterior = servicio.precio();
        servicio.actualizarPrecio(nuevoPrecio);
        precioAnterior = anterior;
    }

    @Override
    public void revertir() {
        servicio.actualizarPrecio(precioAnterior);
    }
}
