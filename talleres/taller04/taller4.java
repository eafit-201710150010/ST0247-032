package taller4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Clase en la cual se implementan los metodos del Taller de Clase #4
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller4 {

    static int menor;
    public static void main(String[] args) {

DigraphAL g = new DigraphAL(8);
		g.addArc(1, 5, 10);
		g.addArc(0, 1, 20);
		g.addArc(4, 1, 50);
		g.addArc(4, 6, 30);
		g.addArc(0, 6, 90);
		g.addArc(6, 0, 20);
		g.addArc(0, 3, 80);
		g.addArc(5, 3, 40);
		g.addArc(3, 6, 20);
		g.addArc(5, 2, 10);
		g.addArc(2, 5, 50);
		g.addArc(3, 2, 10);
		g.addArc(2, 3, 10);
		g.addArc(2, 7, 20);
		

        System.out.println(costoMinimo(g, 0, 0));
    }

    public static ArrayList<Integer> recorridoDfs(Digraph g, int start) {
        ArrayList<Integer> retornar = new ArrayList<Integer>();
        dfs(g, start, new boolean[g.size], retornar);
        if (retornar.size() == 1) {
            return null;
        }
        return retornar;
    }

    private static void dfs(Digraph g, int start, boolean[] isVisited, ArrayList<Integer> lista) {
        isVisited[start] = true;
        lista.add(start);
        ArrayList<Integer> sucesores = g.getSuccessors(start);
        if (sucesores == null) {
            return;
        }
        for (Integer sucesor : sucesores) {
            if (!isVisited[sucesor]) {
                dfs(g, sucesor, isVisited, lista);
            }
        }
    }

    public static boolean hayCaminoDfs(Digraph g, int start, int target) {
        return hayCaminoDfs(g, start, target, new boolean[g.size]);
    }

    private static boolean hayCaminoDfs(Digraph g, int start, int target, boolean[] isVisited) {
        isVisited[start] = true;
        ArrayList<Integer> sucesores = g.getSuccessors(start);
        if (sucesores != null) {
            for (Integer sucesor : sucesores) {
                if (!isVisited[sucesor] && (sucesor == target || hayCaminoDfs(g, sucesor, target))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Integer> recorridoBfs(Digraph g, int start) {
        ArrayList<Integer> retornar = new ArrayList<Integer>();
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

    public static boolean hayCaminoBfs(Digraph g, int start, int target) {
        return hayCaminoDfs(g, start, target, new boolean[g.size]);
    }

    private static boolean hayCaminoBfs(Digraph g, int start, int target, boolean[] isVisited) {
        Queue<Integer> cola = new LinkedList();
        cola.add(start);
        isVisited[start] = true;
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

    public static int costoMinimo(Digraph g, int inicio, int fin) {
        int costo[] = new int[g.size];
        for (int i = 0; i < costo.length; i++) {
            costo[i] = -1;
        }
        if(inicio == fin) return 0;
        costoMinimo(g, inicio, costo, 0);
        return costo[fin];
    }

    private static void costoMinimo(Digraph g, int inicio, int costo[], int acum) {
        ArrayList<Integer> sucesores = g.getSuccessors(inicio);
        if (sucesores != null) {
            for (Integer sucesor : sucesores) {
                int peso = g.getWeight(inicio, sucesor);
                if (costo[sucesor] == -1 || costo[sucesor] > acum + peso) {
                    costo[sucesor] = acum + peso;
                    costoMinimo(g, sucesor, costo, acum + peso);
                }
            }
        }
    }

    public static int recorrido(Digraph g) {
        int arr[] = new int[g.size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        menor = peso(g, arr);
        recorrido(g, arr,0);
        return menor;
    }

    private static void cambiar(int[] arr, int i, int k) {
        int t = arr[i];
        arr[i] = arr[k];
        arr[k] = t;
    }

    private static int peso(Digraph g, int[] arr) {
        int acum = 0;
        for (int i = 0; i < g.size - 1; i++) {
            acum += g.getWeight(arr[i], arr[i + 1]);
        }
        return acum += g.getWeight(arr[arr.length - 1], arr[0]);
    }

    static void recorrido(Digraph g, int[] arr, int start) {
        for (int i = start; i < arr.length; i++) {
            cambiar(arr, i, start);
            int peso = peso(g, arr);
            if (peso < menor) {
                menor = peso;
            }
            recorrido(g, arr, start + 1);
            cambiar(arr, start, i);
        }
    }

}
