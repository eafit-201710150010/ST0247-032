package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ljpalaciom
 */
public class Lab3 {

    /**
     * @param args the command line arguments
     */
    static Pair ruta;

    public static void main(String[] args) {
        System.out.println("Punto 1.1 con 4 reinas: ");
        System.out.println(Arrays.toString(nReinas(4)));
        System.out.println("Punto 1.5 ");
        DigraphAL g = new DigraphAL(8);
        g.addArc(1, 3, 0);
        g.addArc(3, 6, 0);
        g.addArc(6, 1, 0);
        System.out.println(esSinCiclosBfs(g));
        System.out.println("Punto 1.6");

        Digraph g1 = new DigraphAL(6);
        g1.addArc(1, 2, 2); 
        g1.addArc(2, 5, 5);
        g1.addArc(2, 3, 4);
        g1.addArc(1, 4, 1);
        g1.addArc(4, 3, 3);
        g1.addArc(3, 5, 1);

        System.out.println(costoMinimo(g1, 1, 5));

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

    //Punto 1.5
    public static boolean esSinCiclosBfs(Digraph g) {
        for (int i = 0; i < g.size; i++) {
            if (hayCaminoBfs(g, i, i, new boolean[g.size])) {
                return false;
            }
        }
        return true;
    }

    private static boolean hayCaminoBfs(Digraph g, int start, int target, boolean[] isVisited) {
        Queue<Integer> cola = new LinkedList();
        cola.add(start);
        ArrayList<Integer> sucesores;
        while (!cola.isEmpty()) {
            sucesores = g.getSuccessors(cola.remove());
            if (sucesores != null) {
                for (Integer sucesor : sucesores) {
                    if (sucesor == target) {
                        return true;
                    }
                    if (!isVisited[sucesor]) {
                        isVisited[sucesor] = true;
                        cola.add(sucesor);
                    }
                }
            }
        }
        return false;
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
    public static ArrayList<Integer> costoMinimo(Digraph g, int inicio, int fin) {
        int costo[] = new int[g.size];
        for (int i = 0; i < costo.length; i++) {
            costo[i] = Integer.MAX_VALUE;
        }
        costo[inicio] = 0;
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(inicio);
        dfs(g, inicio, fin, costo, lista, 0);
        System.out.println(ruta.second);
        return (ArrayList<Integer>) ruta.first;
    }

    private static void dfs(Digraph g, int nodo, int objetivo, int[] costo, ArrayList<Integer> list, int acum) {
        ArrayList<Integer> sucesores = g.getSuccessors(nodo);
        if (nodo == objetivo) {
            Pair pair = new Pair(list.clone(), acum);
            if (ruta == null) {
                ruta = pair;
            } else if ((int) pair.second < (int) ruta.second) {
                ruta = pair;
            }
            return;
        }
        if (sucesores != null) {
            for (Integer sucesor : sucesores) {
                int peso = g.getWeight(nodo, sucesor);
                if (costo[sucesor] > acum + peso) {
                    list.add(sucesor);
                    costo[sucesor] = acum + peso;
                    dfs(g, sucesor, objetivo, costo, list, acum + peso);
                    list.remove(list.size() - 1);
                }
            }
        }
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
