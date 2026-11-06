package ar.edu.unahur.obj2.catering;

/**
 * Componente común (Composite): un servicio suelto, un menú o un servicio con
 * adicionales se consultan todos de la misma manera.
 */
public interface Propuesta {

    double precio();

    String descripcion();

    int cantidadServicios();

    boolean incluye(String nombreServicio);
}
