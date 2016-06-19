package mx.unam.ciencias.textAnalyzer;

/**
 * Clase que asigna un numero a un String, para simular peso o repeticiones.
 */
public class ArrayString extends Object implements Comparable<ArrayString>{//Extiendo object por legibilidad
    // de codigo ya que sobreescribo mucho metodos
    /** Repeticiones de la palabra*/
    public Integer repeticiones;
    /** String de la clase*/
    protected String string;

    /** Constructor vacio por defecto*/
    public ArrayString() {
        string = "";
        repeticiones = 0;
    }

    /**
     * Constructor que recibe un String
     * @param string el string que se va a contar
     */
    public ArrayString(String string) {
        this.string = string;
        this.repeticiones = 1;
    }

    /**
     * Metodo que devuelve una representacion en cadena del objeto
     * @return una representacion del objeto
     */
    @Override
    public String toString(){
        return string;
    }

    /**
     * Regresa un entero de usando el hashCode de la clase String
     * @return Huella digital del String
     */
    @Override
    public int hashCode(){
        return string.hashCode();
    }

    /**
     * Metodo Equals para comprar 2 objetos
     * @param objeto Objeto a compara
     * @return booleano que dice sin son 2 objetos semanticamente iguales
     */
    @Override
    public boolean equals(Object objeto){
        if (objeto == null) return false;
        if (objeto == this) return true;
        if (!(objeto instanceof ArrayString))return false;
        ArrayString array = (ArrayString)objeto;
        return this.string.equals(array.string);
    }

    /**
     * Metodo compareTo, si tienen en mismo numero de repeticiones, compara
     * por orden alfabetico
     * @param array Objeto a comparar
     * @return un numero menor que 0 si es menor
     *          mayor que 0 si es mayor
     *          0 si es igual
     *        
     */
    @Override
    public int compareTo(ArrayString array){
        if(array.repeticiones.equals(this.repeticiones))
            return array.string.compareTo(this.string);

        return this.repeticiones.compareTo(array.repeticiones);
    }
}
