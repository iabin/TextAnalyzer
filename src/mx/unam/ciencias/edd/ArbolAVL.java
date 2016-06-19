package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends ArbolBinario<T>.Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            altura = 0;
        }
	

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        public String toString() {
            return String.format("%s %d/%d", elemento.toString(), altura, balance(this));
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || raiz == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            if(super.equals(vertice)&&vertice.altura==this.altura)
		return true; //Me atrase un chingo xD al menos lo hice bien

	    return false;
        }

    }


    	private boolean esHijoIzquierdo(Vertice v){ 
	if(v==null)
	return false;
	
	if(v.padre.izquierdo == v)
	return true;
	
	return false;
	

 	}


    private boolean esHoja(Vertice v){
	boolean a;
	a = (v.derecho==null&&v.izquierdo==null);
	if(a)
	    return true;
	return false;
    }

    
    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario. La complejidad en tiempo del método es <i>O</i>(log
     * <i>n</i>) garantizado.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        rebalancea(verticeAVL(ultimoAgregado));
    }

    private void rebalancea(VerticeAVL vertice) {
        if (vertice == null)
            return;

        cambiaAltura(vertice);

        if (balance(vertice) == -2) {
            if (balance(verticeAVL(vertice.derecho)) == 1)
                giraDerechaAVL(verticeAVL(vertice.derecho));

            giraIzquierdaAVL(vertice);
        } else if (balance(vertice) == 2) {
            if (balance(verticeAVL(vertice.izquierdo)) == -1)
                giraIzquierdaAVL(verticeAVL(vertice.izquierdo));

            giraDerechaAVL(vertice);
        }
        
        rebalancea(verticeAVL(vertice.padre));
    }

    private void cambiaAltura(VerticeAVL vertice) {
        vertice.altura = 1 + Math.max(getAltura(verticeAVL(vertice.izquierdo)), getAltura(verticeAVL(vertice.derecho)));;
    }

    /**
     * Regresa la altura del vértice AVL.
     * @param vertice el vértice del que queremos la altura.
     * @return la altura del vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    public int getAltura(VerticeArbolBinario<T> vertice) {
        return vertice == null ? -1 : verticeAVL(vertice).altura;
    }


    private int balance(VerticeAVL vertice) {
        return getAltura(verticeAVL(vertice.izquierdo)) - getAltura(verticeAVL(vertice.derecho));
    }

    private void giraIzquierdaAVL(VerticeAVL vertice){
        super.giraIzquierda(vertice);
        cambiaAltura(vertice);
        cambiaAltura(verticeAVL(vertice.padre));
    }

    private void giraDerechaAVL(VerticeAVL vertice){
        super.giraDerecha(vertice);
        cambiaAltura(vertice);
        cambiaAltura(verticeAVL(vertice.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo. La
     * complejidad en tiempo del método es <i>O</i>(log <i>n</i>) garantizado.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL vertice = verticeAVL(busca(raiz, elemento));

        if (vertice == null)
            return;
        if (vertice.hayIzquierdo()) {
            VerticeAVL aux = vertice;
            vertice = verticeAVL(maximoEnSubarbol(vertice.izquierdo));
            aux.elemento = vertice.elemento;
        }
        if (esHoja(vertice))
            eliminaHoja(vertice);
        else
            subirHijo(vertice);
        
        rebalancea(verticeAVL(vertice.padre));
        elementos--;
    }

    private void eliminaHoja(VerticeAVL vertice) {
        if (vertice == raiz)
            raiz = ultimoAgregado = null;
        else if (esHijoIzquierdo(vertice))
            vertice.padre.izquierdo = null;
        else
            vertice.padre.derecho = null;
    }

    private void subirHijo(VerticeAVL vertice) {
        if (vertice.hayIzquierdo())
            if (vertice == raiz) {
                raiz = vertice.izquierdo;
                raiz.padre = null;
            } else {
                vertice.izquierdo.padre = vertice.padre;
                if (esHijoIzquierdo(vertice))
                    vertice.padre.izquierdo = vertice.izquierdo;
                else
                    vertice.padre.derecho = vertice.izquierdo;
            }
        else
            if (vertice == raiz) {
                raiz = raiz.derecho;
                raiz.padre = null;
            } else {
                vertice.derecho.padre = vertice.padre;
                if (esHijoIzquierdo(vertice))
                    vertice.padre.izquierdo = vertice.derecho;
                else
                    vertice.padre.derecho = vertice.derecho;
            }
    }


    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeAVL}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice AVL.
     * @return el vértice recibido visto como vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    protected VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        return (VerticeAVL)vertice;
    }

}
