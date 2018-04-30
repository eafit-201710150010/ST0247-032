/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Taller10;

/**
 *
 * @author cl18417
 */
public class Taller10 {

    public static void main(String[] args) {
        System.out.println(lcsCadena("AGGTAB", "GXTXAYB"));
    }

    public static int lcs(String a, String b) {
        int[][] tabla = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    tabla[i][j] = tabla[i - 1][j - 1] + 1;
                } else {
                    tabla[i][j] = Math.max(tabla[i - 1][j],
                            tabla[i][j - 1]);
                }
            }
        }
        return tabla[a.length()][b.length()];
    }

    public static String lcsCadena(String a, String b) {
        int[][] tabla = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    tabla[i][j] = tabla[i - 1][j - 1] + 1;
                } else {
                    tabla[i][j] = Math.max(tabla[i - 1][j],
                            tabla[i][j - 1]);
                }
            }
        }

        StringBuilder result = new StringBuilder("");
        int i = a.length(), j = b.length();
        while (result.length() != tabla[a.length()][b.length()]) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                result.insert(0, a.charAt(i - 1));
                i--;
                j--;
            } else if (tabla[i - 1][j] > tabla[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return result.toString();
    }
}
