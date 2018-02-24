/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase donde se resuelve el problema de las n reinas
 * @author Alejandro Arroyave Bedoya, Luis Javier Palacio Mesa, Santiago Castrillón 
 */
public class Nqueens {
    
    /**
     * Clase principal
     * @param args 
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println(queens(11));
        long finalTime = System.currentTimeMillis()-startTime;
        System.out.println(finalTime);
    }
    
    /**
     * Esta clase se utiliza para saber si dado un tablero de ajedrez
     * representado en un arreglo la posición de las reinas es válida.
     * @param tablero arreglo que se va a verificar.
     * @return si el tablero es válido o no por medio de un booleano.
     */
     public static boolean esValido(Integer[] tablero) {
        for (int i = 0; i < tablero.length-1; ++i) {
            for (int j = i +1; j < tablero.length; j++) {
                if (Math.abs(tablero[i]-tablero[j]) == Math.abs(i-j) || Objects.equals(tablero [i], tablero[j]) ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Este método genera todos los arreglos posibles
     * @param pre
     * @param rango 
     * @param list lista donde se almacenan todas las listas posibles
     */
    private static void generarArreglos(String pre, String rango, ArrayList<ArrayList<Integer>> list) {
        if (rango.length() == 0) {
            ArrayList <Integer> arreglo = new ArrayList<>();
            list.add(arreglo);
            for (int i =0; i < pre.length(); i++){
                arreglo.add(Integer.parseInt(pre.substring(i, i+1)));
            }
        } else {
            for (int i = 0; i < rango.length(); i++) {
                generarArreglos(pre + rango.charAt(i), rango.substring(0,i) + rango.substring(i+1), list);
            }
        }
    }

    /**
     * Este método cuenta el número de soluciones que hay para cada tablero.
     * @param n Número de reinas en el tablero.
     * @return Número de soluciones que tiene el tablero.
     */
    public static int queens(int n) {
        String rango = "";
        for (int i = 0; i < n; i++) {
            rango += i;
        }
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        generarArreglos("", rango, list);
        int cont = 0;
        for (ArrayList<Integer> arreglo : list) {        
            if(esValido(arreglo.toArray(new Integer[n]))){
             cont++;   
            }
        }
        return cont;
    }
}
