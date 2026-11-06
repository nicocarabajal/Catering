package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PropuestaNoIncluidaException;

import java.util.ArrayList;
import java.util.List;

/**
 * Compuesto del Composite: reúne otras propuestas (servicios u otros menús,
 * con cualquier nivel de anidamiento) bajo un nombre comercial y un descuento.
 */
public class Menu implements Propuesta {

    private final String nombre;
    private final double descuento;
    private final List<Propuesta> componentes = new ArrayList<>();

    public Menu(String nombre, double descuento, Propuesta... componentes) {
        if (descuento < 0 || descuento > 100) {
            throw new DescuentoInvalidoException(descuento);
        }
        this.nombre = nombre;
        this.descuento = descuento;
        this.componentes.addAll(List.of(componentes));
    }

    public void agregar(Propuesta propuesta) {
        componentes.add(propuesta);
    }

    public void quitar(Propuesta propuesta) {
        if (!componentes.remove(propuesta)) {
            throw new PropuestaNoIncluidaException(nombre, propuesta.descripcion());
        }
    }

    @Override
    public double precio() {
        double suma = componentes.stream().mapToDouble(Propuesta::precio).sum();
        return suma * (100 - descuento) / 100;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public int cantidadServicios() {
        return componentes.stream().mapToInt(Propuesta::cantidadServicios).sum();
    }

    @Override
    public boolean incluye(String nombreServicio) {
        return componentes.stream().anyMatch(componente -> componente.incluye(nombreServicio));
    }
}
