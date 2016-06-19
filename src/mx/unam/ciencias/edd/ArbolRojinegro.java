package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:1
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros son autobalanceados, y por lo tanto las operaciones de
 * inserción, eliminación y búsqueda pueden realizarse en <i>O</i>(log
 * <i>n</i>).
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends ArbolBinario<T>.Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
	    this.color=Color.ROJO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if(this.color == Color.ROJO)
		return "R{"+this.elemento.toString()+"}";
	    return "N{"+this.elemento.toString()+"}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
	    @SuppressWarnings("unchecked") 
	    VerticeRojinegro v = (VerticeRojinegro)o;

	    boolean g = (super.equals(v)&&(v.color==this.color));
		 
	    return g;
	    
            
		    }
	}
	
	/**
	 * Construye un nuevo vértice, usando una instancia de {@link
	 * VerticeRojinegro}.
	 * @param elemento el elemento dentro del vértice.
	 * @return un nuevo vértice rojinegro con el elemento recibido dentro del
	 *         mismo.
	 */
	@Override protected Vertice nuevoVertice(T elemento) {
	    VerticeRojinegro j = new VerticeRojinegro(elemento);
	    return j;
    }
	
	/**
	 * Convierte el vértice (visto como instancia de {@link
	 * VerticeArbolBinario}) en vértice (visto como instancia de {@link
	 * VerticeRojinegro}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice
     *                rojinegro.
     * @return el vértice recibido visto como vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
	VerticeRojinegro k = verticeRojinegro(vertice);
	if(esNegro(k))
	    return Color.NEGRO;
	if(esRojo(k))
	    return Color.ROJO;
	return Color.NINGUNO;
      
    }



    
    /**
     * Método que nos dice si un vértice es rojo.
     * @param vertice, el vértice del cual queremos saber si es rojo.
     * @return true si es rojo, false en otro caso.
     */
    private boolean esRojo(VerticeRojinegro vertice){
        if(vertice == null)
            return false;
        if(vertice.color == Color.ROJO)
            return true;
            return false;
    }

    

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
	super.agrega(elemento);
	VerticeRojinegro v = verticeRojinegro(this.ultimoAgregado);
	v.color = Color.ROJO;
	rebalancea1(v);
	
    }

   
    private boolean esHijoIzquierdo(Vertice vertice){
        if(vertice.padre==null)
            return false;
        return vertice.padre.izquierdo == vertice;        
    }
    
    private boolean esHijoDerecho(Vertice vertice){
        if(vertice.padre==null)
            return false;
        return vertice.padre.derecho == vertice;        
    }
    
    
    private void rebalancea1(VerticeRojinegro vertice) {
	
        //caso 1
	if(!vertice.hayPadre())  {
	    vertice.color = Color.NEGRO;
	    return;
	}

	//caso 2
	VerticeRojinegro padre = verticeRojinegro(vertice.padre);
	if(padre.color==Color.NEGRO)
	    return;

	//caso 3
	VerticeRojinegro abuelo = verticeRojinegro(vertice.padre.padre);
	VerticeRojinegro tio = (esHijoIzquierdo(padre)) ? verticeRojinegro(abuelo.derecho) : verticeRojinegro(abuelo.izquierdo);

	if(tio!=null&& tio.color == Color.ROJO) {
	    tio.color = Color.NEGRO;
	    padre.color = Color.NEGRO;
	    abuelo.color = Color.ROJO;
	    rebalancea1(abuelo);
	    return;
	}

	//caso 4
	if(esHijoIzquierdo(vertice) != esHijoIzquierdo(padre)){
	    if(esHijoIzquierdo(padre)){ 
		super.giraIzquierda(padre);
	    }else { 
		super.giraDerecha(padre);
	    } 
	   
	    VerticeRojinegro v = padre;
	    padre = vertice;
	    vertice = v;
	} 
	
	//caso 5
	padre.color = Color.NEGRO;
	abuelo.color = Color.ROJO;
	if(esHijoIzquierdo(vertice)){
	    super.giraDerecha(abuelo);
	} else{ 
	    super.giraIzquierda(abuelo);
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
        throw new UnsupportedOperationException("Los árboles rojinegro no  pueden " +
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
        throw new UnsupportedOperationException("Los árboles rojinegro no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }

    private boolean esHoja(Vertice vertice){
	if(vertice.derecho==null&&vertice.izquierdo==null)
	    return true;
	return false;
    }
    private boolean esRaiz(Vertice vertice){
	if(vertice.padre==null)
	    return true;
	return false;
    }
   
    
    
   /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro vertice = verticeRojinegro(busca(raiz, elemento));
        VerticeRojinegro hijo, fantasma = null;

        if (vertice == null)
            return;
        if (vertice.hayIzquierdo()) {
            VerticeRojinegro aux = vertice;
            vertice = verticeRojinegro(maximoEnSubarbol(vertice.izquierdo));
            aux.elemento = vertice.elemento;
        }
        if (esHoja(vertice)){
            fantasma = verticeRojinegro(nuevoVertice(null));
            fantasma.color = Color.NEGRO;
            fantasma.padre = vertice;
            vertice.izquierdo = fantasma;
        }
        hijo = obtenerHijo(vertice);
        subirHijo(vertice);

       
        if(esNegro(vertice) && esNegro(hijo)){
            hijo.color = Color.NEGRO;
            rebalancea2(hijo);
        }else 
            hijo.color = Color.NEGRO;

        mataFantasma(fantasma);

        elementos--;
    }

    private void subirHijo(VerticeRojinegro vertice){
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

    private void mataFantasma(VerticeRojinegro fantasma){
        if(fantasma != null)
            if(esRaiz(fantasma))
                raiz = ultimoAgregado = fantasma = null;
            else
                if(esHijoIzquierdo(fantasma))
                    fantasma.padre.izquierdo = null;
                else
                    fantasma.padre.derecho = null;
    }

    private void rebalancea2(VerticeRojinegro vertice) {
        VerticeRojinegro padre, hermano, sobrinoIzq, sobrinoDer, aux;
        if (vertice.padre == null){
            vertice.color = Color.NEGRO;
            raiz = vertice;
            return;
        }
        padre = verticeRojinegro(vertice.padre);
        hermano = obtenerHermano(vertice);
        
        if (!esNegro(hermano)) {
            hermano.color = Color.NEGRO;
            padre.color = Color.ROJO;
            
            if (esHijoIzquierdo(vertice))
                super.giraIzquierda(padre);
            else
                super.giraDerecha(padre);
            padre = verticeRojinegro(vertice.padre);
            hermano = obtenerHermano(vertice);
        }
        sobrinoIzq = verticeRojinegro(hermano.izquierdo);
        sobrinoDer = verticeRojinegro(hermano.derecho);
       
        if (esNegro(padre) && esNegro(hermano) && sobrinosNegros(sobrinoIzq, sobrinoDer)) {
            hermano.color = Color.ROJO;
            rebalancea2(padre);
            return;
        }
      
        if (esNegro(hermano) && sobrinosNegros(sobrinoIzq, sobrinoDer) && !esNegro(padre)) {
            padre.color = Color.NEGRO;
            hermano.color = Color.ROJO;
            return;
        }
      
        if (sonVerticesBicoloreados(sobrinoIzq, sobrinoDer) && sonSobrinoCruzados(vertice, sobrinoIzq, sobrinoDer)) {
            if(!esNegro(sobrinoIzq))
                sobrinoIzq.color = Color.NEGRO;
            else
                sobrinoDer.color = Color.NEGRO;

            hermano.color = Color.ROJO;

            if(esHijoIzquierdo(vertice))
                super.giraDerecha(hermano);
            else
                super.giraIzquierda(hermano);
            hermano = obtenerHermano(vertice);
            sobrinoIzq = verticeRojinegro(hermano.izquierdo);
            sobrinoDer = verticeRojinegro(hermano.derecho);
        }
       
        hermano.color = padre.color;
        padre.color = Color.NEGRO;

        if(esHijoIzquierdo(vertice))
            sobrinoDer.color = Color.NEGRO;
        else
            sobrinoIzq.color = Color.NEGRO;

        if(esHijoIzquierdo(vertice))
            super.giraIzquierda(padre);
        else
            super.giraDerecha(padre);
    }

    private VerticeRojinegro obtenerHijo(VerticeRojinegro vertice){
        if(vertice.hayIzquierdo())
            return verticeRojinegro(vertice.izquierdo);
        return verticeRojinegro(vertice.derecho);
    }

    private boolean sonSobrinoCruzados(VerticeRojinegro vertice, VerticeRojinegro sobrinoIzq, VerticeRojinegro sobrinoDer) {
        return esNegro(sobrinoIzq) && esHijoDerecho(vertice) || esNegro(sobrinoDer) && esHijoIzquierdo(vertice);
    }

    private boolean sonVerticesBicoloreados(VerticeRojinegro a, VerticeRojinegro b) {
        return esNegro(a) ^ esNegro(b);
    }

    
    private boolean esNegro(VerticeRojinegro vertice) {
        return vertice == null || vertice.color == Color.NEGRO;
    }

    private boolean sobrinosNegros(VerticeRojinegro sobrinoIzq, VerticeRojinegro sobrinoDer) {
        return esNegro(sobrinoIzq) && esNegro(sobrinoDer);
    }

    private VerticeRojinegro obtenerHermano(VerticeRojinegro vertice) {
        if (esHijoIzquierdo(vertice))
            return verticeRojinegro(vertice.padre.derecho);
        return verticeRojinegro(vertice.padre.izquierdo);


    }
    
}  
