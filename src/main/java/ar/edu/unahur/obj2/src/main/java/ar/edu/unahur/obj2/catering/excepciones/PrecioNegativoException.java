package ar.edu.unahur.obj2.catering.excepciones;

public class PrecioNegativoException extends RuntimeException {

    public PrecioNegativoException(double precio) {
        super("El precio no puede ser negativo: " + precio);
    }
}