package ar.edu.unahur.obj2.catering;

public interface Propuesta {

    double precio();

    String descripcion();

    int cantidadServicios();

    boolean incluye(String nombreServicio);
}