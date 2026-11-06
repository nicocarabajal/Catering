package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;

// Hoja del Composite: representa un servicio individual
public class Servicio implements Propuesta {

    private final String nombre;
    private double precio;

    public Servicio(String nombre, double precio) {
        validarPrecio(precio);
        this.nombre = nombre;
        this.precio = precio;
    }

    public void actualizarPrecio(double nuevoPrecio) {
        validarPrecio(nuevoPrecio);
        this.precio = nuevoPrecio;
    }

    @Override
    public double precio() {
        return precio;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public int cantidadServicios() {
        return 1;
    }

    @Override
    public boolean incluye(String nombreServicio) {
        return nombre.equals(nombreServicio);
    }

    private static void validarPrecio(double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException(precio);
        }
    }
}