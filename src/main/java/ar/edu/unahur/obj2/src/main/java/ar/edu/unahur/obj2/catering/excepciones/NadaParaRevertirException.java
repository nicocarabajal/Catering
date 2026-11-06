package ar.edu.unahur.obj2.catering.excepciones;

/**
 * Excepción de negocio NO verificada (RuntimeException).
 * Justificación: revertir con el historial vacío es un error de uso (quien
 * invoca puede evitarlo, por ejemplo deshabilitando la acción "revertir") y
 * no hay forma útil de recuperarse localmente. Mantenerla no verificada evita
 * ensuciar las firmas del gestor con un "throws" obligatorio.
 */
public class NadaParaRevertirException extends RuntimeException {

    public NadaParaRevertirException() {
        super("No hay modificaciones para revertir");
    }
}
