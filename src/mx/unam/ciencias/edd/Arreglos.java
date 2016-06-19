package mx.unam.ciencias.edd;

/**
 * Clase para manipular arreglos genéricos de elementos comparables.
 */
public class Arreglos {
    
    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] a) {

	quickSortConIndices(a,0,(a.length-1)); //a mi tambien me duele hacer esto, pero hacerlo de otra manera es demasiado complejo y la vida es muy corta 
	
    } 
    
     /**
     * Ordena el arreglo recibido usando QickSort recursivamente.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     * @param inicio Entero que indica el inicio del subArreglo.
     * @param fin Entero que indica el din del subArreglo.
     */
    private static <T extends Comparable<T>> void quickSortConIndices(T[] a,int inicio,int fin) {
	if (inicio < fin) {
            int i = inicio;
	    int j = fin;
            T x = a[(i + j) / 2];
 
            do {
                while (a[i].compareTo(x) < 0) i++;
                while (x.compareTo(a[j]) < 0) j--;
		
                if ( i <= j) {
		    swap(a,i,j); //metodo auxiliar swap, esta abajo
                    i++;
                    j--;
                }
 
            } while (i <= j);
 
            quickSortConIndices(a, inicio, j);
            quickSortConIndices(a, i, fin);
        }
    }
    
    




    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] a) {
	if(a.length<=1)
	    return;  //si es arreglo es de 1 o menor ya esta arreglado

	for(int i=0; i<a.length -1; i++) {
		int minimo = i;
		
		for(int j=i+1; j<a.length; j++) {
			if(a[minimo].compareTo((a[j])) > 0 )  
				minimo = j;
			    
		    }


		swap(a,minimo,i);

	    }
	
    }
    

    /**
     * Hace un intercambio magico entre 2 elementos de un arreglo generico 
     * @param <T> tipo del que puede ser el arreglo.
     * @param a el arreglo donde se hara el intercambio.
     * @param i elemento a intercambiar.
     * @param j segundo elemento a intercambiar.
     */
    private static <T  extends Comparable<T>> void swap(T[] a, int i, int j) {
        if (i != j) {
            T ayuda = a[i];
            a[i] = a[j];
            a[j] = ayuda;
        }
    }



    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a el arreglo dónde buscar.
     * @param e el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] a, T e) {
	int bot = 0;
	int top = (a.length - 1);
	while (bot <= top) {
	    int  mid = bot + ((top - bot) / 2);
	  if (a[mid].compareTo(e) > 0 ) //si es menor entonces ahora el top es desde el medio menos 1
	      top = mid - 1;
	  
	  else if (a[mid].compareTo(e) < 0) //lo mismo
	      bot = mid + 1;
	  else
	      return mid; //lo encontro 
	}
	return -1; //Termino de recorrer todo y no lo encontro :(
    }
    
    
    
}
