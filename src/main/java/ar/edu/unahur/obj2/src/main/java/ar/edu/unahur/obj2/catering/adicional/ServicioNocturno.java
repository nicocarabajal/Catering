package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.Propuesta;

/**
 * Recargo del 20 % sobre el precio acumulado hasta este punto de la cadena de
 * adicionales: por eso el orden en que se combinan importa.
 */
public class ServicioNocturno extends Adicional {

    private static final double RECARGO_PORCENTUAL = 20;

    public ServicioNocturno(Propuesta base) {
        super(base);
    }

    @Override
    public double precio() {
        double acumulado = base().precio();
        return acumulado + acumulado * RECARGO_PORCENTUAL / 100;
    }

    @Override
    protected String nombreAdicional() {
        return "Servicio nocturno";
    }
}
