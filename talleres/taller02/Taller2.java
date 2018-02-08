/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller2;

import java.util.ArrayList;

/**
 *
 * @author Alejandro Arroyave
 */
public class Taller2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        combinations("abc");
//        permutations("abc");
          int [] lista = {2,1,0};
          System.out.println(esValido(lista));
    }

    //Punto 1
    public static ArrayList<String> combinations(String s) {
        ArrayList<String> list = new ArrayList<>();
        combinations("", s, list);
        System.out.println(list.toString());
        return list;
    }

    private static void combinations(String pre, String pos, ArrayList<String> list) {
        if (pos.length() == 0) {
            list.add(pre);
        } else {
            combinations(pre + pos.substring(0, 1), pos.substring(1), list);
            combinations(pre, pos.substring(1), list);
        }
    }

    //Punto 2
    public static ArrayList<String> permutations(String s) {
        ArrayList<String> list = new ArrayList<>();
        permutations("", s, list);
        System.out.println(list.toString());
        return list;
    }

    private static void permutations(String pre, String pos, ArrayList<String> list) {
        if (pos.length() == 0) {
            list.add(pre);
        } else {
            for (int i = 0; i < pos.length(); i++) {
                permutations(pre + pos.charAt(i), pos.substring(0, i) + pos.substring(i + 1), list);
            }
        }
    }

    public static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        System.out.print("    ");
        for (int i = 0; i < n; ++i) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
        for (int i = 0; i < n; ++i) {
            System.out.print(i + "   ");
            for (int j = 0; j < n; ++j) {
                System.out.print((tablero[i] == j ? "Q" : "#") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean esValido(int[] tablero) {
        boolean yaEsta[] = new boolean[tablero.length];
        for (int i = 0; i < tablero.length; i++) {
            int posReina = tablero[i];
            if (yaEsta[posReina]) {
                return false;
            }
            yaEsta[posReina] = true;
        }
        for (int i = 0; i < tablero.length - 1; i++) {
            for (int j = 0; j < tablero.length - 1; j++) {
                if (Math.abs(tablero[i] - tablero[j]) == j - 1) {
                    return false;
                }
            }
        }
        return true;
    }

     private static void queens(String pre, String pos, ArrayList<String> list) {
        if (pos.length() == 0) {
            list.add(pre);
        } else {
            for (int i = 0; i < pos.length(); i++) {
                queens(pre + pos.charAt(i), pos, list);
            }
        }
    }
    public static int queens(int n) {
        ArrayList<String> list = new ArrayList<>();
        String reina = n + "";
        queens("", reina, list);
        
 
    }
    
    public static int [] toArray(int n){
        String ne = n + "";
        int [] list = new int[ne.length()];
            for (int i = 0; i < 10; i++) {
                String s  = list[i];
                list[i]= ne.charAt(i).toInt();
            }
    }
}
