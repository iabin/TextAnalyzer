package mx.unam.ciencias.textAnalyzer;

import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.graficador.GraficaSVG;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author iabin
 * CLASE MAIN
 * Clase main, programa que analiza textos y genera un archivo html por cada
 * file recibido, ademas de generar un archivo index.html con un link a cada uno de ellos
 */
public class Main {

    /**
     * Main de la clase,
     * @param args Argumentos de la entrada, recibe n archivos de texto plano y un directorio,
     *             en caso de no ingresar directorio se escribira sobre el home
     */
    public static void main(String[] args) {
	if(args.length==0)
	    System.err.println("DEBE INGRESAR AL MENOS UN ARCHIVO");
        int contador = 0;
        Lista<File> archivos = new Lista<>();
        File directorio = new File(".");
        String dir = ".";
        for(String argumento:args) {
            if(argumento.equals("-o")) {
                try {
                    dir = args[contador + 1];
                    directorio = new File(dir);
                    continue;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("NO INGRESÓ UN DIRECTORIO DESPUES DE LA BANDERA -o");
                    continue;
                }
            }
            if(argumento.equals(dir))
                continue;

            archivos.agrega(new File(argumento));
            contador++;
        }
        if(archivos.getLongitud()==0) {
            System.err.println("NO INGRESÓ NINGÚN ARCHIVO");
        }
        Pila<Object[]> lisp = new Pila<>();
        int i = 1;
        Lista<File> errores = new Lista<>();
        for (File o:archivos) {
            if(!o.isFile()) {
                errores.agrega(o);
                System.err.println("el " + i + "-ésimo archivo está mal :'v");
            }


            if(!directorio.exists()) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("DESEA CREAR EL DIRECTORIO   " + directorio + "\n "+ "Y/N");
                try {
                    if (bf.readLine().equalsIgnoreCase("y"))
                        directorio.mkdirs();
                    else{
                        return;
                    }
                }catch (IOException e) {
                    System.err.println("Error leyendo la entrada estandard");
                }
            }

            if(errores.contiene(o))
                continue;

            TextAnalyzer tex = new TextAnalyzer(o);
            String zz = "/archivo"+i+".html";
            String z = (directorio.toString()+zz);
            Object[] aaaa={zz, tex};
            lisp.mete(aaaa);
            String inicior = "<meta charset=\"utf-8\" />\n";
            Escritor escritor = new Escritor(new File(z),inicior+tex.html());
            i++;
        }

        Grafica<String> graf = new Grafica<>();
	String nuevo = "";
        Lista<TextAnalyzer> text =  new Lista<>();

        while(!lisp.esVacia()) {
            Object[] c = lisp.saca();
            graf.agrega(c[1].toString());
            text.agrega((TextAnalyzer) c[1]);
            nuevo = nuevo+"<a href ='."+c[0]+"'>"+c[1]+"</a>"+"</br>"+"\n";
        }

        for(TextAnalyzer a : text) {
            for(TextAnalyzer aa: text) {
                if (a.enComun(aa).getElementos()>4)
                    try {
                        graf.conecta(a.toString(), aa.toString());
                    } catch (IllegalArgumentException e) {
                    }
            }
        }

        GraficaSVG ahoraSi = new GraficaSVG(graf);
        String inicior = "<meta charset=\"utf-8\" />\n";
        Escritor h = new Escritor(new File(directorio.toString()+"/index.html"),inicior+nuevo+ahoraSi.imprimeSVG());
    }
}
