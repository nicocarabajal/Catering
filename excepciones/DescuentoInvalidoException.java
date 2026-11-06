package ar.edu.unahur.obj2.catering.excepciones;

/**
 * Excepción NO verificada (RuntimeException).
 * Justificación: un descuento fuera del rango 0-100 es una violación de
 * precondición (error de programación de quien crea el menú), no una situación
 * del negocio que el llamador deba manejar.
 */
public class DescuentoInvalidoException extends RuntimeException {

    public DescuentoInvalidoException(double descuento) {
        super("El descuento debe estar entre 0 y 100: " + descuento);
    }
}
