/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab5;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Alejandro Arroyave
 */
public class Lab5 {
    public static void main (String [] args){
        System.out.println("Ingresa el número de escenarios");
        Scanner in = new Scanner (System.in);
        int NoEscenarios = in.nextInt();
        Pair [] TamañoEsc;
        TamañoEsc = new Pair [NoEscenarios];
        for (int i= 0; i<NoEscenarios; i++){
            System.out.println("Ingresa las coordenadas del escenario " + i+1);
            int X = in.nextInt();
            int Y = in.nextInt();
            TamañoEsc [i] = new Pair (X, Y);
        }
        System.out.println("Ingrese las coordenadas de Karolina");
        int XKarolina = in.nextInt();
        int YKarolina = in.nextInt();
        Pair CoordKarolina = new Pair (XKarolina, YKarolina);
        System.out.println("Ingrese el número de desechos");
        int NoDesechos = in.nextInt();
        ArrayList<Pair> CoordDesechos;
        CoordDesechos = new ArrayList<>();
        for (int i = 0; i < NoDesechos; i++) {
            System.out.println("Ingrese las coordenadas del desecho " + i+1);
            int XDesechos = in.nextInt();
            int YDesechos = in.nextInt();
            CoordDesechos.add(new Pair(XDesechos, YDesechos));
        }
        for (int i = 0; i < NoEscenarios; i++) {
            System.out.println(recogerDesechos(TamañoEsc[i], CoordKarolina, CoordDesechos));
        }
    }
    /**
     * Guys, la idea del algoritmo es que en primera instancia, busque cual es el
     * desecho mas cercano a Karolina y que ella vaya por el desecho, y esto
     * determinaría para que dirección va a empezar a buscar los desechos, si 
     * el mas cercano fue hacia la izquierda, empezara hacia la izquierda, y si fue 
     * a la derecha empezara hacia la derecha, parandose en una fila y revisando en
     * que fila está la columna con el desecho mas cercano, lo que me falta por determinar
     * es cuando hara este recorrido parandose en filas y revisando columnas, o 
     * cuando lo hara parandose en columnas y revisando filas.
     * 
     * Las coordenadas se estan manejando con la clase pair, donde first es Y y 
     * second es X
     * @param TamañoEsc Tamaño del escenario
     * @param CoordKarolina Coordenadas iniciales de Karolina
     * @param CoordDesechos Arreglo con las coordenadas de todos los desechos
     * @return Retorna un String diciendo cual es el camino mas corto
     */ 
    public static String recogerDesechos(Pair TamañoEsc, Pair CoordKarolina, ArrayList<Pair> CoordDesechos){
        int resultado; // recorrido mas cortos
        Pair PosInicial = CoordKarolina; //Se determina a donde tiene que volver cuando termine todo su recorrido
        resultado = 0;
        int [][] Escenario = new int [(int)TamañoEsc.first][(int)TamañoEsc.second];
        Escenario [(int)CoordKarolina.second][(int)CoordKarolina.first] = 2; //posicion de karolina
        CoordDesechos.forEach((desecho) -> { // se ponen los desechos en la matriz
            Escenario [(int)desecho.second][(int)desecho.first] = 1; 
        });
        Pair menor = new Pair(2143456, 2143456); 
        for (Pair desecho : CoordDesechos){
            if ((int)desecho.first+(int)desecho.second < (int)menor.first+(int)menor.second){
                menor = desecho; // se determina el desecho mas cercano a karolina
            }
        }
        int difX = (int)CoordKarolina.second - (int) menor.second; // es la distancia en X que habia entre
                                                                    // el desecho y karolina
        int difY = (int)CoordKarolina.first - (int) menor.first;// es la distancia en Y que habia entre
                                                                    // el desecho y karolina
        resultado = Math.abs(difX) + Math.abs(difY);    //se agrega el recorrido que hizo desde la posición inicial
                                                        //hasta el desecho
        Escenario [(int)CoordKarolina.second][(int)CoordKarolina.first] = 0; //Karolina se mueve de su pos inicial
                CoordKarolina = menor; //Son las nuevas coordenadas donde se encuentra karolina
        Escenario [(int)menor.second][(int)menor.first] = 2; //Se translada karolina en la matriz
        CoordDesechos.remove(menor); //Se remueve el desecho eliminado del arrayList
        ArrayList<Pair> cercanos = new ArrayList <>();
        if (difX >= 1){
             for (Pair cercano : cercanos) {
                
            }
        }
        return "The shortest path has length ";
    }
    
    public static void toString (int [][] matriz){
        for (int[] matriz1 : matriz) {
            for (int j = 0; j < matriz [0].length; j++) {
                System.out.print(matriz1[j] + " ");
            }
            System.out.println();
        }
    }
}
