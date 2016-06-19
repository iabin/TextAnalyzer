package mx.unam.ciencias.graficador;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.proyecto3.ArrayString;

import java.lang.reflect.Array;

/**
 * Clase que permite dibujar estructas de tipo
 *ArbolRojinegr
 */
public class ArbolRojinegroSVG implements ImprimeSVG{
    /** Atributo Interno*/
    ArbolRojinegro<ArrayString> arbol;

	String repeticiones = "";
    
    /**
     * Constructor único que recibe un elemento.
     * @param arbol El arbolRojinegro que se desea imprimir
     */
    public ArbolRojinegroSVG(ArbolRojinegro<ArrayString> arbol){
	this.arbol = arbol;
    } 
    

    /**
     * Metodo que SobreEscribe imprimeSVG de
     * la interfaz ImprimeSVG
     */
    @Override
    public String imprimeSVG(){
	VerticeArbolBinario<ArrayString> raiz = arbol.raiz();
	int f = (int) (Math.pow(2, arbol.profundidad()) * 90);
	imprimeRecursivo(new Vector2D(f/2,80),arbol.profundidad(),raiz);
		int o = (int) (Math.pow(2, arbol.profundidad()) * 90);
		int oo = arbol.profundidad()*200;
		String prin = ("<svg width='"+o+"' height='"+ oo +"'>");

		String end = ("</svg>");

		return prin+repeticiones+end;
    }
    
    
    /**Metodo privado Para permitir la recursion e inpresion del arbol*/
    private String imprimeRecursivo(Vector2D centro,int n,VerticeArbolBinario<ArrayString> vertice){
	ArbolRojinegro<ArrayString> arbol = new ArbolRojinegro<>();
	Color color = arbol.getColor(vertice);
	CirculoSVG raiz = new CirculoSVG(25,centro,vertice.get().toString(),color);

	int k = (int)(Math.pow(2,n)*(20));
	int minimo = centro.getX() +k;
	int minimo2 = centro.getX() - k;
	
	
	if(minimo-minimo2<50){ 
	    minimo = centro.getX() + 30;
	    minimo2 = centro.getX() -30;
	}

	if(vertice.hayDerecho()){ 
	    int i = n-1;
	    VerticeArbolBinario<ArrayString> derecho = vertice.getDerecho();
	    Vector2D centroDerecho = new Vector2D(minimo,centro.getY()+150);
	    LineaSVG linea = new LineaSVG(centro,centroDerecho);
	    repeticiones = repeticiones+linea.imprimeSVG();
	    repeticiones = repeticiones+imprimeRecursivo(centroDerecho,i,derecho);

	}
	
	if(vertice.hayIzquierdo()) {
	    int j = n-1;
	    VerticeArbolBinario<ArrayString> izquierdo = vertice.getIzquierdo();
	    Vector2D centroIzquierdo = new Vector2D(minimo2,centro.getY()+150);
	    LineaSVG linea = new LineaSVG(centro,centroIzquierdo);
	    repeticiones=repeticiones+linea.imprimeSVG();
	    repeticiones = repeticiones+imprimeRecursivo(centroIzquierdo,j,izquierdo);
	}
	
	
	repeticiones= repeticiones+raiz.imprimeSVG();
		return repeticiones;
    }
}
