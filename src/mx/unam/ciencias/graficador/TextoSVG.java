package mx.unam.ciencias.graficador;

/**
 * Clase que modela un cuadro de texto y lo dibuja en SVG
 */
public class TextoSVG implements ImprimeSVG  {
	private Vector2D centro;//Centro del texto
   	private String texto;//Texto contenido
    private int tamaño;//Tamaño de la fuente
    private String color;//Color de la letra

	/**
	 * Constructor vacio, inicializa en el centro, con un string nulo
	 * y tamaño de la fuente 0
	 */
	public TextoSVG() {
	this.centro = new Vector2D();
	this.texto  = "";
	this.tamaño = 0;
	this.color = "black";
	}


    /**
	 * Constructor que recibe, un texto un tamaño un centro y un color
	 * @param texto texto a representar
	 * @param tamaño tamaño de la fuente
	 * @param centro centro del texto
	 * @param color color del texto
     */
    public  TextoSVG(String texto, int tamaño, Vector2D centro,String color) {
	this.centro = centro;
	this.tamaño = tamaño;
	this.texto  = texto;
	this.color  = color;
    }

	/**
	 * Constructor que recibe, un texto , un tamaño y un centro
	 * @param texto texto a represetar
	 * @param tamaño tamaño de la fuente
	 * @param centro centro del texto
     */
    public  TextoSVG(String texto, int tamaño, Vector2D centro) {
	this.centro = centro;
	this.tamaño = tamaño;
	this.texto  = texto;
	this.color  = "black";
    }

    
    /**
     * Imprime una representacion
     *de la clase en SVG
     */
    @Override
    public String imprimeSVG() {
		String tipografia = "sans-serif";
		String tec = ( "<text fill='" +color+ "' font-family='" + tipografia + "' font-size='" +tamaño+ "' x='" +centro.getX()+ "' y='" +centro.getY()+
			    "' text-anchor='middle'>" +texto+ "</text> \r\n");
		return tec;
	
    }
    
} 
