package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.Propuesta;

/**
 * Decorator base: envuelve una propuesta cualquiera (servicio, menú u otro
 * adicional) y se consulta igual que ella. Los adicionales no agregan
 * servicios, así que la cantidad y la inclusión se delegan tal cual.
 * Cada subclase define solo su efecto en el precio y su etiqueta.
 */
public abstract class Adicional implements Propuesta {

    private final Propuesta base;

    protected Adicional(Propuesta base) {
        this.base = base;
    }

    protected Propuesta base() {
        return base;
    }

    protected abstract String nombreAdicional();

    @Override
    public String descripcion() {
        return base.descripcion() + " + " + nombreAdicional();
    }

    @Override
    public int cantidadServicios() {
        return base.cantidadServicios();
    }

    @Override
    public boolean incluye(String nombreServicio) {
        return base.incluye(nombreServicio);
    }
}
