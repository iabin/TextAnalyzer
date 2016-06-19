package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
	if(elemento == null)
	    throw  new IllegalArgumentException();
	Nodo otroNodo = new Nodo(elemento);
	if(esVacia()) { 
	    this.cabeza = otroNodo;
	    this.rabo = otroNodo;

	}
	else {
	rabo.siguiente = otroNodo;
	this.rabo = otroNodo;
	}

    }
}
