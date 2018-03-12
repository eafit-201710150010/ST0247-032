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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Punto 1.1 con 4 reinas: ");
        System.out.println(Arrays.toString(nReinas(4)));

        System.out.println("Punto 1.5 ");
        DigraphAL g = new DigraphAL(8);
        g.addArc(1, 3, 0);
        g.addArc(3, 6, 0);
        g.addArc(6, 1, 0);
        String respuesta = esSinCiclosBfs(g) ? "Es un grafo sin ciclos" : "Es un grafo CON ciclos";
        System.out.println(respuesta);

        System.out.println("Punto 1.6");
        Digraph g1 = new DigraphAL(4);
        g1.addArc(0, 1, 7);
        g1.addArc(0, 2, 15);
        g1.addArc(0, 3, 6);
        g1.addArc(1, 0, 2);
        g1.addArc(1, 2, 7);
        g1.addArc(1, 3, 3);
        g1.addArc(2, 0, 9);
        g1.addArc(2, 1, 6);
        g1.addArc(2, 3, 12);
        g1.addArc(3, 0, 10);
        g1.addArc(3, 1, 4);
        g1.addArc(3, 2, 8);
        System.out.println(costoMinimo(g1, 1, 3));

        System.out.println("Simulacro de parcial");
        System.out.println("Punto 1: ");
        System.out.println(solucionar(14, 2, 3, 7));

        System.out.println("Punto 2: ");
        int graph[][] = {
            {1, 1, 1, 1}, //0
            {1, 1, 1, 0},
            {1, 1, 1, 1},
            {0, 0, 1, 1}

        };
        respuesta = cicloHamil(graph) ? "Tiene ciclo Hamil" : "No tiene ciclo Hamil";
        System.out.println(respuesta);

        System.out.println("Punto 4");
        System.out.println(camino(g, 1, 6));

        System.out.println("Punto 5: ");
        System.out.println("“ABCDGH” y “AEDFHR”: " + lcs("ABCDGH", "AEDFHR"));  //"ADH" 3
        System.out.println("“AGGTAB” y “GXTXAYB”: " + lcs("AGGTAB", "GXTXAYB")); //"GTAB" 4

    }

    //Punto 1.1
    /**
     * Un algoritmo de backtracking para encontrar una solución al problema de
     * las N reinas
     *
     * @param n El numero de reinas con las que se desea una solucion
     * @return Un arreglo donde cada posicion representa una fila, y su
     * respectivo valor la columna donde esta la reina. Si no hay solucion se
     * retorna null
     */
    public static int[] nReinas(int n) {
        int tablero[] = new int[n];
        if (nReinas(0, tablero)) {
            return tablero;
        } else {
            return null;
        }
    }

    /**
     * Un método auxiliar de nReinas(int) que permite saber si hay al menos una
     * solución o no. Además modifica un arreglo que será usado para representar
     * la solución. Se basa en backtracking.
     *
     * @param fila Un interador que nos servirá para saber en cuál fila del
     * tablero estamos
     * @param tablero Un arreglo donde cada posicion representa una fila, y su
     * respectivo valor la columna donde esta la reina.
     * @return Retorna verdadero si hay una solución a las nReinas, o falso de
     * lo contrario
     */
    private static boolean nReinas(int fila, int[] tablero) {
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

    /**
     * Método auxiliar para las nReinas(int,int[]) Permite saber si es posible
     * poner una reina en la fila y columna de un tablero dado, sin que ataque a
     * otras puestas anteriormente.
     *
     * @param fila La fila donde se quiere poner la reina
     * @param col La columna donde se quiere poner la reina
     * @param tablero Un arreglo donde cada posicion representa una fila, y su
     * respectivo valor la columna donde esta la reina.
     * @return Retorna verdadero si es valido poner la reina o falso de lo
     * contrario.
     */
    private static boolean isValid(int fila, int col, int tablero[]) {
        tablero[fila] = col;
        for (int j = fila - 1; j >= 0; j--) {
            if (col == tablero[j] || Math.abs(tablero[fila] - tablero[j]) == fila - j) {
                return false;
            }
        }
        return true;
    }

    //Punto 1.3
    /**
     * Un algoritmo para recorrer un grafo con BFS y returnar el camino generado
     *
     * @param g El grafo a recorrer
     * @param start El vertice inicial desde el que se desea hacer el recorrido
     * @return Una lista que contendrá el camino que se genera con Bfs
     */
    public static ArrayList<Integer> recorridoBfs(Digraph g, int start) {
        ArrayList<Integer> retornar = new ArrayList<>();
        bfs(g, start, new boolean[g.size], retornar);
        if (retornar.size() == 1) {
            return null;
        }
        return retornar;
    }

    /**
     * Un algoritmo auxiliar para recorridoBfs(Digraph,int) implementado con BFS
     *
     * @param g El grafo a recorrer con BFS
     * @param start El vertice inicial desde el que se desea hacer el recorrido
     * @param isVisited Un arreglo de booleanos que nos ayudará a controlar
     * cuando ya visitamos o no un nodo
     * @param lista Este contendrá el camino que se irá construyendo con el bfs
     */
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

    //Punto 1.5
    /**
     * Un método que permite saber si un grafo no tiene caminos en uno o más
     * pasos de cualquier vértice a sí mismo. Es decir, que sea un grafo sin
     * ciclos.
     *
     * @param g El grafo a analizar
     * @return Verdadero si no tiene ciclos, falso de lo contrario
     */
    public static boolean esSinCiclosBfs(Digraph g) {
        for (int i = 0; i < g.size; i++) {
            if (hayCaminoBfs(g, i, i, new boolean[g.size])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Un método auxiliar para esSinCiclosBfs(Digraph) que permite saber si hay
     * un camino de un vértice a otro. Aunque sólo es utilizado por
     * esSinCiclosBfs(Digraph) para saber si hay un camino de un vértice a sí
     * mismo.
     *
     * @param g El grafo a analizar
     * @param start El vértice inicial
     * @param target El vértice final, que esSinCiclosBfs(Digraph) pone igual a
     * start
     * @param isVisited Un arreglo de booleanos que nos ayudará a controlar
     * cuando ya visitamos o no un nodo
     * @return Verdadero si hay un camino de un vértice a otro o falso de lo
     * contrario
     */
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

    //punto 1.6
    /**
     * Este método permite calcular el camino más corto entre dos puntos de un
     * grafo usando backtracking 
     *
     * @param g Grafo a analizar
     * @param inicio Vertice inicial del camino
     * @param fin Vertice final del camino
     * @return Una lista de enteros con el camino más corto
     */
    public static ArrayList<Integer> costoMinimo(Digraph g, int inicio, int fin) {
        int costo[] = new int[g.size];
        for (int i = 0; i < costo.length; i++) {
            costo[i] = Integer.MAX_VALUE;
        }
        costo[inicio] = 0;
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(inicio);
        dfs(g, inicio, fin, costo, lista, 0);
        return (ArrayList<Integer>) ruta.first;
    }
    /**
     * Este es un método auxiliar de costoMinimo(Digraph, int, int) que hace el dfs para hallar el camino más corto entre dos puntos,
     * además tiene una lista para ir modificando el camino y una variable global donde se guardará el camino más corto y su peso.
     * @param g Grafo a analizar
     * @param nodo Nodo inicial del camino
     * @param objetivo Nodo final del camino
     * @param costo Un arreglo de enteros que representa el costo mínimo para ir desde el nodo inicial a cada otro vértice, se mejora cada vez.
     * @param list La lista que contendrá el camino, al principio está vacía
     * @param acum Un acumulador iniciado en 0 que irá llevando el costo de la ruta.
     */
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

    //Simulacro parcial
    //Punto 1
    /**
     *
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static int solucionar(int n, int a, int b, int c) {
        if (n == 0 || (n < a && n < b && n < c)) {
            return 0;
        }
        int res = solucionar(n - a, a, b, c) + 1;
        res = Math.max(res, solucionar(n - b, a, b, c) + 1);
        res = Math.max(res, solucionar(n - c, a, b, c) + 1);
        return res;
    }

    //Punto 2
    /**
     *
     * @param graph
     * @return
     */
    public static boolean cicloHamil(int graph[][]) {
        int path[] = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            path[i] = -1;
        }
        path[0] = 0;
        return cicloHamilAux(graph, path, 1);
    }

    /**
     *
     * @param v
     * @param graph
     * @param path
     * @param pos
     * @return
     */
    public static boolean sePuede(int v, int graph[][], int path[], int pos) {
        if (graph[path[pos - 1]][v] == 0) {
            return false;
        }
        for (int i = 0; i < pos; i++) {
            if (path[i] == v) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param graph
     * @param path
     * @param pos
     * @return
     */
    public static boolean cicloHamilAux(int graph[][], int path[], int pos) {
        if (pos == path.length) {
            if (graph[path[pos - 1]][path[0]] == 1) {
                return true;
            } else {
                return false;
            }
        }
        for (int v = 1; v < graph.length; v++) {
            if (sePuede(v, graph, path, pos)) {
                path[pos] = v;
                if (cicloHamilAux(graph, path, pos + 1)) {
                    return true;
                }
                path[pos] = -1;
            }
        }
        return false;
    }

    //Punto 4
    /**
     *
     * @param g
     * @param inicio
     * @param fin
     * @return
     */
    public static ArrayList<Integer> camino(Digraph g, int inicio, int fin) {
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

    /**
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int lcs(String s1, String s2) {
        return lcs(s1.length(), s2.length(), s1, s2);
    }
}
