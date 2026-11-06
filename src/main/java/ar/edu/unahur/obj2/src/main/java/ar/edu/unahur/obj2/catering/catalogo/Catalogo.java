package ar.edu.unahur.obj2.catering.catalogo;

import ar.edu.unahur.obj2.catering.Menu;
import ar.edu.unahur.obj2.catering.Servicio;

/**
 * Catálogo inicial de «Mesa Servida». Cada instancia arma servicios y menús
 * nuevos, así que cada escenario (o test) parte de un catálogo limpio aunque
 * otro haya modificado precios o contenidos.
 */
public class Catalogo {

    private final Servicio mesaDeEntradas = new Servicio("Mesa de entradas", 80_000);
    private final Servicio parrilladaCompleta = new Servicio("Parrillada completa", 240_000);
    private final Servicio mesaDulce = new Servicio("Mesa dulce", 60_000);
    private final Servicio barraDeTragos = new Servicio("Barra de tragos", 150_000);
    private final Servicio djYSonido = new Servicio("DJ y sonido", 120_000);
    private final Servicio brindisDeBienvenida = new Servicio("Brindis de bienvenida", 50_000);

    private final Menu menuParrillero =
            new Menu("Menú Parrillero", 10, mesaDeEntradas, parrilladaCompleta, mesaDulce);
    private final Menu packFiesta =
            new Menu("Pack Fiesta", 5, barraDeTragos, djYSonido);
    private final Menu eventoCompleto =
            new Menu("Evento Completo", 20, menuParrillero, packFiesta, brindisDeBienvenida);

    public Servicio mesaDeEntradas() {
        return mesaDeEntradas;
    }

    public Servicio parrilladaCompleta() {
        return parrilladaCompleta;
    }

    public Servicio mesaDulce() {
        return mesaDulce;
    }

    public Servicio barraDeTragos() {
        return barraDeTragos;
    }

    public Servicio djYSonido() {
        return djYSonido;
    }

    public Servicio brindisDeBienvenida() {
        return brindisDeBienvenida;
    }

    public Menu menuParrillero() {
        return menuParrillero;
    }

    public Menu packFiesta() {
        return packFiesta;
    }

    public Menu eventoCompleto() {
        return eventoCompleto;
    }
}
