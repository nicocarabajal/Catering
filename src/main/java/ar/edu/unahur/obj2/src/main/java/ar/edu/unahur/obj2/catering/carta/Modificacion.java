package ar.edu.unahur.obj2.catering.carta;

/**
 * Command: cada modificación de la carta sabe aplicarse y deshacerse.
 * Sumar una nueva modificación es crear una clase que implemente esta
 * interfaz, sin tocar el gestor.
 */
public interface Modificacion {

    void aplicar();

    void revertir();
}
