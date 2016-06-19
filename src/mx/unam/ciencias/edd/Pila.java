package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
	if(elemento == null)
	    throw new IllegalArgumentException();
	Nodo elNodo = new Nodo(elemento);
	elNodo.siguiente=this.cabeza; 
        this.cabeza = elNodo;
     
    }
}
