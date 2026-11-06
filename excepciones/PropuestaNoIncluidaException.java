package ar.edu.unahur.obj2.catering.excepciones;

/**
 * Excepción de negocio NO verificada (RuntimeException).
 * Justificación: se lanza al quitar de un menú algo que no es componente directo.
 * Es un uso incorrecto de la API (el llamador conoce el contenido del menú), y
 * además, si fuera verificada, habría que declarar "throws" en la interfaz
 * Modificacion y en todas sus implementaciones, incluso en las que nunca fallan.
 */
public class PropuestaNoIncluidaException extends RuntimeException {

    public PropuestaNoIncluidaException(String menu, String propuesta) {
        super("'" + propuesta + "' no es un componente directo de '" + menu + "'");
    }
}
