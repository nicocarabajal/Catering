package ar.edu.unahur.obj2.catering.excepciones;

/**
 * Excepción NO verificada (RuntimeException).
 * Justificación: un precio negativo es una violación de precondición, es decir,
 * un error de programación de quien invoca. No hay nada razonable que el
 * llamador pueda "recuperar" capturándola, así que no se lo obliga a declararla.
 */
public class PrecioNegativoException extends RuntimeException {

    public PrecioNegativoException(double precio) {
        super("El precio no puede ser negativo: " + precio);
    }
}
