/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import java.util.Scanner;

/**
 *
 * @author ljpalaciom
 */
public class Lab2 {

    /**
     * @param args the command line arguments
     */
    static boolean[][] esMala;
    static int cantidad;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        int caso = 1;
        String linea;
        n = sc.nextInt();
        while (n != 0) {
            esMala = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                linea = sc.next();
                for (int j = 0; j < linea.length(); j++) {
                    if (linea.charAt(j) == '*') {
                        esMala[i][j] = true;
                    }
                }
            }
            System.out.println("Case " + caso + ": " + nReinas(n));
            n = sc.nextInt();
            caso++;
            cantidad = 0;
        }
        System.out.println();
    }

    private static void nReinas(int[] tablero, int fila) {
        if (fila == tablero.length) {
            cantidad++;
            return;
        }
        for (int col = 0; col < tablero.length; col++) {
            if (!esMala[fila][col]) {
                if (puedoPonerReina(tablero, fila, col)) {
                    nReinas(tablero, fila + 1);
                }
            }
        }

    }

    public static int nReinas(int n) {
        nReinas(new int[n], 0);
        return cantidad;

    }

    public static boolean puedoPonerReina(int[] tablero, int fila, int col) {
        tablero[fila] = col;
        for (int j = fila - 1; j >= 0; j--) {
            if (tablero[j] == col || Math.abs(tablero[fila] - tablero[j]) == fila - j) {
                return false;
            }
        }
        return true;
    }
}
