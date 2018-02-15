package Taller3;

import java.util.Objects;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #3
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller3 {

//    private static boolean puedoPonerReina(int r, int c, int[] tablero) {
//         complete...
//    }
//
//    public static int nReinas(int n) {
//         complete...
//    }
//	private static int nReinas(int r, int n, int[] tablero) {
//		// complete...
//	}
    private static void nReinas (String rango,int[] tablero,int fila) {
            if (rango.length() == 0) {
                imprimirTablero(tablero);
            } else {
                for (int i = 0; i < rango.length(); i++) {
                    if(puedoPonerReina(tablero, fila, Integer.parseInt(rango.charAt(i) + ""))){
                      nReinas(rango.substring(0, i) + rango.substring(i + 1), tablero,fila +1);
                    } 
                }
            }
    }

    public static void nReinas(int n) {
        String rango = "";
        for (int i = 0; i < n; i++) {
            rango += i;
        }

        nReinas(rango, new int[n],0);  
    }
    public static boolean puedoPonerReina(int[] tablero, int fila, int col) {
            tablero[fila] = col;
            for (int j = fila-1; j >= 0; j--) {
                if (Math.abs(tablero[fila] - tablero[j]) == Math.abs(fila - j) || Objects.equals(tablero[fila], tablero[j])) {
                    return false;
                }
            }
        return true;
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

    public static void main(String[] args) {
        nReinas(5);
    }

//    public static ArrayList<Integer> camino(Digraph g, int inicio, int fin) {
//        // complete...
//    }
//
//    // recomendacion
//    private static boolean dfs(Digraph g, int nodo, int objetivo, boolean[] visitados, ArrayList<Integer> list) {
//        // complete...
//    }
}
