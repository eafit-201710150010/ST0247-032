package taller3;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #3
 *
 * @author Mauricio Toro, Mateo Agudelo,Luis Javier Palacio Mesa, Alejandro Arroyave,Santiago Castrillon
 */
public class Taller3 {

    private static void nReinas(String rango, int[] tablero, int fila, int[] auxiliar) {
        if (rango.length() == 0) {
            imprimirTablero(tablero);
            auxiliar[0] += 1;
        }
        for (int i = 0; i < rango.length(); i++) {
            if (puedoPonerReina(tablero, fila, Integer.parseInt(rango.charAt(i) + ""))) {
                nReinas(rango.substring(0, i) + rango.substring(i + 1), tablero, fila + 1, auxiliar);
            }
        }

    }

    public static int nReinas(int n) {
        String rango = "";
        for (int i = 0; i < n; i++) {
            rango += i;
        }
        int auxiliar[] = new int[1];
        nReinas(rango, new int[n], 0, auxiliar);
        return auxiliar[0];

    }

    public static boolean puedoPonerReina(int[] tablero, int fila, int col) {
        tablero[fila] = col;
        for (int j = fila - 1; j >= 0; j--) {
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
        DigraphAL g = new DigraphAL(12);
        g.addArc(7, 11, 1);
        g.addArc(7, 8, 1);
        g.addArc(5, 11, 1);
        g.addArc(3, 8, 1);
        g.addArc(3, 10, 1);
        g.addArc(11, 2, 1);
        g.addArc(11, 9, 1);
        g.addArc(11, 10, 1);
        g.addArc(8, 9, 1);
        camino(g, 3, 10);
        nReinas(4);
    }

    public static ArrayList<Integer> camino(Digraph g, int inicio, int fin) {
        boolean[] visitados = new boolean[g.size()];
        ArrayList<Integer> list = new ArrayList<>();
        list.add(inicio);
        if (dfs(g, inicio, fin, visitados, list)) {
            System.out.println(list);
            return list;
        } 
            return null;
        
    }

    private static boolean dfs(Digraph g, int nodo, int objetivo, boolean[] visitados, ArrayList<Integer> list) {
        ArrayList<Integer> sucesores = g.getSuccessors(nodo);
        if (sucesores != null) {
            for (Integer sucesor : sucesores) {
                if (sucesor == objetivo) {
                    list.add(objetivo);
                    return true;
                } else if (!visitados[sucesor]) {
                    visitados[sucesor] = true;
                    list.add(sucesor);
                    if (dfs(g, sucesor, objetivo, visitados, list)) {
                        return true;
                    } else {
                        list.remove(list.size() - 1);
                        visitados[sucesor] = false;
                    }
                }
            }
        }
        return false;
    }
}
