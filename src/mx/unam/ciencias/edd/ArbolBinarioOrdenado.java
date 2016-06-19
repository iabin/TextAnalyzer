package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para emular la pila de ejecución. */
        private Pila<ArbolBinario<T>.Vertice> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador() {
	    pila = new Pila<>();
	    meteIzq(raiz);
	}

	/* Mete los vertice desde la raiz y todos sus hijos izquierdos en 
	 * la pila */
	private void meteIzq(ArbolBinario<T>.Vertice vertice) {
	    if(vertice == null)
                return;
            pila.mete(vertice);
            meteIzq(vertice.izquierdo);
	}

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento del árbol en orden. */
        @Override public T next() {
	    ArbolBinario<T>.Vertice vertice = pila.saca();
	    meteIzq(vertice.derecho);
	    return vertice.elemento;
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
    public ArbolBinarioOrdenado() {
	super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
	if (elemento == null)
	    throw new IllegalArgumentException("Elemento nulo.");
	if (raiz == null) {
	    raiz = nuevoVertice(elemento);
	    ultimoAgregado = raiz;
	    elementos++;
	}
	else
	    agrega(raiz, elemento);
    }

    private void agrega(Vertice vertice, T elemento) {
	if (vertice.elemento.compareTo(elemento) >= 0) 
	    if (vertice.izquierdo == null) {
		vertice.izquierdo = nuevoVertice(elemento);
		vertice.izquierdo.padre = vertice;
		ultimoAgregado = vertice.izquierdo;
		elementos++;
	    }
	    else
		agrega(vertice.izquierdo, elemento);
	else
	    if (vertice.derecho == null) {
		vertice.derecho = nuevoVertice(elemento);
		vertice.derecho.padre = vertice;
		ultimoAgregado = vertice.derecho;
		elementos++;
	    }
	    else
		agrega(vertice.derecho, elemento);
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
	Vertice auxiliar = busca(raiz, elemento);
	if (auxiliar == null)
	    return;
	elimina(auxiliar);
	elementos--;
	
    }

    private void elimina(Vertice vertice) {
	Vertice anterior = maximoEnSubarbol(vertice.izquierdo);
	if(anterior == null) {
            if(vertice.derecho == null) {
                if(vertice.padre == null) {
                    raiz = null;
		    ultimoAgregado = null;
		}
                else
                    if(vertice.padre.izquierdo != null &&
		       vertice.padre.izquierdo == vertice)
                        vertice.padre.izquierdo = null;
                    else
                        vertice.padre.derecho = null;
            }
	    else { 
		if(vertice.padre == null){ 
		    raiz=vertice.derecho; 
		    vertice.derecho.padre=null; 
		}
		else { 
		    vertice.derecho.padre=vertice.padre; 
		    if(vertice.padre.izquierdo!=null &&
		       vertice.padre.izquierdo == vertice) 
			vertice.padre.izquierdo = vertice.derecho; 
		    else 
			vertice.padre.derecho = vertice.derecho; 
                } 
            } 
        }
	else {  
	    T elemento = vertice.elemento;
	    vertice.elemento = anterior.elemento;
	    anterior.elemento = elemento;
	    elimina(anterior); 
        } 
    }

    /**
     * Busca recursivamente un elemento, a partir del vértice recibido.
     * @param vertice el vértice a partir del cuál comenzar la búsqueda. Puede
     *                ser <code>null</code>.
     * @param elemento el elemento a buscar a partir del vértice.
     * @return el vértice que contiene el elemento a buscar, si se encuentra en
     *         el árbol; <code>null</code> en otro caso.
     */
    @Override protected Vertice busca(Vertice vertice, T elemento) {
	if (vertice == null)
	    return null;
	if (elemento == null)
	    return null;
	if (vertice.elemento.equals(elemento))
	    return vertice;
	if (elemento.compareTo(vertice.elemento) <= 0)
	    return busca(vertice.izquierdo, elemento);
	else
	    return busca(vertice.derecho, elemento);
    }

    /**
     * Regresa el vértice máximo en el subárbol cuya raíz es el vértice que
     * recibe.
     * @param vertice el vértice raíz del subárbol del que queremos encontrar el
     *                máximo.
     * @return el vértice máximo el subárbol cuya raíz es el vértice que recibe.
     */
    protected Vertice maximoEnSubarbol(Vertice vertice) {
	if (vertice == null)
	    return null;
	if (vertice.derecho == null)
	    return vertice;
	return maximoEnSubarbol(vertice.derecho);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
	if (vertice.hayIzquierdo()) {
	    Vertice original = vertice(vertice);
	    Vertice aux = original.izquierdo;
	    original.izquierdo = aux.derecho;
	    if(aux.derecho != null)        
		aux.derecho.padre = original;
	    aux.padre = original.padre;
	    if(original.padre == null)
		raiz = aux;
	    else
		if(original.padre.derecho == original)
		    original.padre.derecho = aux;
		else
		    original.padre.izquierdo = aux;
	    aux.derecho = original;
	    original.padre = aux;
	}
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
	if (vertice.hayDerecho()) {
	    Vertice original = vertice(vertice);
	    Vertice aux = original.derecho;
	    original.derecho = aux.izquierdo;
	    if(aux.izquierdo != null)        
		aux.izquierdo.padre = original;
	    aux.padre = original.padre;
	    if(original.padre == null)
		raiz = aux;
	    else
		if(original.padre.izquierdo == original)
		    original.padre.izquierdo = aux;
		else
		    original.padre.derecho = aux;
	    aux.izquierdo = original;
	    original.padre = aux;
	}
    }
}
