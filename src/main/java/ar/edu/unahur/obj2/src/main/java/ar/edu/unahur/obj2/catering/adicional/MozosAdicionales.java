package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.Propuesta;

public class MozosAdicionales extends Adicional {

    private static final double COSTO = 18_000;

    public MozosAdicionales(Propuesta base) {
        super(base);
    }

    @Override
    public double precio() {
        return base().precio() + COSTO;
    }

    @Override
    protected String nombreAdicional() {
        return "Mozos adicionales";
    }
}
