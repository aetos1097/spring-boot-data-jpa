package com.estudio.springbootdatajpa.controller.util.paginator;

/**
 * Clase para representar cada una de las paginas, que va a tener su numero de pagina
 * y un atributo par indicar si es o no pagina actual
 */
public class PageItem {
    private int numero;
    private boolean actual;

    public PageItem(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isActual() {
        return actual;
    }
}
