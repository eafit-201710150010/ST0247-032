package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Lab3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Punto 1.1 con 4 reinas: ");
        System.out.println(Arrays.toString(nReinas(4)));
        System.out.println("Punto 5: ");
        System.out.println("“ABCDGH” y “AEDFHR”: " + lcs("ABCDGH", "AEDFHR"));  //"ADH" 3
        System.out.println("“AGGTAB” y “GXTXAYB”: " + lcs("AGGTAB", "GXTXAYB")); //"GTAB" 4
    }

    //Punto 1.1
    public static int[] nReinas(int n) {
        int tablero[] = new int[n];
        if (nReinas(0, tablero)) {
            return tablero;
        } else {
            return null;
        }
    }

    public static boolean nReinas(int fila, int[] tablero) {
        if (fila == tablero.length) {
            return true;
        }
        for (int col = 0; col < tablero.length; col++) {
            if (isValid(fila, col, tablero)) {
                if (nReinas(fila + 1, tablero)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValid(int fila, int col, int tablero[]) {
        tablero[fila] = col;
        for (int j = fila - 1; j >= 0; j--) {
            if (col == tablero[j] || Math.abs(tablero[fila] - tablero[j]) == fila - j) {
                return false;
            }
        }
        return true;
    }

    //Punto 1.3
    public static ArrayList<Integer> recorridoBfs(Digraph g, int start) {
        ArrayList<Integer> retornar = new ArrayList<>();
        bfs(g, start, new boolean[g.size], retornar);
        if (retornar.size() == 1) {
            return null;
        }
        return retornar;
    }

    private static void bfs(Digraph g, int start, boolean[] isVisited, ArrayList<Integer> lista) {
        Queue<Integer> cola = new LinkedList();
        cola.add(start);
        lista.add(start);
        isVisited[start] = true;
        ArrayList<Integer> sucesores;
        while (!cola.isEmpty()) {
            sucesores = g.getSuccessors(cola.remove());
            if (sucesores != null) {
                for (Integer sucesor : sucesores) {
                    if (!isVisited[sucesor]) {
                        cola.add(sucesor);
                        lista.add(sucesor);
                        isVisited[sucesor] = true;
                    }
                }
            }
        }
    }
    //punto 1.6

    public static ArrayList<Integer> caminoCorto(Digraph g, int inicio, int fin) {
        boolean[] visitados = new boolean[g.size()];
        ArrayList<Integer> list = new ArrayList<>();
        list.add(inicio);
        if (dfs(g, inicio, fin, visitados, list)) {
            return list;
        }
        return null;

    }

    private static boolean dfs(Digraph g, int nodo, int objetivo, boolean[] visitados, ArrayList<Integer> list) {
        ArrayList<Integer> sucesores = g.getSuccessors(nodo);
        visitados[nodo] = true;
        if (sucesores != null) {
            for (Integer sucesor : sucesores) {
                list.add(sucesor);
                if (!visitados[sucesor] && (sucesor == objetivo || dfs(g, sucesor, objetivo, visitados, list))) {
                    return true;
                } else {
                    list.remove(list.size() - 1);
                }
            }
        }
        return false;
    }

    //Punto 5.
    private static int lcs(int i, int j, String s1, String s2) {
        if (i == -1 || j == -1) { //Este cambio se debe a que si el caso base es 0, entonces no se analizarán las primeras letras de las cadenas
            return 0;
        }
        boolean prev = i < s1.length() && j < s2.length();
        if (prev && s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcs(i - 1, j - 1, s1, s2);
        }
        int ni = lcs(i - 1, j, s1, s2);
        int nj = lcs(i, j - 1, s1, s2);
        return Math.max(ni, nj);
    }

    public static int lcs(String s1, String s2) {
        return lcs(s1.length(), s2.length(), s1, s2);
    }
}
