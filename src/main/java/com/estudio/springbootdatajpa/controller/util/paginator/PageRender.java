package com.estudio.springbootdatajpa.controller.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de caclular los elmentos como total de paginas, numero de paginas, etc...
 * @param <T>
 */
public class PageRender<T> {/*Se usa los generic de java ya que se puede paginar una lista de clientes
    o productos o cualquier tipo de entidad*/

    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElementosPorPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();
        //estos datos se toma desde page y se inicializa en el pageRequest del controlador cliente
        numElementosPorPagina = page.getSize();
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1; // se pone porque el valor default que tenemos es 0 en@RequestParam
        //se calcula el desde y el hasta para capturar nuestro paginador(los rangos)
        int desde, hasta;
        if (totalPaginas <= numElementosPorPagina) {
            desde = 1;
            hasta = totalPaginas;
        } else {//se mostrara las primeras paginas del paginador
            if (paginaActual <= numElementosPorPagina / 2) {
                //calcula  cuando el rango esta intermedio
                desde = 1;
                hasta = numElementosPorPagina;
            } else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {//se muestra las ultimas paginas
                //calcula el rango final del paginador
                desde = totalPaginas - numElementosPorPagina + 1;
                hasta = numElementosPorPagina;
            } else {//se muestra las paginas intermedias
                desde = paginaActual - numElementosPorPagina / 2;
                hasta = numElementosPorPagina;
            }
        }
        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + i, paginaActual == desde + i));
            /*se coloca en el segundo parametro el valor == que corresponde a un boolean para verificar
            * si la página actual es igual a ella misma sumándole 1, esto para que en la plantilla se pueda resaltar
            */
        }

    }

    public String getUrl() {
        return url;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
