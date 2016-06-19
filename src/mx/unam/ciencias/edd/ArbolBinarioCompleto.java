package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios completos. */
    private class Iterador implements Iterator<T> {

        private Cola<ArbolBinario<T>.Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
	    cola = new Cola<>();
	    if (raiz != null)
		cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
	    return !cola.esVacia();
        }

        /* Regresa el elemento siguiente. */
        @Override public T next() {
	    Vertice vertice = cola.saca();
	    if(vertice.hayIzquierdo())
		cola.mete(vertice.izquierdo);
	    if(vertice.hayDerecho())
		cola.mete(vertice.derecho);
	    return vertice.get();
	}

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() {
	super();
    }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
	if (elemento == null)
	    throw new IllegalArgumentException();
	Vertice nuevo = nuevoVertice(elemento);
	if (ultimoAgregado == null) {
	    raiz = nuevo;
	    ultimoAgregado = raiz;
	}
	else if (ultimoAgregado == raiz) {
	    raiz.izquierdo = nuevo;
	    raiz.izquierdo.padre = raiz;
	    ultimoAgregado = raiz.izquierdo;
	}
	else if (esIzquierdo(ultimoAgregado)) {
	    ultimoAgregado.padre.derecho = nuevo;
	    ultimoAgregado.padre.derecho.padre = ultimoAgregado.padre;
	    ultimoAgregado = ultimoAgregado.padre.derecho;
	}
	else {
	    Vertice auxiliar = ultimoAgregado;
	    while (auxiliar != raiz && esDerecho(auxiliar))
		   auxiliar = auxiliar.padre;
	    Vertice minimo;
	    if (auxiliar == raiz)
		minimo = minimoEnSubarbol(auxiliar);
	    else
		minimo = minimoEnSubarbol(auxiliar.padre.derecho);
	    minimo.izquierdo = nuevo;
	    minimo.izquierdo.padre = minimo;
	    ultimoAgregado = minimo.izquierdo;
	}
	elementos++;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
	Vertice borrado = busca(raiz, elemento);
	if (borrado == null)
	    return;
	borrado.elemento = ultimoAgregado.elemento;
	Vertice nuevoU;
	if (ultimoAgregado == raiz) {
	    raiz =  null;
	    nuevoU = null;
	}
	else if (esDerecho(ultimoAgregado))
	    nuevoU = ultimoAgregado.padre.izquierdo;
	else {
	    Vertice auxiliar = ultimoAgregado;
	    while (auxiliar != raiz && esIzquierdo(auxiliar))
		auxiliar = auxiliar.padre;
	    if (auxiliar == raiz)
		nuevoU = maximoEnSubarbol(auxiliar);
	    else
		nuevoU = maximoEnSubarbol(auxiliar.padre.izquierdo);
	}
	if (ultimoAgregado.padre != null) {
	    if (esIzquierdo(ultimoAgregado))
		ultimoAgregado.padre.izquierdo = null;
	    else
		ultimoAgregado.padre.derecho = null;
	}
	ultimoAgregado = null;
	ultimoAgregado = nuevoU;
	elementos--;
    }
    
    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    private boolean esIzquierdo(Vertice vertice) {
	return vertice == vertice.padre.izquierdo;
    }

    private boolean esDerecho(Vertice vertice) {
	return vertice == vertice.padre.derecho;
    }

    private Vertice minimoEnSubarbol(Vertice vertice) {
	if (vertice == null)
	    return null;
	if (vertice.izquierdo == null)
	    return vertice;
	else
	    return minimoEnSubarbol(vertice.izquierdo);
    }

    private Vertice maximoEnSubarbol(Vertice vertice) {
	if (vertice == null)
	    return null;
	if (vertice.derecho == null)
	    return vertice;
	else
	    return maximoEnSubarbol(vertice.derecho);
    }
}
