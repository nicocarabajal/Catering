package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.Menu;
import ar.edu.unahur.obj2.catering.Propuesta;

public class AgregarPropuesta implements Modificacion {

    private final Menu menu;
    private final Propuesta propuesta;

    public AgregarPropuesta(Menu menu, Propuesta propuesta) {
        this.menu = menu;
        this.propuesta = propuesta;
    }

    @Override
    public void aplicar() {
        menu.agregar(propuesta);
    }

    @Override
    public void revertir() {
        menu.quitar(propuesta);
    }
}
