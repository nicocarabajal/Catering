package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.Propuesta;

public class VajillaPremium extends Adicional {

    private static final double COSTO = 12_000;

    public VajillaPremium(Propuesta base) {
        super(base);
    }

    @Override
    public double precio() {
        return base().precio() + COSTO;
    }

    @Override
    protected String nombreAdicional() {
        return "Vajilla premium";
    }
}
