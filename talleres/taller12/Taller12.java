/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller12;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author
 */
public class Taller12 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(HillClimb(8)));
    }

    static class Pair<F, S> {

        public final F first;
        public final S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

    }

    public static Integer[] HillClimb(int n) {
        Integer[] tablero = new Integer[n];
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = 0;
        }
        Pair menor = new Pair(Integer.MAX_VALUE, tablero.clone());
        for (int k = 0; k < 1000; k++){
            for (int i = 0; i < n; i++) {
                int posInicial = tablero[i];
                for (int j = 0; j < n; j++) {
                    tablero[i] = j;
                    int res = esValido(tablero);
                    if (res == 0) {
                        System.out.println(res);
                        return tablero;
                    }

                    if ((int) menor.first > res) {
                        menor = new Pair(res, tablero.clone());
                    }
                    tablero[i] = posInicial;
                }

                for (int j = 0; j < tablero.length; j++) {
                    tablero[j] = ((Integer[]) menor.second)[j];
                }
            }
        }
        System.out.println(menor.first);
        return tablero;
    }

    public static int esValido(Integer[] tablero) {
        int cont = 0;
        for (int i = 0; i < tablero.length - 1; ++i) {
            for (int j = i + 1; j < tablero.length; j++) {
                if (Math.abs(tablero[i] - tablero[j]) == Math.abs(i - j) || Objects.equals(tablero[i], tablero[j])) {
                    cont++;
                }
            }
        }
        return cont;
    }
}
