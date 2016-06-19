package mx.unam.ciencias.textAnalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que escribe en un archivo en un archivo, tiene una funcion unica
 * el constructor que escribe en un archivo valido.
 */
public class Escritor {

    /**
     * Constructor de la clase y funcion unica
     * @param archivo Archivo donde se escribira
     * @param g String a escribir
     */
    public Escritor(File archivo, String g) {
        try {
            archivo.createNewFile();

        } catch (IOException ioe) {
            System.err.println("EL ARCHIVO NO PUEDE SER CREADO BAI :), posible directorio incorrecto");
            return;
        }

        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(g);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
