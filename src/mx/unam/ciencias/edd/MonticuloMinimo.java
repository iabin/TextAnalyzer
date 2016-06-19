package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>). Podemos crear un montículo
 * mínimo con <em>n</em> elementos en tiempo <em>O</em>(<em>n</em>), y podemos
 * agregar y actualizar elementos en tiempo <em>O</em>(log <em>n</em>). Eliminar
 * el elemento mínimo también nos toma tiempo <em>O</em>(log <em>n</em>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T> {
    
    /* Clase privada para iteradores de montículos. */
    private class Iterador implements Iterator<T> {
	
        /* Índice del iterador. */
        private int indice;
	
        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
	    return indice < siguiente ? true:false;
    }
    
    /* Regresa el siguiente elemento. */
    @Override public T next() {
	if(hasNext())
	    return arbol[indice++];

	throw new NoSuchElementException();
	
    }
    
    /* No lo implementamos: siempre lanza una excepción. */
    @Override public void remove() {
	throw new UnsupportedOperationException();
    }
}

/* El siguiente índice dónde agregar un elemento. */
private int siguiente;
/* Usamos un truco para poder utilizar arreglos genéricos. */
private T[] arbol;

/* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
   Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] creaArregloGenerico(int n) {
        return (T[])(new ComparableIndexable[n]);
    }
    
    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Lista)}, pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
	siguiente = 0;
	arbol = creaArregloGenerico(50);
    }
    
    /**
     * Constructor para montículo mínimo que recibe una lista. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param lista la lista a partir de la cuál queremos construir el
     *              montículo.
     */
    public MonticuloMinimo(Lista<T> lista) {
	if(lista.getLongitud()==0)
	    arbol = creaArregloGenerico(50);
	
        arbol = creaArregloGenerico(lista.getLongitud());
	siguiente = 0; 
	for(T elemento:lista){
	    arbol[siguiente] = elemento;
	    arbol[siguiente].setIndice(siguiente);
	    siguiente++;
	    
	}
	
	for (int i = (arbol.length) / 2; i >= 0; i--) 
            ordenaAbajo(i);
        
    }
    
    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        if(siguiente==arbol.length){
	    T[] aux = creaArregloGenerico(2*arbol.length);
	    for(int i=0;i<arbol.length;i++) {
		aux[i]=arbol[i];
	    }
	    this.arbol=aux;
	}
	
	arbol[siguiente]=elemento;
	arbol[siguiente].setIndice(siguiente);
	siguiente++;
       	reordena(arbol[siguiente-1]);
	
    }
    
    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    public T elimina() {
	if(esVacio())
	    throw new IllegalStateException();
	
	T elemento = arbol[0];
	elimina(arbol[0]);
	return elemento;


    }
    
    
    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        if(!contiene(elemento))
	    return;
	
	int aux = elemento.getIndice();
        swap(aux,siguiente-1);
        arbol[siguiente-1] = null;
        elemento.setIndice(-1);
        reordena(arbol[aux]);
	siguiente--;
	
	
    }
    
    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	
	if(elemento==null)
	    return false;
	
	if(esVacio())
	    return false;	
	
	if(elemento.getIndice()<0)
	    return false;
	
	if(elemento.getIndice()>=siguiente)
	    return false;
	
	if(arbol[elemento.getIndice()].equals(elemento))
	    return true;
	
	return false;
		
    }
    
    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacio() {
	return siguiente==0;
    }

   
    
    private void ordenaAbajo(int i){
	
	if (i < 0 && i >= siguiente)
	    return;
	
	    int izq = (i*2) + 1;
	    int der = (i*2) + 2;
	    int menor = i;
	   
	    if (izq >= siguiente||arbol[izq]==null) //no tengo hijos
		return;
	    
	    
	    if ( arbol[i].compareTo(arbol[izq]) > 0 ) //si llegue a aqui tengo hijo izquierdo me comparo con el
		menor    = izq;
	    
	    if ( der < siguiente && arbol[der]!=null ) 
		if ( arbol[menor].compareTo(arbol[der]) > 0) 
		    menor = der;//legando a aqui ya me compare con el izquierdo y ahora me comparo con el menor de ellos
		
	    
	    
	    if (menor == i) 
		return; //si yo soy el menor bai
	    
	    swap(i, menor);
	    ordenaAbajo(menor);
	    
	
	
	
    }
    
  


    
     private void ordenaArriba(int i){
	 if(i<=0)
	     return;
	 if(i>=siguiente)
	     return;

	 int padre = (i-1)/2;
	 if(arbol[padre].compareTo(arbol[i])>0){
	     swap(padre,i);
	     ordenaArriba(padre);
	     
	 }
	 return;

    }

    private void swap(int i, int e){
	T aux = arbol[i];
	arbol[i] = arbol[e];
	arbol[i].setIndice(i);
	arbol[e] = aux;
	arbol[e].setIndice(e);
    }
    
    
    /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    public void reordena(T elemento) {
	if(!contiene(elemento))
	    return;
	int i = elemento.getIndice();
	ordenaAbajo(i);
	ordenaArriba(i);
	return;
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
	return siguiente;
    }
    
    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    public T get(int i) {
	if(i<0||i>=siguiente)
	    throw new NoSuchElementException();
        return arbol[i];
    }
    
    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
	return new Iterador();
    }
}
